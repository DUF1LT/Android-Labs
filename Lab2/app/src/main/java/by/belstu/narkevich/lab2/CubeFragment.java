package by.belstu.narkevich.lab2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CubeFragment extends Fragment {
    private EditText widthInput;
    private TextView resultText;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        widthInput = view.findViewById(R.id.widthCubeInput);
        resultText = view.findViewById(R.id.resultCubeText);

        widthInput.addTextChangedListener(new TextWatcher() {
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
        return inflater.inflate(R.layout.fragment_cube, container, false);
    }
    
    private void HandleInputChange() {
        String widthText = widthInput.getText().toString();

        int width;

        if(widthText.equals("")) {
            width = 0;
        } else {
            width = Integer.parseInt(widthText);
        }

        resultText.setText(String.valueOf(width * width * width), TextView.BufferType.EDITABLE);
    }


}