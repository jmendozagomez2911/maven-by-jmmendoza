package io.github.josemanuel.maven.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

// Reads commons-lang3 version from the dependency's pom.properties on the classpath.
public final class CommonsLang3Version {
    private static final String POM_PROPERTIES_PATH =
            "META-INF/maven/org.apache.commons/commons-lang3/pom.properties";

    private CommonsLang3Version() {
    }

    public static String detect() {
        Properties props = new Properties();
        try (InputStream input = CommonsLang3Version.class.getClassLoader()
                .getResourceAsStream(POM_PROPERTIES_PATH)) {
            if (input == null) {
                return "unknown";
            }
            props.load(input);
            return props.getProperty("version", "unknown");
        } catch (IOException ex) {
            return "unknown";
        }
    }
}