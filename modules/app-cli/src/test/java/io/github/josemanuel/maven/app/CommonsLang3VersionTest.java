package io.github.josemanuel.maven.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CommonsLang3VersionTest {
    @Test
    void resolvedVersionMatchesExpectedProfile() {
        String expected = System.getProperty("expected.commons.lang3.version");
        assertNotNull(expected, "expected.commons.lang3.version must be set by the build profile");
        assertEquals(expected, CommonsLang3Version.detect());
    }
}