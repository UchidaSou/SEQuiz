package jp.ac.aut.musicquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button playGameButton;
    private Button scoreButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //スタートボタン
        playGameButton = findViewById(R.id.playGamebutton);
        playGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,LevelSelectActivity.class);
            startActivity(intent);
        });
        scoreButton = (Button) findViewById(R.id.scoreBoardButton);
        scoreButton.setOnClickListener(v->{
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = pref.edit();;
            editor.putInt("SCORE",0);
            editor.putString("RIGHTPERQUIZ","");
            editor.putInt("LEVEL",0);
            editor.commit();
            Intent intent = new Intent(MainActivity.this,ScoreBoardActivity.class);
            startActivity(intent);
        });
    }

}