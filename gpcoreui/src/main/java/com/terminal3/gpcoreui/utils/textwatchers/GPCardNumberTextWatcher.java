package com.terminal3.gpcoreui.utils.textwatchers;

import android.text.Editable;
import android.text.Selection;

public class GPCardNumberTextWatcher implements GPTextWatcher {
    private boolean isFormatting;
    private String currentText = "";
    private static final char SEPARATOR = ' ';
    private static final int GROUP_SIZE = 4;
    private static final int MAX_GROUPS = 4;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (isFormatting) return;

        isFormatting = true;

        String digitsOnly = s.toString().replaceAll("[^\\d]", "");
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digitsOnly.length(); i++) {
            if (i > 0 && i % GROUP_SIZE == 0 && i / GROUP_SIZE < MAX_GROUPS) {
                formatted.append(SEPARATOR);
            }
            if (i < MAX_GROUPS * GROUP_SIZE) {
                formatted.append(digitsOnly.charAt(i));
            }
        }

        currentText = formatted.toString();
        if (!s.toString().equals(currentText)) {
            s.replace(0, s.length(), currentText);
            try {
                Selection.setSelection(s, currentText.length());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        isFormatting = false;
    }

    @Override
    public String getRealValue() {
        return currentText.replaceAll("[^\\d]", "");
    }

    @Override
    public String getDisplayValue() {
        return currentText;
    }
}