package jp.ac.aut.musicquizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ScoreBoardActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Realm realm;
    private Button goMainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        realm = Realm.getDefaultInstance();
        RealmResults<ScoreModel> realmResult = realm.where(ScoreModel.class)
                .findAll()
                .sort("id", Sort.DESCENDING);
        recyclerView = (RecyclerView)findViewById(R.id.scoreBoard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ViewAdapter(realmResult));
        goMainBtn = findViewById(R.id.goMainBtn);
        goMainBtn.setOnClickListener(v ->{
            Intent intent = new Intent(ScoreBoardActivity.this,MainActivity.class);
            startActivity(intent);
        });
    }
}