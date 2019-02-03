package com.williams.kailyn.connectfour;

import android.content.Intent;
import android.graphics.Color;
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

    private boolean player1Turn = true;
    private boolean winnerAlready=false;
    private int[] chosenPiece={-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private int[][] board= {{0,0,0},
                            {0,0,0},
                            {0,0,0}};
    private int catGameCount=0;

    private String player1Name;
    private String player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Data from PlayerNameActivity
        Bundle bundle= getIntent().getExtras();
        if(bundle!=null){
            player1Name= bundle.getString("PLAYER1NAME");
            player2Name= bundle.getString("PLAYER2NAME");
        }

        Toast.makeText(getApplicationContext(),player1Name +"'s Turn", Toast.LENGTH_LONG).show();

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
            if (player1Turn) {
                chosenPiece[tagNumber] = tagNumber;
                board[row][col]=1;
                redAnimation(gamePiece);
                if(playerWinner())
                    return;
                Toast.makeText(getApplicationContext(), player2Name+ "'s Turn", Toast.LENGTH_LONG).show();
                player1Turn = false;
                catGameCount++;

            } else {
                chosenPiece[tagNumber] = tagNumber;
                board[row][col]=2;
                blueAnimation(gamePiece);
                if(playerWinner())
                    return;
                Toast.makeText(getApplicationContext(), player1Name+"'s Turn", Toast.LENGTH_LONG).show();
                player1Turn = true;
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
        //Player1= 1
        //Player2= 2
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
    public void changePlayer(View v){
        finish();
        startActivity(new Intent(getApplicationContext(),PlayerNameActivity.class));
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
        Animation bounce= AnimationUtils.loadAnimation(this,R.anim.bounce);
        Button restartButton= findViewById(R.id.restartButton);
        Button changePlayerButton= findViewById(R.id.changePlayerButton);



        if(playerValue==1){
            winnerText.setText(player1Name.toUpperCase()+ " IS THE WINNER");
            winnerText.setTextColor(Color.RED);
            winnerText.startAnimation(bounce);
            winnerText.animate().translationY(0);
            restartButton.setVisibility(View.VISIBLE);
            changePlayerButton.setVisibility(View.VISIBLE);


        }
        else if(playerValue==2){
            winnerText.setText(player2Name.toUpperCase() +" IS THE WINNER");
            winnerText.setTextColor(Color.BLUE);
            winnerText.startAnimation(bounce);
            winnerText.animate().translationY(0);
            restartButton.setVisibility(View.VISIBLE);
            changePlayerButton.setVisibility(View.VISIBLE);

        }

    }



}
