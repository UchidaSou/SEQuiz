package jp.ac.aut.musicquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class LevelSelectActivity extends AppCompatActivity {
    private Button gameStart_button;
    private Button back_button;
    private Spinner Genre_spinner;
    private Spinner Level_spinner;
    private String genreItem;
    private int levelItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
        //ゲームスタートボタン
        gameStart_button = findViewById(R.id.gameStart_button);
        gameStart_button.setOnClickListener(v -> {
            onSaveTapped();
            Intent intent = new Intent(LevelSelectActivity.this, PlayGameActivity.class);
            startActivity(intent);
        });

        //もどるボタン処理
        back_button = findViewById(R.id.BackButton);
        back_button.setOnClickListener(v -> {
            Intent intent = new Intent(LevelSelectActivity.this, MainActivity.class);
            startActivity(intent);
        });

        //ジャンル選択プルダウン
        Genre_spinner = (Spinner) findViewById(R.id.Genre_spinner);
        ArrayAdapter<String> genreSelect = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        genreSelect.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSelect.add("楽器");
        genreSelect.add("効果音");
        genreSelect.add("環境音");
        Genre_spinner.setAdapter(genreSelect);
        Genre_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner) adapterView;
                genreItem = (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //レベル選択プルダウン
        Level_spinner = (Spinner) findViewById(R.id.Level_spinner);
        ArrayAdapter<String> levelSelect = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        levelSelect.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(int i=0;i<5;i++){
            levelSelect.add(Integer.toString(i+1));
        }
        Level_spinner.setAdapter(levelSelect);
        Level_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Spinner spinner = (Spinner) adapterView;
                levelItem = Integer.valueOf(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void onSaveTapped(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("LEVEL",levelItem);
        editor.putString("GENRE",genreItem);
        editor.commit();
    }
}