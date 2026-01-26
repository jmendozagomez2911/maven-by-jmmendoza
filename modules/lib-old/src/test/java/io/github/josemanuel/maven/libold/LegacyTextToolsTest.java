package io.github.josemanuel.maven.libold;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LegacyTextToolsTest {
    @Test
    void normalizeLowercasesAndCollapsesWhitespace() {
        String input = "  Maven   INSIDE 2026  ";
        assertEquals("maven inside 2026", LegacyTextTools.normalize(input));
    }
}