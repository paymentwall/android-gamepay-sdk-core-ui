package com.terminal3.gpcoreui.utils.textwatchers;

import android.text.Editable;
import android.text.Selection;

public class GPExpiryDateTextWatcher implements GPTextWatcher {
    private boolean isFormatting;
    private boolean isDeleting;
    private String currentText = "";
    private static final char SEPARATOR = '/';
    private static final int MONTH_LENGTH = 2;
    private static final int YEAR_LENGTH = 2;
    private static final int MAX_LENGTH = MONTH_LENGTH + YEAR_LENGTH;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Detect if this is a deletion operation
        isDeleting = count > after;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (isFormatting) return;

        isFormatting = true;

        String originalText = s.toString();
        String digitsOnly = originalText.replaceAll("[^\\d]", "");
        StringBuilder formatted = new StringBuilder();

        // Format as MM/YY
        for (int i = 0; i < digitsOnly.length(); i++) {
            if (i == MONTH_LENGTH && digitsOnly.length() > MONTH_LENGTH) {
                formatted.append(SEPARATOR);
            }
            if (i < MAX_LENGTH) {
                formatted.append(digitsOnly.charAt(i));
            }
        }

        currentText = formatted.toString();

        if (!originalText.equals(currentText)) {
            // Clear and set new text
            s.clear();
            s.append(currentText);

            // Handle cursor position
            int cursorPosition;
            if (isDeleting) {
                // When deleting, keep cursor before the separator if possible
                cursorPosition = Math.min(originalText.length(), currentText.length());
                if (cursorPosition > 0 && currentText.charAt(cursorPosition - 1) == SEPARATOR) {
                    cursorPosition--;
                }
            } else {
                // When adding, put cursor after the newly added character
                cursorPosition = currentText.length();
            }

            // Ensure cursor position is valid
            if (cursorPosition >= 0 && cursorPosition <= s.length()) {
                try {
                    Selection.setSelection(s, cursorPosition);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
