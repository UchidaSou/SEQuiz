package jp.ac.aut.musicquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;


public class PlayGameActivity extends AppCompatActivity {

    private TextView qText;
    private TextView rightPq;
    private TextView scoreText;
    public Button[] answerBtn = null;
    public MediaPlayer ritghtMediaPlayer;
    private MediaPlayer notMediaPlayer;
    public MediaPlayer mediaPlayer;
    private FloatingActionButton Play_SE_button;

    private int rightAnswerCount=0;
    private int quizCount = 1;
    private String genre;
    private int level;
    private int score=0;

    private String[] fileList = null;
    private String rightPqText;
    private Quiz quiz = new Quiz();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        //スコア、正解数、問題カウントのテキストを設定
        scoreText = (TextView)findViewById(R.id.scoreText);
        scoreText.setText(Integer.toString(score));
        qText = (TextView)findViewById(R.id.q_text);
        qText.setText("Q"+Integer.toString(quizCount));
        //共有プリファレンスからジャンルとレベルを読み込む
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        genre = pref.getString("GENRE","");
        level = pref.getInt("LEVEL",1);
        AssetManager assetManager = getResources().getAssets();
        fileList = new String[level*5];
        try {
            int item = 0;
            for(int i=1;i<=level;i++){
                String[] file = getAssets().list(genre + "/" + Integer.toString(i));
                for(int j=0;j<file.length;j++){
                    fileList[item + j] = file[j];
                }
                item = item + file.length;
            }
            quiz.createQuiz(fileList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        rightPq = findViewById(R.id.rightPq);
        rightPq.setText(rightAnswerCount + "/" + level*3);
        //選択肢ボタンの設定
        answerBtn = new Button[4];
        answerBtn[0] = findViewById(R.id.answerBtn1);
        answerBtn[1] = findViewById(R.id.answerBtn2);
        answerBtn[2] = findViewById(R.id.answerBtn3);
        answerBtn[3] = findViewById(R.id.answerBtn4);

        for(int i=0;i<answerBtn.length;i++){
            Button btn = answerBtn[i];
            btn.setOnClickListener(v ->{
                mediaPlayer.stop();
                if(quiz.checkAnswer(btn.getText().toString(),quiz.rightAnswer)){
                    //正解の音声を再生
                    ritghtMediaPlayer.start();
                    //スコアと正解数を更新
                    score = score + level*50;
                    while (ritghtMediaPlayer.isPlaying()){
                        continue;
                    }
                }else{
                    //不正解の音声を再生
                    notMediaPlayer.start();
                    while (notMediaPlayer.isPlaying()){
                        continue;
                    }
                }
                rightPqText = Integer.toString(quiz.rightAnswerCount) + " / " + Integer.toString(level*3);
                //スコアテキストと正解数テキストを更新
                scoreText.setText(Integer.toString(score));
                rightPq.setText(rightPqText);
                if(quizCount == level*3){
                    //スコア、正解数、レベルを保存
                    SharedPreferences.Editor editor = pref.edit();;
                    editor.putInt("SCORE",score);
                    editor.putInt("RIGHTCOUNT",quiz.rightAnswerCount);
                    editor.putInt("LEVEL",level);
                    editor.putString("GENRE",genre);
                    editor.commit();
                    //スコア画面へ
                    Intent intent = new Intent(PlayGameActivity.this,ScoreActivity.class);
                    startActivity(intent);
                }else{
                    //クイズカウントを更新して次の問題へ
                    quizCount++;
                    qText.setText("Q"+Integer.toString(quizCount));
                    ArrayList<String> quizList = quiz.showNextQuiz(this, genre, Integer.toString(level), answerBtn);
                    for(int j=0;j<answerBtn.length;j++){
                        answerBtn[j].setText(quizList.get(j));
                    }
                }
            });
        }


        //再生ボタン
        Play_SE_button = findViewById(R.id.play_SE_button);
        Play_SE_button.setOnClickListener(v -> {
            mediaPlayer.start();
        });


        //もどるボタンS
        Button backButton = findViewById(R.id.BackButton2);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(PlayGameActivity.this,LevelSelectActivity.class);
            startActivity(intent);
        });

        ArrayList<String> quizList = quiz.showNextQuiz(this, genre, Integer.toString(level), answerBtn);
        for(int i=0;i<answerBtn.length;i++){
            answerBtn[i].setText(quizList.get(i).toString());
        }
    }


   @Override
    protected void onResume() {
        super.onResume();
        //正解、不正解の音声を読み込み
       AssetFileDescriptor assetFileDescriptor = null;
       try {
           assetFileDescriptor = getAssets().openFd("quiz_correct.mp3");
           ritghtMediaPlayer = new MediaPlayer();
           ritghtMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                   assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
           ritghtMediaPlayer.prepare();
           assetFileDescriptor = getAssets().openFd("quiz_wrong.mp3");
           notMediaPlayer = new MediaPlayer();
           notMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                   assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
           notMediaPlayer.prepare();
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

    }

    @Override
    protected void onPause() {
        super.onPause();
        //メディアをリリース
        ritghtMediaPlayer.release();
        notMediaPlayer.release();
        mediaPlayer.release();
    }


}
