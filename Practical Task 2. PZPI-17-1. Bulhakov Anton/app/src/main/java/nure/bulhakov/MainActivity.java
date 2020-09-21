package nure.bulhakov;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private int[] colors = {128, 128, 128};

    private SeekBar[] bars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bars = new SeekBar[]{findViewById(R.id.red_bar), findViewById(R.id.green_bar), findViewById(R.id.blue_bar)};
        Stream.of(bars).forEach(bar -> bar.setOnSeekBarChangeListener(this));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        colors = Stream.of(bars).mapToInt(SeekBar::getProgress).toArray();
        View circle = findViewById(R.id.circle);

        GradientDrawable drawable = (GradientDrawable) circle.getBackground().getCurrent();
        drawable.setColor(Color.rgb(colors[0], colors[1], colors[2]));
        //circle.setBackgroundColor(Color.rgb(colors[0], colors[1], colors[2]));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        /*SeekBar[] bars = new SeekBar[]{findViewById(R.id.red_bar), findViewById(R.id.green_bar), findViewById(R.id.blue_bar)};
        colors = Stream.of(bars).mapToInt(SeekBar::getProgress).toArray();
        View circle = findViewById(R.id.circle);
        circle.setBackgroundColor(colors[0] << 16 + colors[1] << 8 + colors[2]);*/
    }
}