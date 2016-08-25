package com.mmhelloworld.jvmassembler.server;

public class AssemblerResponse {

    private final boolean isSuccess;
    private final String message;

    public AssemblerResponse(final boolean isSuccess, final String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }
}
