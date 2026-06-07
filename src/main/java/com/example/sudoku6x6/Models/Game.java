package com.example.sudoku6x6.Models;

import java.util.ArrayList;

/**
 * Class responsible for the game´s logic.
 * @author Dylan-Tobar, Ricardo-Hallado
 * @version 1.0
 *
 */

public class Game {
    private ArrayList<ArrayList<Integer>> tab;
    private boolean[][] clues;
    private int helps;

    /**
     * Constructor method
     */
    public Game(){
        tab = new ArrayList<>();
        clues = new boolean[6][6];
        helps = 3;
        tabGenerate();
        cluesGenerate();
    }

    /**
     * Method responsible for generating the game board
     */
    private void tabGenerate(){
        int base[][] = {{1, 2, 3, 4, 5, 6}, {4, 5, 6, 1, 2, 3},{2, 3, 1, 5, 6, 4},{5, 6, 4, 2, 3, 1},{3, 1, 2, 6, 4, 5},{6, 4, 5, 3, 1, 2}
        };
        for(int i=0; i<6; i++){
            ArrayList<Integer> rowList = new ArrayList<>();
            for(int j=0; j<6; j++){
                rowList.add(base[i][j]);
            }
            tab.add(rowList);
        }
    }

    /**
     * Method responsible for generating tracks randomly
     */
    private void cluesGenerate(){
        int rowRandom1, rowRandom2, columnRandom1, columnRandom2;

        for(int i=0;i<6;i+=2){
            for(int j=0;j<6;j+=3){
                rowRandom1 = (int)(Math.random()*2);
                columnRandom1 = (int)(Math.random()*3);
                do {
                    rowRandom2 = (int) (Math.random() * 2);
                    columnRandom2 = (int) (Math.random() * 3);
                }while(rowRandom1==rowRandom2 && columnRandom1 == columnRandom2);

                clues[i+rowRandom1][j+columnRandom1] = true;
                clues[i+rowRandom2][j+columnRandom2] = true;
            }
        }
    }

    /**
     * A method that checks whether a number can be entered into a specific cell.
     * @param row the row index of the cell.
     * @param col the column index of the cell.
     * @param val the number to validate.
     * @return true if the number is valid in that position or false.
     */
    public boolean validNum(int row, int col, int val){
        int rowB = (row/2)*2;
        int colB = (col/3)*3;

        for(int j=0;j<6;j++){
            if(j != col && tab.get(row).get(j) == val){
                return false;
            }
        }

        for(int i=0;i<6;i++){
            if(i != row && tab.get(i).get(col)==val){
                return false;
            }
        }

        for(int i=rowB;i<rowB+2;i++){
            for (int j = colB; j < colB + 3; j++) {
                if ((i != row || j != col) && tab.get(i).get(j) == val) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * a method that checks the player's progress and provides assistance
     */
    public int helpUser(int rowH, int colH){
        helps --;
        for(int i=1;i<=6;i++){
            if(validNum(rowH,colH,i)){
                return i;
            }
        }
        return 0;
    }

    /**
     * Checks if the player has completed the board by verifying
     * @return true if the board is fully filled, false otherwise
     */
    public boolean winUser(){
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                if(tab.get(i).get(j) == 0){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the value stored in a specific cell of the board.
     * @param row the row index.
     * @param col the column index.
     * @return the integer value at that position.
     */
    public int getVal(int row, int col){
        return tab.get(row).get(col);
    }

    /**
     * Checks whether a specific cell is a clue (pre-filled, non-editable).
     * @param row the row index.
     * @param col the column index.
     * @return true if the cell is a clue, false if it is editable.
     */
    public boolean isClue(int row, int col){
        return clues[row][col];
    }

    /**
     * Sets a value in a specific cell of the board.
     * @param row the row index.
     * @param col the column index.
     * @param val the value to set.
     */
    public void setVal(int row, int col, int val){
        tab.get(row).set(col, val);
    }

}
