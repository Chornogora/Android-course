package nure.bulhakov;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.math.BigDecimal;
import java.util.prefs.Preferences;

import nure.bulhakov.math.Operand;

public class MainActivity extends AppCompatActivity {

    private static final String LAST_NUMBER_ID = "LastNumber";

    private Operand mainOperator;

    private Operand lastOperator;

    private StringBuilder operandBuilder = new StringBuilder();

    private StringBuilder rowBuilder = new StringBuilder();

    private TextView historyView;

    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        historyView = findViewById(R.id.history);
        showHistory();
        display.setText(rowBuilder.toString());
    }

    @Override
    protected void onDestroy() {
        if(isFinishing()) {
            SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
            int i = 0;
            String row;
            do {
                row = getPreferences(MODE_PRIVATE).getString(String.valueOf(i), System.lineSeparator());
                editor.putString(String.valueOf(i), "");
                ++i;
            } while (!System.lineSeparator().equals(row));
            editor.putInt(LAST_NUMBER_ID, 0)
                    .apply();
        }
        super.onDestroy();
    }

    public void addNumericOperand(View view) {
        CharSequence text = ((AppCompatButton) view).getText();
        appendToInterface(text);
        display.setText(display.getText().toString() + text);
    }

    public void addDotOperand(View view) {
        if (operandBuilder.length() == 0 || operandBuilder.indexOf(".") != -1) {
            return;
        }
        CharSequence text = ((AppCompatButton) view).getText();
        appendToInterface(text);
        display.setText(display.getText().toString() + text);
    }

    public void addPlusOperation(View view) {
        if (operandBuilder.toString().endsWith(".") || operandBuilder.length() == 0) {
            return;
        }
        double number = Double.parseDouble(operandBuilder.toString());
        Operand leftOperand;
        if (mainOperator == null) {
            leftOperand = new Operand((l, r) -> BigDecimal.valueOf(number));
        } else {
            leftOperand = mainOperator;
            lastOperator.setRightOperand(new Operand((l, r) -> BigDecimal.valueOf(number)));
        }
        mainOperator = new Operand((left, right) -> {
            BigDecimal leftPart = left.apply();
            BigDecimal rightPart = right.apply();
            return leftPart.add(rightPart);
        });
        mainOperator.setLeftOperand(leftOperand);
        lastOperator = mainOperator;
        operandBuilder = new StringBuilder();
        rowBuilder.append("+");
        display.setText(display.getText().toString() + "+");
    }

    public void addMinusOperation(View view) {
        if (operandBuilder.length() == 0) {
            appendToInterface("-");
            display.setText(display.getText().toString() + "-");
            return;
        }
        double number = Double.parseDouble(operandBuilder.toString());
        Operand leftOperand;
        if (mainOperator == null) {
            leftOperand = new Operand((l, r) -> BigDecimal.valueOf(number));
        } else {
            leftOperand = mainOperator;
            lastOperator.setRightOperand(new Operand((l, r) -> BigDecimal.valueOf(number)));
        }
        mainOperator = new Operand((left, right) -> {
            BigDecimal leftPart = left.apply();
            BigDecimal rightPart = right.apply();
            return leftPart.subtract(rightPart);
        });
        mainOperator.setLeftOperand(leftOperand);
        lastOperator = mainOperator;
        operandBuilder = new StringBuilder();
        rowBuilder.append("-");
        display.setText(display.getText().toString() + "-");
    }

    public void addMultiplyOperation(View view) {
        if (operandBuilder.toString().endsWith(".") || operandBuilder.length() == 0) {
            return;
        }
        double number = Double.parseDouble(operandBuilder.toString());
        Operand leftOperand = new Operand((l, r) -> BigDecimal.valueOf(number));
        if (mainOperator == null) {
            mainOperator = new Operand((left, right) -> {
                BigDecimal leftPart = left.apply();
                BigDecimal rightPart = right.apply();
                return leftPart.multiply(rightPart);
            });
            mainOperator.setLeftOperand(leftOperand);
            lastOperator = mainOperator;
        } else {
            Operand multiplyOperation = new Operand((left, right) -> {
                BigDecimal leftPart = left.apply();
                BigDecimal rightPart = right.apply();
                return leftPart.multiply(rightPart);
            });
            multiplyOperation.setLeftOperand(leftOperand);
            mainOperator.setRightOperand(multiplyOperation);
            lastOperator = multiplyOperation;
        }
        operandBuilder = new StringBuilder();
        rowBuilder.append("*");
        display.setText(display.getText().toString() + "*");
    }

    public void addDivideOperation(View view) {
        if (operandBuilder.toString().endsWith(".") || operandBuilder.length() == 0) {
            return;
        }
        double number = Double.parseDouble(operandBuilder.toString());
        Operand leftOperand = new Operand((l, r) -> BigDecimal.valueOf(number));
        if (mainOperator == null) {
            mainOperator = new Operand((left, right) -> {
                BigDecimal leftPart = left.apply();
                BigDecimal rightPart = right.apply();
                return leftPart.divide(rightPart);
            });
            mainOperator.setLeftOperand(leftOperand);
            lastOperator = mainOperator;
        } else {
            Operand multiplyOperation = new Operand((left, right) -> {
                BigDecimal leftPart = left.apply();
                BigDecimal rightPart = right.apply();
                return leftPart.divide(rightPart);
            });
            multiplyOperation.setLeftOperand(leftOperand);
            mainOperator.setRightOperand(multiplyOperation);
            lastOperator = multiplyOperation;
        }
        operandBuilder = new StringBuilder();
        rowBuilder.append("/");
        display.setText(display.getText().toString() + "/");
    }

    public void resolveOperation(View view) {
        if (mainOperator == null || operandBuilder.toString().endsWith(".") || operandBuilder.length() == 0) {
            return;
        }
        double number = Double.parseDouble(operandBuilder.toString());
        lastOperator.setRightOperand(new Operand((l, r) -> BigDecimal.valueOf(number)));
        BigDecimal result = mainOperator.apply();
        display.setText(String.valueOf(result));

        rowBuilder.append("=").append(result);
        addToHistory(rowBuilder.toString());
        rowBuilder = new StringBuilder();
        rowBuilder.append(result);
        operandBuilder = new StringBuilder(String.valueOf(result));
        mainOperator = null;
        lastOperator = null;
        showHistory();
    }

    public void clearAll(View view) {
        mainOperator = null;
        operandBuilder = new StringBuilder();
        rowBuilder = new StringBuilder();
        display.setText("");
    }

    private void appendToInterface(CharSequence text) {
        operandBuilder.append(text);
        rowBuilder.append(text);
    }

    private void showHistory() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; ; ++i) {
            String row = getPreferences(MODE_PRIVATE).getString(String.valueOf(i), System.lineSeparator());
            if (System.lineSeparator().equals(row)) {
                break;
            }
            builder.append(row).append(System.lineSeparator());
        }
        historyView.setText(builder.toString());
    }

    private void addToHistory(String row) {
        int lastNumber = getPreferences(MODE_PRIVATE).getInt(LAST_NUMBER_ID, 0);
        if(lastNumber < 6){
            getPreferences(MODE_PRIVATE).edit()
                    .putString(String.valueOf(lastNumber), row)
                    .putInt(LAST_NUMBER_ID, (lastNumber + 1))
                    .apply();
            return;
        }
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for(int i = 0; i < 5; ++i){
            editor.putString(String.valueOf(i), preferences.getString(String.valueOf(i+1), null));
        }
        editor.putString(String.valueOf(5), row)
                .apply();
    }
}