package com.mmhelloworld.jvmassembler.server;

import java.io.File;

public final class Config {
    private final int port;
    private final File workingDir;
    private final boolean isInteractive;
    private final boolean forceStart;

    public Config(final int port, final File workingDir, final boolean isInteractive, final boolean forceStart) {
        this.port = port;
        this.workingDir = workingDir;
        this.isInteractive = isInteractive;
        this.forceStart = forceStart;
    }

    public int getPort() {
        return port;
    }

    public File getWorkingDir() {
        return workingDir;
    }

    public boolean isInteractive() {
        return isInteractive;
    }

    public boolean isForceStart() {
        return forceStart;
    }
}
