package com.terminal3.gpcoreui.utils.textwatchers;

import android.text.TextWatcher;

public interface GPTextWatcher extends TextWatcher {
    String getRealValue();
    String getDisplayValue();
}
