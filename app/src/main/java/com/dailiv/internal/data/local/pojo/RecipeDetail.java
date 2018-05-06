package com.dailiv.internal.data.local.pojo;

import com.dailiv.internal.data.remote.response.recipe.AddThoughtResponse;
import com.dailiv.internal.data.remote.response.recipe.Instruction;
import com.dailiv.internal.data.remote.response.recipe.Recipe;
import com.dailiv.internal.data.remote.response.recipe.RecipeDetailResponse;
import com.dailiv.internal.data.remote.response.recipe.RecipeIngredient;
import com.dailiv.internal.data.remote.response.recipe.Thought;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.dailiv.util.common.AssetUtil.getRecipeImageUrl;
import static com.dailiv.util.common.AssetUtil.getUserImageUrl;
import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 5/5/18.
 */

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RecipeDetail extends RecipeIndex{

    public RecipeDetail(RecipeDetailResponse response) {
        super(response.recipe, response.user);
        this.description = response.recipe.description;
        this.portion = response.recipe.portion;
        this.isCooked = response.cooked;
        this.isLiked = response.liked;
        this.ingredients = mapListToList(response.ingredients, RecipeIngredient::stringify);
        this.instructions = mapListToList(response.instructions, Instruction::getBody);
        this.comments = mapListToList(response.thoughts, Thought::toComment);
        this.relatedRecipes = mapListToList(response.relatedRecipes, Recipe::toRelatedRecipe);
    }

    private String description;

    private int portion;

    private boolean isCooked;

    private boolean isLiked;

    private List<String> ingredients;

    private List<String> instructions;

    private List<RelatedRecipe> relatedRecipes;

    private List<Comment> comments;

    public String getNumOfComments() {

        return getComments().size() + " " + "comments";
    }

    public void toggleLike() {
        setLiked(!isLiked());
        if(isLiked()) {
            addLike();
        }
        else {
            subtractLike();
        }
    }

    public void toggleCook() {
        setCooked(!isCooked());
    }


    @Data
    @AllArgsConstructor
    public static class RelatedRecipe{

        private int id;

        private String slug;

        private String name;

        private String image;

        public String getImageUrl() {

            return getRecipeImageUrl(getImage());
        }
    }

    @Data
    @AllArgsConstructor
    public static class Comment {

        private int id;

        private int userId;

        private String userName;

        private String commentBody;

        private String userPhoto;

        private String commentTime;

        public String getImageUrl() {

            return getUserImageUrl(getUserPhoto());

        }

        public Comment(AddThoughtResponse response) {

            this(
                    response.thoughtId,
                    //todo
                    0,
                    response.name,
                    response.thought,
                    response.photo,
                    response.date
            );
        }
    }


}
