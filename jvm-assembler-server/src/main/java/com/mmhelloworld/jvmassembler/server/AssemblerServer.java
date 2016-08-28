package com.mmhelloworld.jvmassembler.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class AssemblerServer {
    public static void main(String[] args) throws Exception {
        int alreadyRunningPort = readPort();
        if (alreadyRunningPort >= 0) {
            System.err.printf("A server may be already running on port %d.\n", alreadyRunningPort);
            System.out.println("Do you want to force start a new server? (y/n)");
            final Scanner in = new Scanner(System.in);
            if (in.hasNext() && !in.nextLine().equalsIgnoreCase("y")) {
                System.exit(0);
            }
        }
        startServer();
    }

    private static void startServer() throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        ServletHolder jerseyServlet = context.addServlet(ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("javax.ws.rs.Application", AssemblerApp.class.getCanonicalName());

        Server jettyServer = new Server(0);
        jettyServer.setHandler(context);

        try {
            jettyServer.start();
            int port = getPort(jettyServer);
            System.out.printf("Server started successfully and is running on port %s.\n", port);
            writePort(port);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                getInfoFile().delete();
            }));
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }

    private static void writePort(final int port) {
        final File portInfoFile = getInfoFile();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(portInfoFile))) {
            out.append(Integer.toString(port)).append('\n');
            out.flush();
        } catch (IOException e) {
            System.err.println("Cannot write port details to " + portInfoFile);
            e.printStackTrace();
        }
    }

    private static int readPort() throws IOException {
        final File portInfoFile = getInfoFile();
        if (portInfoFile.exists()) {
            return Files.lines(portInfoFile.toPath())
            .findFirst()
            .map(s -> parseIntWithDefault(s, -1))
            .orElse(-1);
        } else {
            return -1;
        }

    }

    private static File getInfoFile() {
        File workingDir = getWorkingDir();
        getWorkingDir().mkdir();
        return new File(workingDir, ".assembler");
    }

    private static int parseIntWithDefault(String portStr, int dflt) {
        try {
            return Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            return dflt;
        }
    }

    private static File getWorkingDir() {
        final String userHome = System.getProperty("user.home");
        return new File(userHome, ".jvm-assembler");
    }

    private static int getPort(final Server jettyServer) {
        return ((ServerConnector) (jettyServer.getConnectors()[0])).getLocalPort();
    }

}
