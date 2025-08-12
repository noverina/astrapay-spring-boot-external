package com.astrapay.util;

import com.fasterxml.uuid.Generators;
import org.springframework.stereotype.Component;

@Component
public class UuidV6Generator {
    public String generate() {
        return  Generators.timeBasedReorderedGenerator().generate().toString();
    }
}
