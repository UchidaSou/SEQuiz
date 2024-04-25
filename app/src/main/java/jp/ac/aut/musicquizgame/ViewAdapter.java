package jp.ac.aut.musicquizgame;

import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class ViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private RealmResults<ScoreModel> realmResults;

    public ViewAdapter(RealmResults<ScoreModel> realmResult) {
        this.realmResults = realmResult;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dateText.setText(
                DateFormat.format("yyyy/MM/dd kk:mm", realmResults.get(position).date)
        );
        holder.levelDataText.setText(realmResults.get(position).level.toString());
        holder.scoreDataText.setText(realmResults.get(position).score.toString());
        holder.genreDateText.setText(realmResults.get(position).genre);
        holder.itemView.setBackgroundResource(R.drawable.border);
    }

    @Override
    public int getItemCount() {
        return realmResults.size();
    }
}
