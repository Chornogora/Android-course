package nure.bulhakov;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private int[] colors;

    private SeekBar[] bars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bars = new SeekBar[]{findViewById(R.id.red_bar), findViewById(R.id.green_bar), findViewById(R.id.blue_bar)};
        Stream.of(bars).forEach(bar -> bar.setOnSeekBarChangeListener(this));
        setCircleBackgroundColor();
        setColorDescription();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setCircleBackgroundColor();
        setColorDescription();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        /*Doesn't need to be realized*/
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        /*Doesn't need to be realized*/
    }

    private void setCircleBackgroundColor(){
        colors = Stream.of(bars).mapToInt(SeekBar::getProgress).toArray();
        View circle = findViewById(R.id.circle);
        GradientDrawable drawable = (GradientDrawable) circle.getBackground().getCurrent();
        drawable.setColor(Color.rgb(colors[0], colors[1], colors[2]));
    }

    private void setColorDescription(){
        String description = String.format("0x%H%H%H", colors[0], colors[1], colors[2]);
        TextView descriptionView = findViewById(R.id.color_text);
        descriptionView.setText(description);
    }
}