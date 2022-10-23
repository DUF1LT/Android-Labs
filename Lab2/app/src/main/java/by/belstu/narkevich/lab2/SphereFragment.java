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

public class SphereFragment extends Fragment {
    private EditText radiusInput;
    private TextView resultText;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radiusInput = view.findViewById(R.id.radiusSphereInput);
        resultText = view.findViewById(R.id.resultSphereText);

        radiusInput.addTextChangedListener(new TextWatcher() {
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
        return inflater.inflate(R.layout.fragment_sphere, container, false);
    }

    private void HandleInputChange() {
        String radiusText = radiusInput.getText().toString();

        int radius;

        if(radiusText.equals("")) {
            radius = 0;
        } else {
            radius = Integer.parseInt(radiusText);
        }

        resultText.setText(String.valueOf(Math.round(4/3 * Math.PI * Math.pow(radius, 3))), TextView.BufferType.EDITABLE);
    }
}