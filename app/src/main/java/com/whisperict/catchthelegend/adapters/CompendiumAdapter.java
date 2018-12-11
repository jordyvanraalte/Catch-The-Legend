package com.whisperict.catchthelegend.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.entities.Legend;

import java.util.ArrayList;

public class CompendiumAdapter extends RecyclerView.Adapter<CompendiumAdapter.ViewHolder> {
    private ArrayList<Legend> legends = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private Context context;

    public CompendiumAdapter(Context context, ArrayList<Legend> legends) {
        this.context = context;
        this.legends = legends;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CompendiumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.compendium_recyclerview_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompendiumAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return legends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
