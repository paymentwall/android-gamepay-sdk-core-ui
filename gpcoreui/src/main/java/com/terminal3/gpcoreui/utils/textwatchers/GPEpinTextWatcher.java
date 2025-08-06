package com.terminal3.gpcoreui.utils.textwatchers;

import android.text.Editable;

public class GPEpinTextWatcher implements GPTextWatcher {
    private static final char SEPARATOR = ' ';
    private boolean isFormatting;
    private boolean deletingHyphen;
    private int hyphenStart;
    private boolean deletingBackward;

    private String currentText = "";

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (count > 0 && s.charAt(start) == SEPARATOR) {
            deletingHyphen = true;
            hyphenStart = start;
        } else {
            deletingHyphen = false;
        }

        deletingBackward = count > after;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isFormatting) return;

        isFormatting = true;

        // Remove all separators
        int length = s.length();
        for (int i = 0; i < length; i++) {
            if (s.charAt(i) == SEPARATOR) {
                s.delete(i, i + 1);
                length--;
                i--;
            }
        }

        // Add new separators every 4 characters
        for (int i = 4; i <= s.length(); i += 5) {
            if (i < s.length()) {
                s.insert(i, String.valueOf(SEPARATOR));
            }
        }

        // Handle deletion
        if (deletingHyphen && hyphenStart > 0) {
            if (deletingBackward && hyphenStart - 1 >= 0) {
                s.delete(hyphenStart - 1, hyphenStart);
            } else if (!deletingBackward && hyphenStart + 1 <= s.length()) {
                s.delete(hyphenStart, hyphenStart + 1);
            }
        }
        currentText = s.toString();
        isFormatting = false;
    }

    @Override
    public String getRealValue() {
        return currentText.replaceAll(String.valueOf(SEPARATOR), "");
    }

    @Override
    public String getDisplayValue() {
        return currentText;
    }
}
