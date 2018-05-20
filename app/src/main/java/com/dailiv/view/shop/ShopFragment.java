package com.dailiv.view.shop;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.CheckboxItem;
import com.dailiv.internal.data.local.pojo.FilterBy;
import com.dailiv.internal.data.local.pojo.IngredientFilter;
import com.dailiv.internal.data.local.pojo.IngredientIndex;
import com.dailiv.internal.data.remote.response.Category;
import com.dailiv.internal.data.remote.response.ingredient.IngredientsResponse;
import com.dailiv.internal.injector.component.DaggerFragmentComponent;
import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractFragment;
import com.dailiv.view.custom.CheckboxDialog;
import com.dailiv.view.custom.EndlessScrollListener;
import com.dailiv.view.custom.RecyclerViewDecorator;
import com.dailiv.view.custom.RangeDialog;
import com.dailiv.view.custom.ReselectSpinner;
import com.dailiv.view.custom.FilterByAdapter;
import com.dailiv.view.shop.detail.IngredientDetailActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import rx.functions.Action1;
import rx.functions.Action2;

import static com.annimon.stream.Collectors.toList;
import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 4/1/18.
 */

public class ShopFragment extends AbstractFragment implements ShopView{

    @Inject
    ShopPresenter presenter;

    @Inject
    Navigator navigator;

    @BindView(R.id.rv_shop)
    RecyclerView rvShop;

    @BindView(R.id.sp_filter)
    ReselectSpinner spFilter;

    @BindArray(R.array.shop_filter)
    String[] filter;

    @BindView(R.id.tv_filter_label)
    TextView tvFilterLabel;

    private ShopAdapter shopAdapter;

    private IngredientFilter ingredientFilter = new IngredientFilter();

    private List<FilterBy> filterList;

    private FilterByAdapter filterArrayAdapter;

    private List<IngredientIndex> ingredients = new ArrayList<>();

    private List<CheckboxItem> checkboxItems = new ArrayList<>();

    private RangeDialog rangeDialog;

    private CheckboxDialog checkboxDialog;

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
    protected int getContentView() {
        return R.layout.fragment_shop;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.onDetach();
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        presenter.getCategories();
        setAdapter();
        presenter.getIngredients(ingredientFilter);
        setRangeDialog();
        setSpinner();
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void showIngredients(IngredientsResponse response) {

        List<IngredientIndex> ingredientIndices = mapListToList(response.data, IngredientIndex::new);

        ingredients.addAll(ingredientIndices);

        shopAdapter.setIngredients(ingredientIndices);
        shopAdapter.notifyDataSetChanged();
    }

    private void navigateToDetail(String identifier) {

        navigator.openDetails(getActivity(), IngredientDetailActivity.class, identifier);
    }

    private void setAdapter() {

        shopAdapter = new ShopAdapter(
                new ArrayList<>(),
                this::addToCart,
                this::deleteCart,
                this::updateCart,
                this::navigateToDetail
        );

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvShop.setLayoutManager(gridLayoutManager);

        rvShop.setAdapter(shopAdapter);

        rvShop.addItemDecoration(new RecyclerViewDecorator());

        rvShop.addOnScrollListener(new EndlessScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                ingredientFilter.setPage(page);
                presenter.getIngredients(ingredientFilter);
            }
        });
    }

    private void setSpinner() {

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
                    rangeDialog.show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    private void setRangeDialog() {
        rangeDialog = new RangeDialog(getContext(), getLayoutInflater()) {
            @Override
            public float tickStart() {
                return 500f;
            }

            @Override
            public float tickEnd() {
                return 200000f;
            }

            @Override
            public float fromValue() {
                return ingredientFilter.getFromPrice();
            }

            @Override
            public float toValue() {
                return ingredientFilter.getToPrice();
            }

            @Override
            public String title() {
                return "Filter by price";
            }

            @Override
            public Action2<Integer, Integer> submitAction() {
                return onFilterPrice();
            }

        };

    }

    private void setCheckboxDialog() {

        checkboxDialog = new CheckboxDialog(getContext(), getLayoutInflater(), checkboxItems) {
            @Override
            public String title() {
                return "Filter by category";
            }

            @Override
            public Action1<List<CheckboxItem>> submitAction() {
                return onFilterCategory();
            }
        };
    }

    public Action2<Integer, Integer> onFilterPrice() {
        return (from, to) -> {

            System.out.println(from);
            System.out.println(to);
            ingredientFilter.setFromPrice(from);
            ingredientFilter.setToPrice(to);

            filterIngredient();

            setSpinnerSelected(2, from + " - " + to);

        };
    }

    public Action1<List<CheckboxItem>> onFilterCategory() {

        return (checkboxItems -> {
            List<String> selectedCategory = getSelectedCheckboxes(checkboxItems);
            ingredientFilter.setCategory(selectedCategory);
            filterIngredient();

            setSpinnerSelected(1, String.valueOf(selectedCategory.size()));
        });
    }

    private List<String> getSelectedCheckboxes(List<CheckboxItem> checkboxItems) {
        return Stream.of(checkboxItems)
                .filter(CheckboxItem::isSelected)
                .map(CheckboxItem::getName)
                .collect(toList());
    }

    private void filterIngredient(){

        ingredients.clear();
        shopAdapter.clearIngredients();
        shopAdapter.notifyDataSetChanged();
        ingredientFilter.setPage(1);

        presenter.getIngredients(ingredientFilter);
    }

    private void setSpinnerSelected(int position, String info) {

        tvFilterLabel.setVisibility(View.VISIBLE);

        FilterBy filterBy = filterList.get(position);
        filterBy.setSelected(true);
        filterBy.setInfo(info);

        updateSpinnerItem(position, filterBy);
    }

    private void resetSpinnerSelected(int position) {
        FilterBy filterBy = filterList.get(position);
        filterBy.setSelected(false);
        filterBy.setInfo("");

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

    public Action1<Integer> resetFilter() {
        return position -> {
            if(position == 1) {
                ingredientFilter.resetCategory();
                checkboxDialog.reset();
            }

            else if(position == 2) {
                ingredientFilter.resetPrice();
            }

            filterIngredient();
            resetSpinnerSelected(position);

        };

    }

    public void onAddToCart(int cartId, int cartedAmount, int ingredientId) {

        List<IngredientIndex> list = Stream.of(ingredients)
                .map(i -> {
                    if(i.getId() == ingredientId){
                        i.setCartId(cartId);
                    }
                    return i;
                })
                .collect(toList());

        shopAdapter.updateIngredients(list);
        shopAdapter.notifyDataSetChanged();
    }

    //todo
    public void addToCart(int storeIngredientId) {

        presenter.addToCart(1, storeIngredientId);
        System.out.println("adding to cart with store ingredient id " + storeIngredientId);
    }


    public void deleteCart(int cartId) {

        presenter.deleteCart(cartId);

        System.out.println("delete cart with cart id " + cartId);
    }

    public void updateCart(int cartId, int quantity) {

        presenter.updateCart(cartId, quantity);

        System.out.println("update cart with cart id " + cartId + " and quantity " + quantity);

    }

    @Override
    public void getCategories(List<Category> categories) {

        checkboxItems = mapListToList(categories, CheckboxItem::new);
        setCheckboxDialog();

    }
}
