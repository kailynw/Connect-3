package com.williams.kailyn.connectfour;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean red= true;
    private boolean winnerAlready=false;
    private int[] chosenPiece={-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private int[][] board= {{0,0,0},
                            {0,0,0},
                            {0,0,0}};
    private int catGameCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"RED'S Turn", Toast.LENGTH_LONG).show();

    }

    public void dropIn(View v) {
        ImageView gamePiece = (ImageView) v;
        int tagNumber= Integer.parseInt(gamePiece.getTag().toString());

        //Row and Column to match index for 2D array and Grid
        int row = tagNumber/3;
        int col= ((row *3)-tagNumber)*-1;

        if(winnerAlready||playerWinner()) {
            winnerAlready = true;
            return;
        }



        //Checks if the spot is not filled yet
        if(chosenPiece[tagNumber]==-1) {
            if (red) {
                chosenPiece[tagNumber] = tagNumber;
                board[row][col]=1;
                redAnimation(gamePiece);
                if(playerWinner())
                    return;
                Toast.makeText(getApplicationContext(), "BLUE'S Turn", Toast.LENGTH_LONG).show();
                red = false;
                catGameCount++;

            } else {
                chosenPiece[tagNumber] = tagNumber;
                board[row][col]=2;
                blueAnimation(gamePiece);
                if(playerWinner())
                    return;
                Toast.makeText(getApplicationContext(), "RED'S Turn", Toast.LENGTH_LONG).show();
                red = true;
                catGameCount++;
            }

            if(catGameCount==9){
                // One second delay for restarting
                Runnable runnable= new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        startActivity(getIntent());
                    }
                };
                Handler handler= new Handler();
                handler.postDelayed(runnable,1000);
            }
        }
    }

    private boolean playerWinner(){
        //RED= 1
        //BLUE= 2
        Connect connect= new Connect(board);

        if(connect.gameWinner(1)){
            textAnimation(1);
            return true;
        }
        else if(connect.gameWinner(2)){
            textAnimation(2);
            return true;
        }

        else
            return false;
    }


    public void restartGame(View v){
        finish();
        startActivity(getIntent());
    }








    //**********ANIMATIONS************//


    private void redAnimation(ImageView red){
        red.setTranslationY(-1000f);
        red.setImageResource(R.drawable.red);
        red.animate().translationY(0);

    }

    private void blueAnimation(ImageView blue){
        blue.setTranslationY(-1000f);
        blue.setImageResource(R.drawable.blue);
        blue.animate().translationY(0);
    }


    private void textAnimation(int playerValue){

        TextView winnerText= findViewById(R.id.winnerText);
        winnerText.setTranslationY(-1000f);
        Animation spin= AnimationUtils.loadAnimation(this,R.anim.bounce);
        Button restartButton= findViewById(R.id.restartButton);


        if(playerValue==1){
            winnerText.setText("RED IS THE WINNER");
            winnerText.setTextColor(Color.RED);
            winnerText.startAnimation(spin);
            winnerText.animate().translationY(0);
            restartButton.setVisibility(View.VISIBLE);

        }
        else if(playerValue==2){
            winnerText.setText("BLUE IS THE WINNER");
            winnerText.setTextColor(Color.BLUE);
            winnerText.startAnimation(spin);
            winnerText.animate().translationY(0);
            restartButton.setVisibility(View.VISIBLE);

        }

    }



}
