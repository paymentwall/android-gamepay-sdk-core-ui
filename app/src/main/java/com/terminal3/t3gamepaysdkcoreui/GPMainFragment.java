package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GPMainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.listOptions);
        String[] items = new String[] {"Input Fields", "Buttons", "Dynamic Form", "Saved Cards"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ((GPMainActivity) requireActivity()).showFragment(new GPInputFieldsFragment(), true);
                        break;
                    case 1:
                        ((GPMainActivity) requireActivity()).showFragment(new GPButtonsFragment(), true);
                        break;
                    case 2:
                        ((GPMainActivity) requireActivity()).showFragment(new GPFormHostFragment(), true);
                        break;
                    case 3:
                        ((GPMainActivity) requireActivity()).showFragment(new GPSavedCardFragment(), true);
                        break;
                }
            }
        });
    }
}
