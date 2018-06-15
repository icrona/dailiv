package com.dailiv.view.recipe.detail.comment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.RecipeDetail;
import com.dailiv.view.profile.other.OtherProfileActivity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Setter;
import rx.functions.Action2;

import static com.dailiv.util.common.GlideUtil.glide;

/**
 * Created by aldo on 5/6/18.
 */

@AllArgsConstructor
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapterViewHolder>{

    @Setter
    private List<RecipeDetail.Comment> comments;

    private Action2<Class, String> navigateTo;

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

        glide(holder.getUserPhoto(), comments.get(holder.getAdapterPosition()).getImageUrl());

        holder.getUserPhoto().setOnClickListener(v -> navigateTo.call(OtherProfileActivity.class, comments.get(holder.getAdapterPosition()).getUserSlug()));

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

}
