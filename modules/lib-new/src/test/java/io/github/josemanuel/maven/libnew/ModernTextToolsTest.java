package io.github.josemanuel.maven.libnew;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModernTextToolsTest {
    @Test
    void titleCaseCapitalizesFirstLetter() {
        assertEquals("Hello world", ModernTextTools.titleCase("  HELLO   WORLD  "));
    }

    @Test
    void maskDigitsReplacesNumbers() {
        assertEquals("Order ###", ModernTextTools.maskDigits("Order 123"));
    }
}