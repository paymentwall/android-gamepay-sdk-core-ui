package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.terminal3.gpcoreui.components.GPPayAltoButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class GPPayAltoListFragment extends Fragment {

    //region Lifecycle
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payalto_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout containerLayout = view.findViewById(R.id.payaltoContainer);
        try {
            JSONArray methods = new JSONArray(loadJsonFromRaw());
            for (int i = 0; i < methods.length(); i++) {
                JSONObject obj = methods.getJSONObject(i);
                GPPayAltoButton button = new GPPayAltoButton(requireContext());
                button.setText(obj.optString("name"));
                button.setLogo(obj.optString("img_url"));
                final String id = obj.optString("id");
                button.setOnClickListener(v -> Log.d("GPPayAltoListFragment", "Clicked: " + id));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i > 0) {
                    params.topMargin = dpToPx(16);
                }
                button.setLayoutParams(params);
                containerLayout.addView(button);
            }
        } catch (JSONException | IOException e) {
            Log.e("GPPayAltoListFragment", "Failed to load payment methods", e);
        }
    }
    //endregion

    //region Helpers
    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }

    private String loadJsonFromRaw() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.payalto_methods);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        return builder.toString();
    }
    //endregion
}
