package com.example.beadando;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid extends GridLayout {

    private Boolean flag = false;
    private Boolean gameOver = false;
    private Integer bombCount;
    private Integer flagCount;
    private Integer row_Count;
    private Integer col_Count;

    private Cell cells[][];

    private MainActivity context;

    public Grid(Context context) {
        super(context);
        init(context);
    }

    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Grid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setAttributes();
        this.context = (MainActivity) context;
    }

    private void clearGrid() {
        removeAllViews();
    }

    private void setAttributes() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        setLayoutParams(layoutParams);
    }

    public void generateBombs(int bombCount) {
        Random rand = new Random();
        for (int i = 0; i < bombCount; ) {
            Integer column = rand.nextInt(cells.length);
            Integer row = rand.nextInt(cells[column].length);
            if (cells[column][row].getValue() == Cell.BLANK) {
                cells[column][row].setValue(Cell.BOMB);
                i++;
            }
        }
    }

    public Cell[][] generateCells(int columnCount, int rowCount, int bombCount) {
        setFlagCount(0);
        setBombCount(bombCount);
        setRow_Count(rowCount);
        setCol_Count(columnCount);
        gameOver = false;
        clearGrid();
        setColumnCount(columnCount);
        cells = new Cell[columnCount][rowCount];

        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                Cell cell = new Cell(context, this, 75);
                cells[column][row] = cell;
                addView(cell);
            }
        }
        generateBombs(bombCount);
        setCellNeighbours();
        return cells;
    }

    private void setCellNeighbours() {

        for (int column = 0; column < col_Count; column++) {
            for (int row = 0; row < row_Count; row++) {
                Cell cell = cells[column][row];
                cell.setNeighbours(getNeighbours(column, row));
            }
        }
    }

    private List<Cell> getNeighbours(int col, int row) {
        List<Cell> neighbours = new ArrayList<>();


        List<Cell> cellsList = new ArrayList<>();
        cellsList.add(cellAt(col - 1, row));
        cellsList.add(cellAt(col + 1, row));
        cellsList.add(cellAt(col - 1, row - 1));
        cellsList.add(cellAt(col, row - 1));
        cellsList.add(cellAt(col + 1, row - 1));
        cellsList.add(cellAt(col - 1, row + 1));
        cellsList.add(cellAt(col, row + 1));
        cellsList.add(cellAt(col + 1, row + 1));

        for (Cell cell : cellsList) {
            if (cell != null) {
                neighbours.add(cell);
            }
        }
        return neighbours;
    }

    public Cell cellAt(int col, int row) {
        if (col < 0 || col >= col_Count || row < 0 || row >= row_Count) {
            return null;
        }
        return cells[col][row];
    }

    public void checkSolution() {
        Boolean goodSolution = true;
        for (int row = 0; row < row_Count; row++) {
            for (int column = 0; column < col_Count; column++) {
                Cell cell = cells[column][row];
                if(!cell.isGoodSolution()){
                    goodSolution = false;
                }
            }
        }
        if(goodSolution){
            Toast toast = Toast.makeText(context, "Nyertél!", Toast.LENGTH_LONG);
            toast.show();
            gameOver = true;
        }
    }


    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }


    public Integer getBombCount() {
        return bombCount;
    }

    public void setBombCount(Integer bombCount) {
        this.bombCount = bombCount;
    }

    public Integer getFlagCount() {
        return flagCount;
    }

    public void setFlagCount(Integer flagCount) {
        this.flagCount = flagCount;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }


    public Integer getRow_Count() {
        return row_Count;
    }

    public void setRow_Count(Integer row_Count) {
        this.row_Count = row_Count;
    }

    public Integer getCol_Count() {
        return col_Count;
    }

    public void setCol_Count(Integer col_Count) {
        this.col_Count = col_Count;
    }
}
