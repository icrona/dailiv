package com.dailiv.view.recipe.detail;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.RecipeDetail;
import com.dailiv.internal.data.remote.response.recipe.AddThoughtResponse;
import com.dailiv.internal.data.remote.response.recipe.RecipeDetailResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.custom.ExpandableListAdapter;
import com.dailiv.view.custom.NonScrollExpandableListView;
import com.dailiv.view.custom.RecyclerViewDecorator;
import com.dailiv.view.recipe.detail.comment.CommentAdapter;
import com.dailiv.view.recipe.detail.related.RelatedRecipeAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.annimon.stream.Collectors.toList;
import static java.util.Collections.singletonList;

/**
 * Created by aldo on 4/29/18.
 */

public class RecipeDetailActivity extends AbstractActivity implements RecipeDetailView{

    @Inject
    Common common;

    @Inject
    RecipeDetailPresenter presenter;

    @Inject
    Navigator navigator;

    @BindView(R.id.toolbar_recipe_detail)
    Toolbar toolbar;

    @BindView(R.id.iv_recipe)
    ImageView ivRecipe;

    @BindView(R.id.tv_recipe_name)
    TextView tvRecipeName;

    @BindView(R.id.tv_recipe_description)
    TextView tvRecipeDescription;

    @BindView(R.id.tv_num_of_comments)
    TextView tvNumOfComments;

    @BindView(R.id.et_comment)
    EditText etComment;

    @BindView(R.id.elv_recipe)
    NonScrollExpandableListView elvRecipe;

    @BindString(R.string.ingredients)
    String sIngredients;

    @BindString(R.string.instructions)
    String sInstructions;

    @BindView(R.id.rv_related_recipe)
    RecyclerView rvRelatedRecipe;

    @BindView(R.id.rv_comment)
    RecyclerView rvComment;

    @BindView(R.id.tv_num_of_like)
    TextView tvNumOfLike;

    @BindView(R.id.iv_like)
    ImageView ivLike;

    @BindView(R.id.iv_mark)
    ImageView ivCook;

    private RecipeDetail recipeDetail;

    private ExpandableListAdapter expandableListAdapter;

    private RelatedRecipeAdapter relatedRecipeAdapter;

    private CommentAdapter commentAdapter;


    @Override
    public void onDetach() {

        presenter.onDetach();
    }

    @Override
    public void inject() {
        DaggerActivityComponent.builder()
                .applicationComponent(App.getComponent())
                .activityModule(new ActivityModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void showDetail(RecipeDetailResponse response) {

        recipeDetail = new RecipeDetail(response);

        tvRecipeName.setText(recipeDetail.getRecipeName());
        tvRecipeDescription.setText(recipeDetail.getDescription());

        Glide.get(ivRecipe.getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(ivRecipe.getContext())
                .load(recipeDetail.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_home)
                .error(R.mipmap.ic_home)
                .dontAnimate()
                .into(ivRecipe);


        updateLikeButton();
        updateCookButton();

        Map<String, List<String>> itemListMap = new LinkedHashMap<String, List<String>>(){{
            put(sIngredients, recipeDetail.getIngredients());
            put(sInstructions, recipeDetail.getInstructions());
        }};

        List<String> headerList = Stream.of(itemListMap)
                .map(Map.Entry::getKey)
                .collect(toList());

        expandableListAdapter.setHeaderList(headerList);
        expandableListAdapter.setItemListMap(itemListMap);
        expandableListAdapter.notifyDataSetChanged();

        relatedRecipeAdapter.setRelatedRecipes(recipeDetail.getRelatedRecipes());
        relatedRecipeAdapter.notifyDataSetChanged();

        commentAdapter.setComments(recipeDetail.getComments());
        commentAdapter.notifyDataSetChanged();

        updateNumOfComments();

    }

    private void updateLikeButton() {

        ivLike.setImageResource(recipeDetail.isLiked() ? R.drawable.ic_like : R.drawable.ic_unlike);

        tvNumOfLike.setText(String.valueOf(recipeDetail.getLike()));
    }

    private void updateCookButton() {

        ivCook.setImageResource(recipeDetail.isCooked() ? R.drawable.ic_mark : R.drawable.ic_unmark);
    }
    private void updateNumOfComments() {
        tvNumOfComments.setText(recipeDetail.getNumOfComments());
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_recipe_detail;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();
        setAdapters();
        final Bundle bundle = getIntent().getExtras();
        presenter.getRecipeDetail(bundle.getString("identifier"));
    }

    private void setAdapters() {

        expandableListAdapter = new ExpandableListAdapter();
        elvRecipe.setAdapter(expandableListAdapter);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        final int width = displayMetrics.widthPixels;

        elvRecipe.setIndicatorBoundsRelative(
                width - common.getDpFromPixel(getApplicationContext(), 50),
                width - common.getDpFromPixel(getApplicationContext(), 10));

        relatedRecipeAdapter = new RelatedRecipeAdapter(
                new ArrayList<>(),
                this::navigateToDetail
        );

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvRelatedRecipe.setLayoutManager(gridLayoutManager);

        rvRelatedRecipe.setAdapter(relatedRecipeAdapter);

        rvRelatedRecipe.addItemDecoration(new RecyclerViewDecorator());

        commentAdapter = new CommentAdapter();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        rvComment.setLayoutManager(linearLayoutManager);

        rvComment.setAdapter(commentAdapter);

        rvComment.addItemDecoration(new RecyclerViewDecorator());

    }

    private void setToolbar() {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setNavigationIcon(
                ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void navigateToDetail(String identifier) {

        navigator.openDetails(this, RecipeDetailActivity.class, identifier);
    }

    @OnClick(R.id.btn_add_comment)
    public void addComment() {

        presenter.addComment(recipeDetail.getId(), etComment.getText().toString());
        etComment.getText().clear();
    }

    @OnClick(R.id.iv_like)
    public void toggleLike() {

        if(recipeDetail.isLiked()) {
            presenter.unLike(recipeDetail.getId());
        }
        else{
            presenter.like(recipeDetail.getId());
        }

        recipeDetail.toggleLike();
        updateLikeButton();
    }

    @OnClick(R.id.iv_mark)
    public void toggleCook() {

        if(recipeDetail.isCooked()) {
            presenter.unCook(recipeDetail.getId());
        }
        else{
            presenter.cook(recipeDetail.getId());
        }

        recipeDetail.toggleCook();
        updateCookButton();
    }


    @Override
    public void onThoughtAdded(AddThoughtResponse response) {

        RecipeDetail.Comment comment = new RecipeDetail.Comment(response);

        List<RecipeDetail.Comment> comments = recipeDetail.getComments();
        comments.add(0, comment);

        recipeDetail.setComments(comments);

        commentAdapter.setComments(comments);

        commentAdapter.notifyDataSetChanged();

        updateNumOfComments();
    }
}
