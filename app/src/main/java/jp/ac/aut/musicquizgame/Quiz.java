package jp.ac.aut.musicquizgame;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Quiz {
    // {"問題", "正解", "選択肢１", "選択肢２", "選択肢３"}
    String[][] quizData;
    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    String rightAnswer;
    int rightAnswerCount = 0;
    ArrayList<String> quiz = null;


    public void createQuiz(@NonNull String[] fileList){

        this.quizData = new String[fileList.length][5];
        //クイズ用配列を作成
        for(int i=0;i< fileList.length;i++){
            quizData[i][0] = fileList[i].substring(0,fileList[i].indexOf("."));
            quizData[i][1] = fileList[i].substring(0,fileList[i].indexOf("."));
        }
        //ダミー選択肢を設定
        for(int i=0;i< fileList.length;i++){
            int j=2;
            boolean flg;
            while(j<5){
                flg = false;
                Random r = new Random();
                int num = r.nextInt(fileList.length);
                String file = fileList[num].substring(0,fileList[num].indexOf("."));
                //同じ選択肢があれば入れない
                for (int k=1;k<j;k++){
                    if(file.equals(this.quizData[i][k])){
                        flg = true;
                    }
                }
                if(flg == true){
                    continue;
                }
                this.quizData[i][j] = fileList[num].substring(0,fileList[num].indexOf("."));
                j++;
            }
        }
        // quizDataからクイズ出題用のquizArrayを作成する
        for (int i = 0; i < this.quizData.length; i++) {

            // 新しいArrayListを準備
            ArrayList<String> tmpArray = new ArrayList<>();

            // クイズデータを追加
            tmpArray.add(quizData[i][0]);  // 問題
            tmpArray.add(quizData[i][1]);  // 正解
            tmpArray.add(quizData[i][2]);  // 選択肢１
            tmpArray.add(quizData[i][3]);  // 選択肢２
            tmpArray.add(quizData[i][4]);  // 選択肢３

            // tmpArrayをquizArrayに追加する
            this.quizArray.add(tmpArray);
        }
    }

    public ArrayList<String> showNextQuiz(@NonNull PlayGameActivity activity, String genre, String level, Button[] answerBtn) {
        // ランダムな数字を取得
        Random random = new Random();
        int randomNum = random.nextInt(this.quizArray.size());
        // randomNumを使って、quizArrayからクイズを一つ取り出す
        quiz = this.quizArray.get(randomNum);


        // 正解をrightAnswerにセット
        this.rightAnswer = quiz.get(1);
        //問題音声を設定
        try{
            boolean flg = false;
            AssetFileDescriptor assetFileDescriptor = null;
            for (int i=1;i<=Integer.valueOf(level);i++){
                if(flg == true){
                    break;
                }
                String[] files = activity.getAssets().list(genre + "/" + Integer.toString(i));
                for(String file :files){
                    if(file.equals(rightAnswer+".mp3")){
                        assetFileDescriptor = activity.getAssets().openFd(genre + "/" + Integer.toString(i) + "/" + rightAnswer + ".mp3");
                        flg = true;
                        break;
                    }
                }
            }

            activity.mediaPlayer = new MediaPlayer();
            activity.mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),assetFileDescriptor.getLength());
            activity.mediaPlayer.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // クイズ配列から問題文を削除
        quiz.remove(0);
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        // 正解と選択肢３つをシャッフル
        Collections.shuffle(quiz);
        // このクイズをquizArrayから削除
        quizArray.remove(randomNum);
        return quiz;
    }

    public boolean checkAnswer(String selectedAnswer, String answer){
        if(selectedAnswer.equals(answer)){
            rightAnswerCount++;
            return true;
        }else{
            return false;
        }
    }
}
