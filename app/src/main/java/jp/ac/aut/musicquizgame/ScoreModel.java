package jp.ac.aut.musicquizgame;

import java.util.Date;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ScoreModel extends RealmObject{
    @PrimaryKey
    Long id = 0L;
    Date date = new Date();
    Long level = 0L;
    Long score = 0L;
    String genre = "";

}
