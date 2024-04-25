package jp.ac.aut.musicquizgame;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView dateText;
    TextView levelDataText;
    TextView scoreDataText;
    TextView genreDateText;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        dateText = (TextView) itemView.findViewById(R.id.dateText);
        levelDataText = (TextView) itemView.findViewById(R.id.levelDataText);
        scoreDataText = (TextView) itemView.findViewById(R.id.scoreDataText);
        genreDateText = (TextView) itemView.findViewById(R.id.genreDataText);
    }
}
