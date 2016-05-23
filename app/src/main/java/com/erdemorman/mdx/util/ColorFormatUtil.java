package com.erdemorman.mdx.util;

import android.graphics.Color;

import java.util.Locale;

public class ColorFormatUtil {
    public enum ColorFormat {
        HEX,
        RGB
    }

    public static String format(int color, ColorFormat format) {
        switch (format) {
            case RGB:
                return toRgb(color);
            case HEX:
            default:
                return toHex(color);
        }
    }

    public static String toRgb(String hexColor) {
        return toRgb(Color.parseColor(hexColor));
    }

    public static String toRgb(int color) {
        return String.format(Locale.US, "rgb(%1$d, %2$d, %3$d)",
                Color.red(color), Color.green(color), Color.blue(color));
    }

    public static String toHex(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }
}
