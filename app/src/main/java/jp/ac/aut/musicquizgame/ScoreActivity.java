package jp.ac.aut.musicquizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ScoreActivity extends AppCompatActivity {
    private Button backButton;
    private TextView resultTextView;
    private TextView resultRightPerQuizText;
    private TextView levelText;
    private TextView genreText;
    private int resultScore;
    private String resultRightPerQuiz;
    private int resultRightCount;
    private int level;
    private Realm realm;
    private Button saveButton;
    private Long id;
    private Date date;
    private RecyclerView recyclerView;
    private boolean saveFlag = true;
    private String genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        realm = Realm.getDefaultInstance();

        resultTextView = (TextView)findViewById(R.id.resultText);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        resultScore = pref.getInt("SCORE",0);
        resultTextView = (TextView)findViewById(R.id.resultText);
        resultTextView.setText("SCORE : "+Integer.toString(resultScore));
        resultRightCount = pref.getInt("RIGHTCOUNT",0);
        level = pref.getInt("LEVEL",0);
        genre = pref.getString("GENRE","");
        resultRightPerQuizText = (TextView)findViewById(R.id.rightCountText);
        resultRightPerQuiz = Integer.toString(resultRightCount) + " / " + Integer.toString(level*3);
        resultRightPerQuizText.setText("正解数 : " + resultRightPerQuiz);
        levelText = (TextView) findViewById(R.id.levelText);
        levelText.setText("level: " + Integer.toString(level));
        genreText = (TextView) findViewById(R.id.genreText);
        genreText.setText("ジャンル"+genre);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(v ->{
            Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
            startActivity(intent);
        });

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v ->{
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if(!saveFlag){
                        return;
                    }
                    id = (Long) realm.where(ScoreModel.class).max("id");
                    if(id == null){
                        id = 0L;
                    }
                    Long nextId = id + 1L;
                    date = new Date();
                    ScoreModel scoreModel = realm.createObject(ScoreModel.class,nextId);
                    scoreModel.date = date;
                    scoreModel.level = Long.valueOf(level);
                    scoreModel.score = Long.valueOf(resultScore);
                    scoreModel.genre = genre;
                    saveFlag = false;
                    Toast.makeText(getApplicationContext(),"保存しました",Toast.LENGTH_SHORT).show();
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            });
            });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
    @Override
    protected void onStart(){
        super.onStart();
        RealmResults<ScoreModel> realmResult = realm.where(ScoreModel.class)
                .findAll()
                .sort("id", Sort.DESCENDING);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ViewAdapter(realmResult));
    }
}