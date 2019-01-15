package com.whisperict.catchthelegend.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.model.entities.Legend;
import com.whisperict.catchthelegend.controllers.managers.apis.legend.LegendApiManager;

import java.util.ArrayList;

public class CompendiumAdapter extends RecyclerView.Adapter<CompendiumAdapter.ViewHolder> {
    private ArrayList<Legend> legends;
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
        Legend legend = legends.get(i);
        viewHolder.legendNameTextView.setText(legend.getName());
        Picasso.get().load(LegendApiManager.getInstance().getLegendImageUrl(legend.getName())).into(viewHolder.legendImage);

        String rarity = legend.getRarity();
        switch (rarity) {
            case "common":
                viewHolder.background.setImageResource(R.mipmap.eenster);
                break;

            case "uncommon":
                viewHolder.background.setImageResource(R.mipmap.tweesterren);
                break;

            case "rare":
                viewHolder.background.setImageResource(R.mipmap.driesterren);
                break;

            case "legend":
                viewHolder.background.setImageResource(R.mipmap.viersterren);
                break;

            case "ultra_legend":
                viewHolder.background.setImageResource(R.mipmap.vijfsterren);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return legends.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView legendNameTextView;
        private ImageView legendImage;
        private ImageView background;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            legendNameTextView = itemView.findViewById(R.id.legend_name_text_view);
            legendImage = itemView.findViewById(R.id.legend_compendium_image_view);
            background = itemView.findViewById(R.id.compendiumbackground);
            itemView.setOnClickListener(view -> {
                if(onItemClickListener != null){
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }
}
