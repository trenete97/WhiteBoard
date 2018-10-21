package com.example.mario.whiteboard;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class WhiteboardActivity extends AppCompatActivity {
    private PaintView paintView;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.paintView = (PaintView)findViewById(R.id.activity_my_view_whiteboard);
        SeekBar simpleSeekBar;
        name = getIntent().getStringExtra("name");
        simpleSeekBar=(SeekBar)findViewById(R.id.simpleSeekBar);
        // perform seek bar change listener event used for getting the progress value
        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int newThickness = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                newThickness = progress;
                paintView.canvasThicknessChange(newThickness);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
               // Toast.makeText(WhiteboardActivity.this, "Seek bar progress is :" + newThickness,
                   //     Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        paintView.setClient(name);
    }

    public void buttonClearClick(View v) {
        Log.i("INFO", "Clear button clicked");
        paintView.sendClear();
        paintView.clearCanvas();
    }

    public void setEraser(View v){
        paintView.setEraser();
    }

    public void buttonPress(View v) {
        Button button = null;
        switch (v.getId()) {
            case R.id.btn1:
                button = findViewById(R.id.btn1);
                break;
            case R.id.btn2:
                button = findViewById(R.id.btn2);
                break;
            case R.id.btn3:
                button = findViewById(R.id.btn3);
                break;
            case R.id.btn4:
                button = findViewById(R.id.btn4);
                break;
            case R.id.btn5:
                button = findViewById(R.id.btn5);
                break;
        }
        button.setSelected(true);
        ColorDrawable buttonColor = (ColorDrawable) button.getBackground();
        int colorId = buttonColor.getColor();
        paintView.changeColor(colorId);
    }

}
