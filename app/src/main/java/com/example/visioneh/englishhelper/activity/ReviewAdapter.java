package com.example.visioneh.englishhelper.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visioneh.englishhelper.R;
import com.example.visioneh.englishhelper.bean.Word;
import com.example.visioneh.englishhelper.frag.RecycleAdapter;

import java.util.List;

/**
 * Created by visionEH on 2017/9/26.
 */

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater inflater;
    private List<Word> mlists;
    public interface onItemClickListener{
        void onItemClick(View view , int position);
    }
    public onItemClickListener onItemClickListener;

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ReviewAdapter(Context context, List<Word> mlists) {
        this.inflater = LayoutInflater.from(context);
        this.mlists = mlists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.review_item,parent,false);
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Word word=mlists.get(position);
        final ItemViewHolder itemViewHolder=(ItemViewHolder)holder;
        itemViewHolder.name.setText(word.getName());
        itemViewHolder.tran.setText(word.getTranslation());
        if(onItemClickListener!=null){
          /*  itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemViewHolder.itemView, holder.getLayoutPosition());
                }
            });*/
            itemViewHolder.addreview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(itemViewHolder.addreview,holder.getLayoutPosition());
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
        public TextView tran;
        public TextView addreview;
        public ItemViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            tran=(TextView)itemView.findViewById(R.id.tran);
            addreview=(TextView)itemView.findViewById(R.id.addreview);
        }
    }

}
