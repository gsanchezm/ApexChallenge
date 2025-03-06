package com.apex.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serial;

public class FrameworkException extends RuntimeException {

    private static final Logger logger = LogManager.getLogger(FrameworkException.class);
    @Serial
    private static final long serialVersionUID = 700L;

    public FrameworkException(String message) {
        super(message);
        logger.error(message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
        logger.error(message, cause);
    }
}
