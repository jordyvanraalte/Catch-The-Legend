package com.whisperict.catchthelegend.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whisperict.catchthelegend.R;
import com.whisperict.catchthelegend.model.entities.Quest;
import java.util.ArrayList;

public class QuestAdapter extends RecyclerView.Adapter<QuestAdapter.ViewHolder> {
    private ArrayList<Quest> quests;
    private QuestAdapter.OnItemClickListener onItemClickListener;
    private Context context;

    public QuestAdapter(Context context, ArrayList<Quest> quests) {
        this.context = context;
        this.quests = quests;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(QuestAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public QuestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.quest_recyclerview_item, viewGroup, false);
        return new QuestAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestAdapter.ViewHolder viewHolder, int i) {
        Quest quest = quests.get(i);
        viewHolder.nameTextView.setText(quest.getName());
        viewHolder.descriptionTextView.setText(quest.getDescriptionEnglish());
    }

    @Override
    public int getItemCount() {
        return quests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameTextView;
        private TextView descriptionTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.quest_image_view_recycler_view_item);
            nameTextView = itemView.findViewById(R.id.quest_name_recyclerview_item);
            descriptionTextView = itemView.findViewById(R.id.description_text_view_recycler_view);
            itemView.setOnClickListener(view -> {
                if(onItemClickListener != null){
                    int position = getAdapterPosition();
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }
}
