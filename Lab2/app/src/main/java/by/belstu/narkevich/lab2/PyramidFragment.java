package by.belstu.narkevich.lab2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class PyramidFragment extends Fragment {
    private EditText siteLengthInput;
    private EditText heightInput;
    private TextView resultText;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        siteLengthInput = view.findViewById(R.id.siteLengthPyramidInput);
        heightInput = view.findViewById(R.id.heightPyramidInput);
        resultText = view.findViewById(R.id.resultPyramidText);

        siteLengthInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                HandleInputChange();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        heightInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                HandleInputChange();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pyramid, container, false);
    }

    private void HandleInputChange() {
        String siteLengthText = siteLengthInput.getText().toString();
        String heightText = heightInput.getText().toString();

        int siteLength;
        int height;

        if(siteLengthText.equals("")) {
            siteLength = 0;
        } else {
            siteLength = Integer.parseInt(siteLengthText);
        }

        if(heightText.equals("")) {
            height = 0;
        } else {
            height = Integer.parseInt(heightText);
        }

        resultText.setText(String.valueOf(Math.round(Math.pow(siteLength, 2) * height / (Math.sqrt(3) * 4))), TextView.BufferType.EDITABLE);
    }
}