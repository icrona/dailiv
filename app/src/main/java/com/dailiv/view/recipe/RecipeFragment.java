package com.dailiv.view.recipe;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.CheckboxItem;
import com.dailiv.internal.data.local.pojo.Difficulty;
import com.dailiv.internal.data.local.pojo.FilterBy;
import com.dailiv.internal.data.local.pojo.MealPlanning;
import com.dailiv.internal.data.local.pojo.RecipeFilter;
import com.dailiv.internal.data.local.pojo.RecipeIndex;
import com.dailiv.internal.data.local.pojo.SortBy;
import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.recipe.RecipesResponse;
import com.dailiv.internal.injector.component.DaggerFragmentComponent;
import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractFragment;
import com.dailiv.view.custom.CheckboxDialog;
import com.dailiv.view.custom.EndlessScrollListener;
import com.dailiv.view.custom.FilterByAdapter;
import com.dailiv.view.custom.MealPlanningDialog;
import com.dailiv.view.custom.RadioButtonDialog;
import com.dailiv.view.custom.RangeDialog;
import com.dailiv.view.custom.RecyclerViewDecorator;
import com.dailiv.view.custom.ReselectSpinner;
import com.dailiv.view.custom.SortByAdapter;
import com.dailiv.view.recipe.detail.RecipeDetailActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import rx.functions.Action1;
import rx.functions.Action2;

import static com.annimon.stream.Collectors.toList;
import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 4/1/18.
 */

public class RecipeFragment extends AbstractFragment implements RecipeView{

    @Inject
    RecipePresenter presenter;

    @Inject
    Navigator navigator;

    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipe;

    @BindView(R.id.sp_filter)
    ReselectSpinner spFilter;

    @BindView(R.id.sp_sort)
    AppCompatSpinner spSort;

    @BindArray(R.array.recipe_filter)
    String[] filter;

    @BindView(R.id.tv_filter_label)
    TextView tvFilterLabel;

    @BindString(R.string.filter_by_category)
    String sFilterByCategory;

    @BindString(R.string.filter_by_difficulty)
    String sFilterByDifficulty;

    @BindString(R.string.filter_by_duration)
    String sFilterByDuration;

    private RecipeAdapter recipeAdapter;

    private RecipeFilter recipeFilter = new RecipeFilter();

    private List<FilterBy> filterList;

    private List<SortBy> sortByList = Arrays.asList(SortBy.values());

    private FilterByAdapter filterArrayAdapter;

    private List<RecipeIndex> recipes = new ArrayList<>();

    private List<CheckboxItem> checkboxItems = new ArrayList<>();

    private RangeDialog rangeDialog;

    private CheckboxDialog checkboxDialog;

    private RadioButtonDialog radioButtonDialog;

    private MealPlanningDialog mealPlanningDialog;

    @Override
    public void inject() {

        DaggerFragmentComponent.builder()
                .applicationComponent(App.getComponent())
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onAttach() {
        presenter.onAttach(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetach();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_recipe;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        presenter.getCategories();
        setAdapter();
        presenter.getRecipes(recipeFilter);
        setRangeDialog();
        setRadioButtonDialog();
        setMealPlanningDialog();
        setSortSpinner();
        setFilterSpinner();
    }

    private void navigateToDetail(String identifier) {

        navigator.openDetails(getActivity(), RecipeDetailActivity.class, identifier);
    }

    private void setAdapter() {

        recipeAdapter = new RecipeAdapter(new ArrayList<>(), this::addToMealPlanning, this::navigateToDetail);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        rvRecipe.setLayoutManager(linearLayoutManager);

        rvRecipe.setAdapter(recipeAdapter);

        rvRecipe.addItemDecoration(new RecyclerViewDecorator());

        rvRecipe.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                recipeFilter.setPage(page);
                presenter.getRecipes(recipeFilter);
            }
        });
    }

    private void addToMealPlanning(int recipeId) {
        mealPlanningDialog.show(recipeId);
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void showRecipes(RecipesResponse response) {

        List<RecipeIndex> recipeIndices = mapListToList(response.data, RecipeIndex::new);

        recipes.addAll(recipeIndices);

        recipeAdapter.setRecipes(recipeIndices);
        recipeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getCategories(List<Category> categories) {
        checkboxItems = mapListToList(categories, CheckboxItem::new);
        setCheckboxDialog();
    }

    private void setCheckboxDialog() {
        checkboxDialog = new CheckboxDialog(getContext(), getLayoutInflater(), checkboxItems) {
            @Override
            public String title() {
                return sFilterByCategory;
            }

            @Override
            public Action1<List<CheckboxItem>> submitAction() {
                return onFilterCategory();
            }
        };
    }

    public Action1<List<CheckboxItem>> onFilterCategory() {

        return (checkboxItems -> {
            List<String> selectedCategory = getSelectedCheckboxes(checkboxItems);
            recipeFilter.setCategory(selectedCategory);
            filterRecipe();

            setSpinnerSelected(1, String.valueOf(selectedCategory.size()));
        });
    }

    private List<String> getSelectedCheckboxes(List<CheckboxItem> checkboxItems) {
        return Stream.of(checkboxItems)
                .filter(CheckboxItem::isSelected)
                .map(CheckboxItem::getName)
                .collect(toList());
    }

    private void filterRecipe(){

        recipes.clear();
        recipeAdapter.clearRecipes();
        recipeAdapter.notifyDataSetChanged();
        recipeFilter.setPage(1);

        presenter.getRecipes(recipeFilter);
    }

    private void setSpinnerSelected(int position, String info) {

        tvFilterLabel.setVisibility(View.VISIBLE);

        FilterBy filterBy = filterList.get(position);
        filterBy.setSelected(true);
        filterBy.setInfo(info);

        updateSpinnerItem(position, filterBy);
    }

    private void updateSpinnerItem(int position, FilterBy filterBy) {

        filterList.set(position, filterBy);
        filterArrayAdapter.setItems(filterList);
        filterArrayAdapter.notifyDataSetChanged();
        boolean isAnySelected = Stream.of(filterList).anyMatch(FilterBy::isSelected);

        if(isAnySelected) {
            tvFilterLabel.setVisibility(View.VISIBLE);
        }
        else {
            tvFilterLabel.setVisibility(View.INVISIBLE);
        }
    }

    private void setRadioButtonDialog() {

        radioButtonDialog = new RadioButtonDialog(getContext(), getLayoutInflater(), Arrays.asList(Difficulty.values())) {
            @Override
            public String title() {
                return sFilterByDifficulty;
            }

            @Override
            public Action1<Difficulty> submitAction() {
                return onFilterDifficulty();
            }
        };
    }

    private Action1<Difficulty> onFilterDifficulty() {
        return difficulty -> {
            recipeFilter.setDifficulty(difficulty);
            filterRecipe();

            setSpinnerSelected(2, difficulty.getText());
        };
    }

    private void setMealPlanningDialog() {

        mealPlanningDialog = new MealPlanningDialog(getContext(), getLayoutInflater()) {
            @Override
            public Action1<MealPlanning> submitAction() {
                return mealPlanning -> {
                    if(mealPlanning.isValid()){
                        presenter.addMealPlanning(mealPlanning);
                    }
                    else{
                        //todo
                    }
                };
            }

            @Override
            public String title() {
                return "Add to meal planning";
            }
        };
    }


    private void setRangeDialog() {
        rangeDialog = new RangeDialog(getContext(), getLayoutInflater()) {
            @Override
            public float tickStart() {
                return 1f;
            }

            @Override
            public float tickEnd() {
                return 400f;
            }

            @Override
            public float fromValue() {
                return recipeFilter.getFromDuration();
            }

            @Override
            public float toValue() {
                return recipeFilter.getToDuration();
            }

            @Override
            public String title() {
                return sFilterByDuration;
            }

            @Override
            public Action2<Integer, Integer> submitAction() {
                return onFilterDuration();
            }
        };
    }

    public Action2<Integer, Integer> onFilterDuration() {
        return (from, to) -> {

            System.out.println(from);
            System.out.println(to);
            recipeFilter.setFromDuration(from);
            recipeFilter.setToDuration(to);

            filterRecipe();

            setSpinnerSelected(3, from + " - " + to);

        };
    }

    private void setSortSpinner() {

        SortByAdapter sortByAdapter = new SortByAdapter(getContext(), R.layout.item_spinner, sortByList);

        spSort.setAdapter(sortByAdapter);

        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                recipeFilter.setSortBy(sortByList.get(i));

                filterRecipe();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setFilterSpinner() {

        filterList = mapListToList(Arrays.asList(filter), FilterBy::new);

        filterArrayAdapter = new FilterByAdapter(
                getActivity(), R.layout.item_spinner, filterList, resetFilter());

        spFilter.setAdapter(filterArrayAdapter);

        spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    tvFilterLabel.setVisibility(View.INVISIBLE);
                    return;
                }

                if(position == 1){
                    if(checkboxDialog != null){
                        checkboxDialog.show();
                    }
                }

                else if(position == 2) {
                    radioButtonDialog.show();
                }

                else if(position == 3) {
                    rangeDialog.show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    public Action1<Integer> resetFilter() {
        return position -> {
            if(position == 1) {
                recipeFilter.resetCategory();
                checkboxDialog.reset();
            }

            else if(position == 2) {
                recipeFilter.resetDifficulty();
                radioButtonDialog.reset();
            }

            else if(position == 3) {
                recipeFilter.resetDuration();
            }

            filterRecipe();

            resetSpinnerSelected(position);

        };

    }

    private void resetSpinnerSelected(int position) {
        FilterBy filterBy = filterList.get(position);
        filterBy.setSelected(false);
        filterBy.setInfo("");

        updateSpinnerItem(position, filterBy);
    }


}
