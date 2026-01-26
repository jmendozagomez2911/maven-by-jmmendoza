package io.github.josemanuel.maven.libold;

import org.apache.commons.lang3.StringUtils;

// Uses the older commons-lang3 version to show a real transitive conflict.
public final class LegacyTextTools {
    private LegacyTextTools() {
    }

    public static String normalize(String input) {
        String safe = StringUtils.trimToEmpty(input);
        String collapsed = StringUtils.normalizeSpace(safe);
        return StringUtils.lowerCase(collapsed);
    }
}