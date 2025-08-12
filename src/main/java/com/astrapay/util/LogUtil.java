package com.astrapay.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogUtil {
    public void error(Exception ex) {
        if (ex.getCause() != null)
            log.error("[{} ({})] {}: {}", ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(), ex.getCause().toString(), ex.getMessage());
        else
            log.error("[{} ({})] {}", ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(), ex.getMessage());
    }
}
