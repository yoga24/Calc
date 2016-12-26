package com.cyorg.example.calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    DecimalFormat decimalFormat = new DecimalFormat("###############.#####");

    TextView enterTextView, historyTextView;

    Double valueOne = 0.0, result = -1.0;

    int operationToBePerformed = -1;

    //Flags
    boolean isEqualPressedFlag = false;
    boolean isAllClearFlag = true;
    boolean isFirstValueFlag = true;

    List<Integer> allOperations = Arrays.asList(R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide, R.id.btn_reciprocal, R.id.btn_percent, R.id.btn_equals);
    List<Integer> basicOperations = Arrays.asList(R.id.btn_add, R.id.btn_subtract, R.id.btn_multiply, R.id.btn_divide, R.id.btn_percent);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enterTextView = (TextView) findViewById(R.id.enter_text);
        historyTextView = (TextView) findViewById(R.id.history_text);

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.main_container);
        setListenerForButton(viewGroup);
    }

    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        if (allOperations.contains(viewId)) {
            if (!isEmpty(enterTextView.getText().toString())) {
                operatorPressed(viewId, view);
            }
        } else if(viewId == R.id.btn_clear_all) {
            resetAllValues();
        }else{
            numberPressed(view);
        }
    }

    private void operatorPressed(int viewId, View view) {

        if (basicOperations.contains(viewId)) {
            moveTextToHistory(view);
            operationToBePerformed = viewId;
        } else if (viewId == R.id.btn_reciprocal) {
            double value = Double.parseDouble(enterTextView.getText().toString());
            String historyText;
            if (isFirstValueFlag) {
                double reciValue = 1 / value;
                readIntoValueOne(reciValue);

                enterTextView.setText(decimalFormat.format(reciValue));

                historyText = "recipro(" + decimalFormat.format(value) + ")";
            } else {
                computeResult(1 / value);
                historyText = historyTextView.getText().toString() + " " + "recipro(" + decimalFormat.format(value) + ")";
            }

            historyTextView.setText(historyText);
        } else if (viewId == R.id.btn_equals) {
            double value = Double.parseDouble(enterTextView.getText().toString());
            isEqualPressedFlag = true;
            computeResult(value);
            operationToBePerformed = -1;
        }
    }

    private void resetAllValues()   {
        isEqualPressedFlag = false;
        isAllClearFlag = true;
        isFirstValueFlag = true;
        enterTextView.setText("0");
        historyTextView.setText("");
    }

    private void numberPressed(View view) {
        if (isAllClearFlag) {
            enterTextView.setText(((Button) view).getText());
            isAllClearFlag = false;
        } else {
            String newText = enterTextView.getText().toString() + ((Button) view).getText().toString();
            enterTextView.setText(newText);
        }
    }

    private void moveTextToHistory(View view) {
        double enteredValue = Double.parseDouble(enterTextView.getText().toString());

        String historyText;

        if (isFirstValueFlag) {
            readIntoValueOne(enteredValue);
            historyText = decimalFormat.format(enteredValue) + " " + ((Button) view).getText().toString();
        } else {
            computeResult(enteredValue);
            historyText = historyTextView.getText().toString() + " " + decimalFormat.format(enteredValue) + " " + ((Button) view).getText().toString();
        }


        historyTextView.setText(historyText);

    }

    private void readIntoValueOne(double enteredValue) {
        valueOne = enteredValue;
        isAllClearFlag = true;
        isFirstValueFlag = false;
    }

    private void computeResult(double value) {

//        if (valueTwo == -1.0) {
//            valueTwo = Double.parseDouble(enterTextView.getText().toString());
//        }

        switch (operationToBePerformed) {
            case R.id.btn_add:
                result = valueOne + value;
                break;
            case R.id.btn_subtract:
                result = valueOne - value;
                break;
            case R.id.btn_multiply:
                result = valueOne * value;
                break;
            case R.id.btn_divide:
                result = valueOne / value;
                break;
            case R.id.btn_percent:
                result = valueOne % value;
                break;
        }

        if(isEqualPressedFlag) {
            String historyText = historyTextView.getText().toString() + " " + decimalFormat.format(value);
            historyTextView.setText(historyText);
            isEqualPressedFlag = false;
            isFirstValueFlag = true;
        }

        valueOne = result;
        isAllClearFlag = true;
        enterTextView.setText(decimalFormat.format(result));

    }

    private boolean isEmpty(String value) {
        return (TextUtils.isEmpty(value) || value.equalsIgnoreCase("0"));
    }

    private void setListenerForButton(ViewGroup viewGroup) {
        View view;

        for (int index = 0; index < viewGroup.getChildCount(); index++) {
            view = viewGroup.getChildAt(index);

            if (view instanceof ViewGroup) {
                setListenerForButton((ViewGroup) view);
            } else if (view instanceof Button) {
                view.setOnClickListener(this);
            }
        }
    }
}
