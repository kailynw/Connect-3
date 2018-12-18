package com.williams.kailyn.connectfour;
import java.util.Scanner;
public class Connect {
    private int[][] rowWinner;

    public Connect(int [][]rowWinner) {
        this.rowWinner=rowWinner;

    }


    private boolean rowWinner(int row, int playerValue) {
        boolean winner= false;
        for (int i = 0; i < rowWinner[row].length; i++) {
            if(rowWinner[row][i]==playerValue)
                winner=true;
            else return false;
        }
        return winner;
    }


    private boolean colWinner(int col,int playerValue) {
        boolean winner= false;
        for (int i = 0; i < rowWinner.length; i++) {
            if(rowWinner[i][col]==playerValue)
                winner=true;
            else return false;
        }
        return winner;

    }


    private boolean diagWinnner(int playerValue) {
        boolean winner= false;
        for (int row = 0; row < rowWinner.length; row++) {
            for (int col = rowWinner.length - 1; col >= 0; col--) {
                if(rowWinner[row][col]==playerValue) {
                    winner = true;
                    row++;
                }
                else return false;
            }
        }
        return winner;
    }


    private boolean winnerOtherDiag(int playerValue) {

        boolean winner = false;
        for (int row = 0; row < rowWinner.length; row++) {
            for (int col = 0; col < rowWinner.length; col++) {
                if(rowWinner[row][col]==playerValue) {
                    winner = true;
                    row++;
                }
                else return false;
            }
        }
        return winner;
    }

    public boolean gameWinner(int playerValue) {

        //Checks if all columns and rows have a winner
        for(int i=0; i<3;i++) {
            if (colWinner(i, playerValue) || rowWinner(i, playerValue)) {
                return true;
            }
        }

        if (winnerOtherDiag(playerValue) || diagWinnner(playerValue))
            return true;

        else return false;
    }




}