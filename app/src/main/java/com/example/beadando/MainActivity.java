package com.example.beadando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Grid grid;

    TextView flagSwitchView;
    View popupView;
    PopupWindow popupWindow;
    EditText row;
    EditText col;
    EditText bomb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
        flagSwitchView = (TextView) findViewById(R.id.flagSwitch);


        grid = new Grid(this);
        grid.generateCells(14, 18, 20);
        layout.addView(grid);

    }

    public void flagSwitch(View v) {
        grid.setFlag(!grid.getFlag());
        if (grid.getFlag()) {
            flagSwitchView.setText(R.string.flag);
        }else {
            flagSwitchView.setText(R.string.bomb);
        }
    }

    public void newGame(View v){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.new_game, null);

        EditText row = (EditText) popupView.findViewById(R.id.rowInput);
        EditText col = (EditText) popupView.findViewById(R.id.colInput);
        EditText bomb = (EditText) popupView.findViewById(R.id.bombInput);

        row.setText(grid.getRow_Count().toString());
        col.setText(grid.getCol_Count().toString());
        bomb.setText(grid.getBombCount().toString());


        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });


    }

    public void newGameOk(View v){
        EditText row = (EditText) popupView.findViewById(R.id.rowInput);
        EditText col = (EditText) popupView.findViewById(R.id.colInput);
        EditText bomb = (EditText) popupView.findViewById(R.id.bombInput);

        grid.generateCells(
                Integer.parseInt(col.getText().toString()),
                Integer.parseInt(row.getText().toString()),
                Integer.parseInt(bomb.getText().toString()));
        popupWindow.dismiss();
    }



}