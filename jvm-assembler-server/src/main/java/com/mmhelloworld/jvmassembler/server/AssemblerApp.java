package com.mmhelloworld.jvmassembler.server;

import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * User: marimuthu
 */
public class AssemblerApp extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(AssemblerResource.class);
        return resources;
    }
}
