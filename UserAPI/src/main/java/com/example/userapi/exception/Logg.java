package com.example.userapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Logg {

    default Logger LOGGER() {
        return LoggerFactory.getLogger(this.getClass());
    }

}
