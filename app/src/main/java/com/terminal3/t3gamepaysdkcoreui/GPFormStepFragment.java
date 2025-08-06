package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.terminal3.gpcoreui.components.GPPrimaryButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.terminal3.gpcoreui.components.GPDynamicForm;
import com.terminal3.gpcoreui.components.GPOptionView;
import com.terminal3.gpcoreui.enums.GPOptionType;
import com.terminal3.gpcoreui.models.GPOption;
import com.terminal3.gpcoreui.models.GPOptionValidation;
import com.terminal3.gpcoreui.utils.validator.GPValidator;
import com.terminal3.gpcoreui.utils.validator.rules.GPRegexRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPRequiredRule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GPFormStepFragment extends Fragment {

    private static final String ARG_JSON_RES = "json_res";

    private GPDynamicForm form;
    private GPValidator validator;
    private OnStepCompleteListener stepListener;

    public static GPFormStepFragment newInstance(int jsonRes) {
        GPFormStepFragment fragment = new GPFormStepFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_JSON_RES, jsonRes);
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnStepCompleteListener(OnStepCompleteListener listener) {
        this.stepListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        form = view.findViewById(R.id.dynamicForm);
        GPPrimaryButton btnNext = view.findViewById(R.id.btnNext);

        validator = new GPValidator.Builder().setAutoDisplayError(true).build();

        int resId = getArguments() != null ? getArguments().getInt(ARG_JSON_RES) : 0;
        if (resId != 0) {
            List<GPOption> options = parseOptions(resId);
            form.setOptions(options);
            boolean isRedirect = !options.isEmpty() && options.get(0).getType() == GPOptionType.REDIRECT;
            if (isRedirect) {
                btnNext.setVisibility(View.GONE);
                form.setOnFormValueChangedListener((id, value) -> {
                    if (stepListener != null) {
                        java.util.Map<String, String> map = new java.util.HashMap<>();
                        map.put(id, value);
                        stepListener.onStepComplete(map);
                    }
                });
            } else {
                setupValidation(options);
                btnNext.setOnClickListener(v -> {
                    if (validator.validate().getAllErrors().isEmpty()) {
                        if (stepListener != null) {
                            stepListener.onStepComplete(form.getValues());
                        }
                    }
                });
            }
        }
    }

    private void setupValidation(List<GPOption> options) {
        for (GPOption option : options) {
            if (option.getType() == GPOptionType.REDIRECT) continue;
            GPOptionView view = form.getOptionView(option.getId());
            List<GPOptionValidation> validations = option.getValidations();
            List<com.terminal3.gpcoreui.utils.validator.GPValidationRule> rules = new ArrayList<>();
            rules.add(new GPRequiredRule(option.getLabel() + " is required"));
            for (GPOptionValidation val : validations) {
                if (!TextUtils.isEmpty(val.getRegex())) {
                    rules.add(new GPRegexRule(val.getRegex(), val.getMessage()));
                }
            }
            validator.addRules(view, rules);
        }
    }

    private List<GPOption> parseOptions(int resId) {
        List<GPOption> list = new ArrayList<>();
        try {
            InputStream is = requireContext().getResources().openRawResource(resId);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());
            JSONObject data = json.getJSONObject("data");
            JSONArray arr = data.getJSONArray("instructions");
            for (int i = 0; i < arr.length(); i++) {
                GPOption option = GPOption.fromJson(arr.getJSONObject(i));
                list.add(option);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public interface OnStepCompleteListener {
        void onStepComplete(java.util.Map<String, String> values);
    }
}
