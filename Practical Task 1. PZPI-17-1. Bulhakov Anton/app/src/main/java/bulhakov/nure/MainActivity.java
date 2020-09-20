package bulhakov.nure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
    }

    public void addNumber(View view) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table);
        TableRow tableRow = new TableRow(this);
        int number = addNumber();
        String dateFormatted = getDateFormatted();

        TextView numberTextView = generatedTableCell(String.valueOf(number));
        TextView dateTextView = generatedTableCell(dateFormatted);

        tableRow.addView(numberTextView);
        tableRow.addView(dateTextView);

        tableLayout.addView(tableRow);
    }

    private String getDateFormatted() {
        Date date = new Date(System.currentTimeMillis());
        return DateFormat.format("hh:mm dd.MM.yyyy", date).toString();
    }

    private int addNumber(){
        CheckBox checkBox = (CheckBox) findViewById(R.id.negative_checkbox);
        int number = random.nextInt();
        number = (number > 0) ? number : -number;
        return checkBox.isChecked() ? -number : number;
    }

    private TextView generatedTableCell(String text){
        TextView cell = new TextView(this);
        cell.setPadding(3, 0, 0, 0);
        cell.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        cell.setText(text);
        return cell;
    }
}