package com.terminal3.gpcoreui.components;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.terminal3.gpcoreui.R;

public class GPAgreementTextView extends AppCompatTextView {

    private String tosLabel;
    private String privacyLabel;
    private String tosUrl;
    private String privacyUrl;
    private String merchantName;

    public GPAgreementTextView(Context context) {
        this(context, null);
    }

    public GPAgreementTextView(Context context, @Nullable android.util.AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GPAgreementTextView(Context context, @Nullable android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void configure(
            @NonNull String tosLabel,
            @NonNull String tosUrl,
            @NonNull String privacyLabel,
            @NonNull String privacyUrl,
            @NonNull String merchantName,
            @NonNull String extraDisclaimerText
    ) {
        this.tosLabel = tosLabel;
        this.tosUrl = tosUrl;
        this.privacyLabel = privacyLabel;
        this.privacyUrl = privacyUrl;
        this.merchantName = merchantName;
        updateText(extraDisclaimerText);
    }

    public void updateText(String extraText) {
        String fullText = getContext().getString(R.string.gp_agreement_text, merchantName, tosLabel, privacyLabel, extraText);
        SpannableStringBuilder builder = new SpannableStringBuilder(fullText);

        addLink(builder, fullText.indexOf(tosLabel), tosLabel.length(), () -> openUrl(tosUrl));
        addLink(builder, fullText.indexOf(privacyLabel), privacyLabel.length(), () -> openUrl(privacyUrl));

        setText(builder);
    }

    private void addLink(SpannableStringBuilder builder, int start, int length, Runnable onClick) {
        if (start < 0) return;
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                onClick.run();
            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getContext().getColor(R.color.gp_text_link));
            }
        }, start, start + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.NORMAL),
                start, start + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void openUrl(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException ignored) {}
    }
}
