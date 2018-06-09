package com.example.visioneh.englishhelper.frag;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.visioneh.englishhelper.R;
import com.example.visioneh.englishhelper.bean.Word;

import java.util.List;

/**
 * Created by visionEH on 2017/9/19.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<Word> mlists;
    public interface onItemClickListener{
        void onItemClick(View view , int position);
        void onLongClick(View view ,int position);
    }
    public onItemClickListener onItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecycleAdapter(Context context, List<Word> mlists) {
        this.inflater = LayoutInflater.from(context);
        this.mlists = mlists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view=inflater.inflate(R.layout.word_item,parent,false);
       return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            Word word=mlists.get(position);
            final ItemViewHolder itemViewHolder=(ItemViewHolder)holder;
            itemViewHolder.name.setText(word.getName());
            itemViewHolder.transf.setText(word.getTranslation());
            itemViewHolder.transf.setVisibility(View.INVISIBLE);
            if(onItemClickListener!=null) {
                itemViewHolder.hide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(itemViewHolder.itemView, holder.getLayoutPosition());
                    }
                });
                itemViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(itemViewHolder.delete, holder.getLayoutPosition());
                    }
                });
                itemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        onItemClickListener.onLongClick(itemViewHolder.itemView,holder.getLayoutPosition());
                        return false;
                    }
                });
                itemViewHolder.transf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(itemViewHolder.itemView, holder.getLayoutPosition());
                    }
                });
            }
    }
    @Override
    public int getItemCount() {
        return mlists.size();
    }

    class  ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView transf;
        public TextView hide;
        public ImageView delete;
        public ItemViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            transf=(TextView)itemView.findViewById(R.id.transf);
            hide=(TextView)itemView.findViewById(R.id.hide);
            delete=(ImageView)itemView.findViewById(R.id.delete);
        }
    }

}
