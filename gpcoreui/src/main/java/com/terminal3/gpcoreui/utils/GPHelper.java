package com.terminal3.gpcoreui.utils;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;

public class GPHelper {
    /**
     * Creates a formatted payment instruction text with bold HTML tags
     *
     * @param continueText  The text to be bolded for the continue action (e.g., "Continue")
     * @param paymentMethod The payment method to be bolded (e.g., "Kakao Pay")
     * @return Formatted HTML string with payment instructions
     */
    public static SpannableStringBuilder createPaymentInstructionText(String continueText, String paymentMethod) {
        String fullText = String.format(
                "Click %s to pay for this order using %s. " +
                        "Once your payment is confirmed, you'll receive a notification with your order details.",
                continueText, paymentMethod
        );

        return createSpannableWithBoldWords(fullText, continueText, paymentMethod);
    }

    /**
     * Generic method to create spannable text with multiple bold words
     * @param text The full text string
     * @param boldWords Array of words/phrases to make bold
     * @return SpannableStringBuilder with specified words in bold
     */
    public static SpannableStringBuilder createSpannableWithBoldWords(String text, String... boldWords) {
        SpannableStringBuilder spannableBuilder = new SpannableStringBuilder(text);

        for (String boldWord : boldWords) {
            int start = 0;
            while (start < text.length()) {
                int index = text.indexOf(boldWord, start);
                if (index == -1) break;

                int end = index + boldWord.length();
                spannableBuilder.setSpan(
                        new StyleSpan(Typeface.BOLD),
                        index,
                        end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                start = end;
            }
        }

        return spannableBuilder;
    }
}
