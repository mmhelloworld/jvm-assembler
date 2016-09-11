package com.mmhelloworld.jvmassembler.server;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Scanner;

public final class AssemblerServer {

    private final int port;
    private final File workingDir;

    public AssemblerServer(int port, File workingDir) {
        this.port = port;
        this.workingDir = workingDir;
    }

    public void start() {
        workingDir.mkdirs();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("javax.ws.rs.Application", AssemblerApp.class.getCanonicalName());

        Server jettyServer = new Server(port);
        jettyServer.setHandler(context);

        try {
            jettyServer.start();
            int runningPort = getPort(jettyServer);
            System.out.printf("Server started successfully and is running on port %s.\n", runningPort);
            writePort(runningPort);
            jettyServer.join();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jettyServer.destroy();
        }
    }

    private void writePort(int port) {
        final File portInfoFile = getInfoFile();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(portInfoFile))) {
            out.append(Integer.toString(port)).append('\n');
            out.flush();
        } catch (IOException e) {
            System.err.println("Cannot write port details to " + portInfoFile);
            e.printStackTrace();
        }
    }

    public Optional<Integer> getPort() {
        return Optional.of(getInfoFile())
            .filter(File::exists)
            .flatMap(f -> {
                try {
                    return Files.lines(f.toPath())
                        .findFirst()
                        .flatMap(AssemblerServer::parseInt)
                        .filter(this::isRunning);
                } catch (IOException e) {
                    return Optional.empty();
                }
            });
    }

    private boolean isRunning(int port) {
        final String url = String.format("http://localhost:%d/assembler/health", port);
        try (InputStream ignored = new URL(url).openStream()) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private File getInfoFile() {
        return new File(workingDir, ".assembler");
    }

    private static Optional<Integer> parseInt(String portStr) {
        try {
            return Optional.of(Integer.parseInt(portStr));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static File getDefaultWorkingDir() {
        final String userHome = System.getProperty("user.home");
        return new File(userHome, ".jvm-assembler");
    }

    private static int getPort(final Server jettyServer) {
        return ((ServerConnector) (jettyServer.getConnectors()[0])).getLocalPort();
    }

    public static void start(final String[] args) {
        Config config = getConfig(args);
        AssemblerServer server = new AssemblerServer(config.getPort(), config.getWorkingDir());

        server.getPort()
            .map(port -> {
                System.out.printf("A server may be already running on port %d.%n", port);
                if (shouldForceStart(config)) {
                    server.start();
                } else {
                    System.err.println("Use option --force to force start a new server.");
                }
                return false; //ignored
            })
            .orElseGet(() -> {
                server.start();
                return false; // ignored
            });
    }

    private static boolean shouldForceStart(Config config) {
        return config.isForceStart() || (config.isInteractive() && userToStartAnother());
    }

    private static Config getConfig(final String[] args) {
        OptionParser parser = new OptionParser();
        OptionSpec<File> workDirOpt = parser.accepts("work-dir")
            .withOptionalArg()
            .ofType(File.class)
            .defaultsTo(getDefaultWorkingDir());

        OptionSpec<Integer> portOpt = parser.accepts("port")
            .withOptionalArg()
            .ofType(Integer.class)
            .defaultsTo(0);

        parser.accepts("non-interactive").withOptionalArg();
        parser.accepts("force").withOptionalArg();

        OptionSet options = parser.parse(args);
        return new Config(portOpt.value(options), workDirOpt.value(options), !options.has("non-interactive"),
            options.has("force"));
    }

    private static boolean userToStartAnother() {
        System.out.print("Do you really want to start a new server? (y/n): ");
        final Scanner in = new Scanner(System.in);
        return in.hasNext() && in.nextLine().equalsIgnoreCase("y");
    }

    public static void main(String[] args) {
        start(args);
    }

}
