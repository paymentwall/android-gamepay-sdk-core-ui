package com.terminal3.gpcoreui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import com.terminal3.gpcoreui.R;

public class GPFontManager {
    public static final String FONT_ROBOTO = "roboto";

    private static final String PREF_FONT_KEY = "selected_font";
    private static final String PREF_NAME = "font_prefs";

    private Context context;

    public GPFontManager(Context context) {
        this.context = context;
    }

    public void saveSelectedFont(String fontName) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_FONT_KEY, fontName);
        editor.apply();
    }

    public String getSelectedFont() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(PREF_FONT_KEY, FONT_ROBOTO);
    }

    public int getFontFamily() {
        String selectedFont = getSelectedFont();
        switch (selectedFont) {
            case FONT_ROBOTO:
                return R.font.roboto_family;
            default:
                return R.font.roboto_family;
        }
    }

    public Typeface getTypeface() {
        return getTypeface(null);
    }

    public Typeface getTypeface(String fontName) {
        String font = (fontName != null) ? fontName : getSelectedFont();
        try {
            switch (font) {
                case FONT_ROBOTO:
                    return ResourcesCompat.getFont(context, R.font.roboto_family);
                default:
                    return ResourcesCompat.getFont(context, R.font.roboto_family);
            }
        } catch (Exception e) {
            return Typeface.DEFAULT;
        }
    }
}
