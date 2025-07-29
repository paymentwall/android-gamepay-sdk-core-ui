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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.terminal3.gpcoreui.R;

/**
 * Agreement checkbox view displaying rich text with clickable links.
 */
public class GPAgreementCheckboxView extends LinearLayout {

    private AppCompatCheckBox checkBox;
    private TextView textView;

    private String tosLabel;
    private String privacyLabel;
    private String tosUrl;
    private String privacyUrl;
    private String supportEmail;
    private String merchantName;

    public GPAgreementCheckboxView(Context context) {
        super(context);
        init(context);
    }

    public GPAgreementCheckboxView(Context context, @Nullable android.util.AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPAgreementCheckboxView(Context context, @Nullable android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GPAgreementCheckboxView(
            Context context,
            @NonNull String tosLabel,
            @NonNull String tosUrl,
            @NonNull String privacyLabel,
            @NonNull String privacyUrl,
            @Nullable String supportEmail,
            @NonNull String merchantName
    ) {
        super(context);
        init(context);
        configure(tosLabel, tosUrl, privacyLabel, privacyUrl, supportEmail, merchantName);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.gp_agreement_checkbox_view, this, true);
        checkBox = findViewById(R.id.gp_agreement_checkbox);
        textView = findViewById(R.id.gp_agreement_text);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        checkBox.setButtonTintList(getContext().getColorStateList(R.color.gp_text_primary));
    }

    /**
     * Configure the checkbox view text and links.
     */
    public void configure(
            @NonNull String tosLabel,
            @NonNull String tosUrl,
            @NonNull String privacyLabel,
            @NonNull String privacyUrl,
            @Nullable String supportEmail,
            @NonNull String merchantName
    ) {
        this.tosLabel = tosLabel;
        this.tosUrl = tosUrl;
        this.privacyLabel = privacyLabel;
        this.privacyUrl = privacyUrl;
        this.supportEmail = supportEmail;
        this.merchantName = merchantName;
        updateText();
    }

    private void updateText() {
        StringBuilder sb = new StringBuilder();
        sb.append("I accept the ")
                .append(tosLabel)
                .append(" and ")
                .append(privacyLabel)
                .append(" of ")
                .append(merchantName)
                .append(". ")
                .append(merchantName)
                .append(" is the authorised reseller and merchant of the products and services offered within this store.");
        if (supportEmail != null && !supportEmail.isEmpty()) {
            sb.append(" Please contact us if you have any questions via e-mail at ")
                    .append(supportEmail)
                    .append('.');
        }

        String fullText = sb.toString();
        SpannableStringBuilder builder = new SpannableStringBuilder(fullText);

        addLink(builder, fullText.indexOf(tosLabel), tosLabel.length(), () -> openUrl(tosUrl));
        addLink(builder, fullText.indexOf(privacyLabel), privacyLabel.length(), () -> openUrl(privacyUrl));
        if (supportEmail != null && !supportEmail.isEmpty()) {
            int start = fullText.indexOf(supportEmail);
            addLink(builder, start, supportEmail.length(), () -> openEmail(supportEmail));
        }

        textView.setText(builder);
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
                ds.setColor(getContext().getColor(R.color.gp_text_primary)); // Set link color to black
                ds.setUnderlineText(true); // Keep underline
            }
        }, start, start + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                start, start + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new android.text.style.UnderlineSpan(),
                start, start + length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void openUrl(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException ignored) {}
    }

    private void openEmail(String email) {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException ignored) {}
    }

    public boolean isChecked() {
        return checkBox.isChecked();
    }

    public void setChecked(boolean checked) {
        checkBox.setChecked(checked);
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        checkBox.setOnCheckedChangeListener(listener);
    }
}
