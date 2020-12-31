package com.strangegrotto.montu.view;

import com.google.common.base.Preconditions;

public enum GutterMarker {
    ARROW("->"),
    BLANK("  ");

    private static final int REQUIRED_STR_LENGTH = 2;

    private final String str;

    GutterMarker(String str) {
        Preconditions.checkArgument(
                str.length() == REQUIRED_STR_LENGTH,
                "Gutter string must be exactly %s chars long",
                REQUIRED_STR_LENGTH);
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
