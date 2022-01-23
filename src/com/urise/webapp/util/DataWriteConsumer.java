package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface DataWriteConsumer<T> {
    void write(T t) throws IOException;
}
