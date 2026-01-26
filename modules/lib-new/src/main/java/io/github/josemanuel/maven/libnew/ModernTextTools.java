package io.github.josemanuel.maven.libnew;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

// Uses the newer commons-lang3 version pulled from the BOM.
public final class ModernTextTools {
    private ModernTextTools() {
    }

    public static String titleCase(String input) {
        String safe = StringUtils.trimToEmpty(input);
        String collapsed = StringUtils.normalizeSpace(safe);
        String lower = collapsed.toLowerCase(Locale.ROOT);
        return StringUtils.capitalize(lower);
    }

    public static String maskDigits(String input) {
        String safe = StringUtils.trimToEmpty(input);
        return StringUtils.replacePattern(safe, "\\d", "#");
    }
}