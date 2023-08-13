package com.example.unitconveter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText inputEditText;
    private Spinner unitTypeSpinner;
    private Button convertButton;
    private TextView resultTextView;

    private static final double METER_TO_FEET = 3.28084;
    private static final double METER_TO_INCHES = 39.3701;
    private static final double KG_TO_POUNDS = 2.20462;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.inputEditText);
        unitTypeSpinner = findViewById(R.id.unitTypeSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.unit_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitTypeSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }

    private void performConversion() {
        String inputValueString = inputEditText.getText().toString().trim();
        if (inputValueString.isEmpty()) {
            resultTextView.setText("Please enter a value to convert.");
            return;
        }

        double inputValue = Double.parseDouble(inputValueString);
        int selectedUnitPosition = unitTypeSpinner.getSelectedItemPosition();
        String resultText;

        switch (selectedUnitPosition) {
            case 0: // Meters to Feet
                double feetValue = inputValue * METER_TO_FEET;
                resultText = String.format("%.2f meters convert to %.2f feet", inputValue, feetValue);
                break;
            case 1: // Feet to Meters
                double metersValue = inputValue / METER_TO_FEET;
                resultText = String.format("%.2f feet convert to %.2f meters", inputValue, metersValue);
                break;
            case 2: // Meters to Inches
                double inchesValue = inputValue * METER_TO_INCHES;
                resultText = String.format("%.2f meters convert to %.2f inches", inputValue, inchesValue);
                break;
            case 3: // Kilograms to Pounds
                double poundsValue = inputValue * KG_TO_POUNDS;
                resultText = String.format("%.2f kilograms convert to %.2f pounds", inputValue, poundsValue);
                break;
            case 4: // Pounds to Kilograms
                double kilogramsValue = inputValue / KG_TO_POUNDS;
                resultText = String.format("%.2f pounds convert to %.2f kilograms", inputValue, kilogramsValue);
                break;
            default:
                resultText = "Unknown unit type selected.";
        }

        resultTextView.setText(resultText);
    }
}
