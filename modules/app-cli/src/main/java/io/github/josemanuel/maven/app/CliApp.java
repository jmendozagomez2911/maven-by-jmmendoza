package io.github.josemanuel.maven.app;

import io.github.josemanuel.maven.libnew.ModernTextTools;
import io.github.josemanuel.maven.libold.LegacyTextTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// CLI entry point to demonstrate thin JAR vs fat JAR execution.
public final class CliApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(CliApp.class);

    private CliApp() {
    }

    public static void main(String[] args) {
        String input = args.length == 0 ? "  Maven   inside 2026  " : String.join(" ", args);

        String legacy = LegacyTextTools.normalize(input);
        String modern = ModernTextTools.titleCase(input);
        String masked = ModernTextTools.maskDigits(input);

        String langVersion = CommonsLang3Version.detect();
        LOGGER.info("commons-lang3 resolved version: {}", langVersion);

        System.out.println("Input:   " + input);
        System.out.println("Legacy:  " + legacy);
        System.out.println("Modern:  " + modern);
        System.out.println("Masked:  " + masked);
        System.out.println("Lang3:   " + langVersion);
    }
}