package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface DataReadConsumer {
    void read() throws IOException;
}
