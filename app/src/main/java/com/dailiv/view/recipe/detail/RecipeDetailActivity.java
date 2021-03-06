package com.dailiv.view.recipe.detail;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.MealPlanning;
import com.dailiv.internal.data.local.pojo.RecipeDetail;
import com.dailiv.internal.data.remote.response.recipe.AddThoughtResponse;
import com.dailiv.internal.data.remote.response.recipe.RecipeDetailResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.custom.ExpandableListAdapter;
import com.dailiv.view.custom.MealPlanningDialog;
import com.dailiv.view.custom.NonScrollExpandableListView;
import com.dailiv.view.custom.RecyclerViewDecorator;
import com.dailiv.view.profile.other.OtherProfileActivity;
import com.dailiv.view.recipe.detail.comment.CommentAdapter;
import com.dailiv.view.recipe.detail.related.RelatedRecipeAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.grantland.widget.AutofitTextView;
import rx.functions.Action1;

import static com.annimon.stream.Collectors.toList;
import static com.dailiv.util.common.GlideUtil.glide;

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

    @BindString(R.string.add_to_meal_plan)
    String sAddMealPlan;

    @BindString(R.string.recipe_detail_info)
    String sRecipeDetailInfo;

    @BindView(R.id.tv_recipe_category)
    TextView tvCategory;

    @BindView(R.id.tv_recipe_info)
    AutofitTextView tvRecipeInfo;

    @BindView(R.id.civ_recipe_user_photo)
    CircleImageView civUserPhoto;

    @BindView(R.id.tv_recipe_user)
    TextView tvRecipeUser;

    private RecipeDetail recipeDetail;

    private ExpandableListAdapter expandableListAdapter;

    private RelatedRecipeAdapter relatedRecipeAdapter;

    private CommentAdapter commentAdapter;

    private MealPlanningDialog mealPlanningDialog;

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

        tvCategory.setText(recipeDetail.getCategoriesString());

        String info = String.format(
                sRecipeDetailInfo,
                String.valueOf(recipeDetail.getDuration()),
                String.valueOf(recipeDetail.getPortion()),
                StringUtils.capitalize(recipeDetail.getDifficulty())
        );

        tvRecipeInfo.setText(Html.fromHtml(info));
        tvRecipeDescription.setText(recipeDetail.getDescription());

        glide(civUserPhoto, recipeDetail.getUserPhotoUrl());

        civUserPhoto.setOnClickListener(v -> navigateTo(OtherProfileActivity.class, recipeDetail.getUserSlug()));

        tvRecipeUser.setText(recipeDetail.getUsername());

        glide(ivRecipe, recipeDetail.getImageUrl());

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
        setMealPlanningDialog();
    }

    private void setMealPlanningDialog() {

        mealPlanningDialog = new MealPlanningDialog(this, getLayoutInflater()) {
            @Override
            public Action1<MealPlanning> submitAction() {
                return mealPlanning -> {
                    presenter.addMealPlanning(mealPlanning);
                };
            }

            @Override
            public String title() {
                return sAddMealPlan;
            }
        };
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
                this::navigateTo
        );

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvRelatedRecipe.setLayoutManager(gridLayoutManager);

        rvRelatedRecipe.setAdapter(relatedRecipeAdapter);

        commentAdapter = new CommentAdapter(new ArrayList<>(), this::navigateTo);

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

    private void navigateTo(Class className, String identifier) {

        navigator.openDetails(this, className, identifier);
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

    @OnClick(R.id.btn_add_meal_planning)
    public void addToMealPlanning() {
        mealPlanningDialog.show(recipeDetail.getId());
    }



    @Override
    public void onThoughtAdded(AddThoughtResponse response) {

        RecipeDetail.Comment comment = new RecipeDetail.Comment(response);

        List<RecipeDetail.Comment> comments = recipeDetail.getComments();

        if(comments.isEmpty()) {
            comments = new ArrayList<>();
        }

        comments.add(0, comment);

        recipeDetail.setComments(comments);

        commentAdapter.setComments(comments);

        commentAdapter.notifyDataSetChanged();

        updateNumOfComments();
    }

    @Override
    protected String getScreenName() {
        return "Recipe Detail";
    }
}
