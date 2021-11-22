package com.example.beadando;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.List;

public class Cell extends androidx.appcompat.widget.AppCompatTextView {

    private MainActivity context;
    private Grid grid;

    public static final int BOMB = -1;
    public static final int BLANK = 0;

    private Integer value = BLANK;
    private List<Cell> neighbours;
    private Boolean isRevealed = false;
    private Boolean isFlagged = false;

    public Cell(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setContext(context);
    }

    public Cell(Context context, AttributeSet attrs) {
        super(context, attrs);
        setContext(context);
    }

    public Cell(Context context,Grid grid, int width) {
        super(context);
        setContext(context);
        setGrid(grid);
        init(width);
    }


    private void setContext(Context context) {
        if (context.getClass() == MainActivity.class) {
            this.context = (MainActivity) context;
        }
    }

    private void init(int width) {
        setTextSize(18);
        setOnClickListener(v -> {
            onClick();
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, width);
        setLayoutParams(layoutParams);
        setTextColor(Color.BLACK);
        setBackgroundColor(Color.GRAY);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setGravity(Gravity.CENTER);
        setBackground(ContextCompat.getDrawable(context, R.drawable.background));
    }

    public void onClick() {
        if (isRevealed == false && grid.getGameOver() == false){
            if (!grid.getFlag()) {
                reveal();
            } else {
                switchFlag();
            }
        }
        grid.checkSolution();
    }

    private void switchFlag() {
        isFlagged = !isFlagged;
        if(isFlagged){
            setText(R.string.flag);
            grid.setFlagCount(grid.getFlagCount()+1);
        }else {
            setText("");
            grid.setFlagCount(grid.getFlagCount()+1);
        }
    }

    private void reveal() {
        if(!isFlagged){
            isRevealed = true;
            if (value != null && value.equals(BOMB)) {
                setText(R.string.bomb);
                setBackground(ContextCompat.getDrawable(context, R.drawable.revealed_bomb));
                Toast toast = Toast.makeText(context, "Vesztettél!", Toast.LENGTH_LONG);
                toast.show();
                grid.setGameOver(true);
            } else {
                setBackground(ContextCompat.getDrawable(context, R.drawable.background_revealed));
                if(getBombCount() == 0){
                    revealNeighbours();
                }else {
                    setText(getBombCount().toString());
                }
            }
        }

    }

    private void revealNeighbours(){

        for (Cell cell: neighbours) {
            cell.onClick();
        }
    }

    private Integer getBombCount(){
        Integer count = 0;
        for (Cell cell: neighbours) {
            if(cell.getValue() == Cell.BOMB){
                count++;
            }
        }
        return count;
    }

    public Boolean isGoodSolution(){
        if(value==BOMB && isFlagged){
            return true;
        }
        if(value==BLANK && isRevealed){
            return true;
        }
        return false;
    }


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(Boolean revealed) {
        isRevealed = revealed;
    }

    public Boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(Boolean flagged) {
        isFlagged = flagged;
    }

    public void setNeighbours(List<Cell> neighbours) {
        this.neighbours = neighbours;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }
}
