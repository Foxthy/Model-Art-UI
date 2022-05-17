package com.example.modelartui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView[] textViews = new TextView[5];
    private int[] originalColor = new int[5];
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        seekbarchange();
        getOriginalColors();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void Anhxa() {
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        textViews[0] = (TextView) findViewById(R.id.tv1);
        textViews[1] = (TextView) findViewById(R.id.tv2);
        textViews[2] = (TextView) findViewById(R.id.tv3);
        textViews[3] = (TextView) findViewById(R.id.tv4);
        textViews[4] = (TextView) findViewById(R.id.tv5);

    }

    private void seekbarchange() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setPanelsColors(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setPanelsColors(int progressChanged) {
        for (int i = 0; i < textViews.length; i++) {
            int color = originalColor[i];
            if (color != 0xffffffff && color != 0xff888888) {
                int invertColor = (0xFFFFFF - color) | 0xFF000000;
                int r = (color >> 16) & 0x000000FF;
                int g = (color >> 8) & 0x000000FF;
                int b = (color >> 0) & 0x000000FF;
                int invr = (invertColor >> 16) & 0x000000FF;
                int invg = (invertColor >> 8) & 0x000000FF;
                int invb = (invertColor >> 0) & 0x000000FF;
                int newColor = Color.rgb(
                        (int) (r + ((invr - r) * (progressChanged / 100f))),
                        (int) (g + ((invg - g) * (progressChanged / 100f))),
                        (int) (b + ((invb - b) * (progressChanged / 100f))));
                Log.i("R", " Red: " + Color.red(newColor));
                Log.i("G", " Green: " + Color.green(newColor));
                Log.i("B", " Blue: " + Color.blue(newColor));
                Log.i("RI", " Red I: " + invr);
                Log.i("GI", " Green I: " + invg);
                Log.i("BI", " Blue I: " + invb);
                Log.i("COLOR", "Color: id: " + i + " , " + newColor);
                textViews[i].setBackgroundColor(newColor);
            }
        }
    }

    private void getOriginalColors() {
        for (int i = 0; i < textViews.length; i++) {
            originalColor[i] = ((ColorDrawable) textViews[i].getBackground())
                    .getColor();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu) {
            Dialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Dialog() {
        String URL = "http://www.moma.org";
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Inspired by the works of artists such as" +
                " Piet Mondrian and Ben Nicholson.");
        alertDialog.setMessage("Click below to learn more");
        alertDialog.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.setPositiveButton("Visit MOMA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                startActivity(intent);
            }
        });

        alertDialog.show();
    }
}