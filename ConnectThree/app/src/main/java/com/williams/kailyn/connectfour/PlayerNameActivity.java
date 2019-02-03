package com.williams.kailyn.connectfour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlayerNameActivity extends AppCompatActivity {
    private EditText player1Text;
    private EditText player2Text;
    private Button playButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        player1Text= findViewById(R.id.player1Text);
        player2Text= findViewById(R.id.player2Text);
        playButton= findViewById(R.id.playButton);


        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emptyTextFields())
                    return;

                Intent mainIntent= new Intent(PlayerNameActivity.this, MainActivity.class);
                mainIntent.putExtra("PLAYER1NAME", player1Text.getText().toString());
                mainIntent.putExtra("PLAYER2NAME", player2Text.getText().toString());
                startActivity(mainIntent);

            }
        });
    }

    private boolean emptyTextFields(){
        if(TextUtils.isEmpty(player1Text.getText().toString())|| TextUtils.isEmpty(player2Text.getText().toString())) {
            Toast.makeText(getApplicationContext(),"Empty Text Field", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (player1Text.getText().toString().equalsIgnoreCase(player2Text.getText().toString())){
            Toast.makeText(getApplicationContext(),"Choose different names!",Toast.LENGTH_LONG).show();
            return true;
        }
        else
            return false;

    }

}
