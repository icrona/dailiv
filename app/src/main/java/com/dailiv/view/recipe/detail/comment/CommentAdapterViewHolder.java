package com.dailiv.view.recipe.detail.comment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dailiv.R;

import de.hdodenhof.circleimageview.CircleImageView;
import lombok.Getter;

/**
 * Created by aldo on 5/6/18.
 */

@Getter
public class CommentAdapterViewHolder extends RecyclerView.ViewHolder{

    private CircleImageView userPhoto;

    private TextView userName;

    private TextView commentTime;

    private TextView commentBody;

    public CommentAdapterViewHolder(View itemView) {
        super(itemView);
        this.userPhoto = itemView.findViewById(R.id.iv_user);
        this.userName = itemView.findViewById(R.id.tv_comment_user);
        this.commentTime = itemView.findViewById(R.id.tv_comment_time);
        this.commentBody = itemView.findViewById(R.id.tv_comment_body);
    }
}
