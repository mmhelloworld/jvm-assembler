package com.mmhelloworld.jvmassembler.server;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * User: marimuthu
 */
public class CreateBytecode {
    private final List<Asm> instructions;

    public CreateBytecode(@JsonProperty("instructions") List<Asm> instructions) {
        this.instructions = instructions;
    }

    public List<Asm> getInstructions() {
        return instructions;
    }
}
