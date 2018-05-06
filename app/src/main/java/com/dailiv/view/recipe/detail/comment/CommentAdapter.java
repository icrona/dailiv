package com.dailiv.view.recipe.detail.comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.RecipeDetail;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by aldo on 5/6/18.
 */

@NoArgsConstructor
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapterViewHolder>{

    @Setter
    private List<RecipeDetail.Comment> comments = new ArrayList<>();

    @Override
    public CommentAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);

        return new CommentAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CommentAdapterViewHolder holder, int position) {

        holder.getUserName().setText(comments.get(holder.getAdapterPosition()).getUserName());

        holder.getCommentBody().setText(comments.get(holder.getAdapterPosition()).getCommentBody());

        holder.getCommentTime().setText(comments.get(holder.getAdapterPosition()).getCommentTime());

        Glide.get(holder.getUserPhoto().getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(holder.getUserPhoto().getContext())
                .load(comments.get(holder.getAdapterPosition()).getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_home)
                .error(R.mipmap.ic_home)
                .dontAnimate()
                .into(holder.getUserPhoto());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

}
