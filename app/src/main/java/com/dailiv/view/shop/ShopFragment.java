package com.dailiv.view.shop;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.FilterBy;
import com.dailiv.internal.data.local.pojo.IngredientFilter;
import com.dailiv.internal.data.local.pojo.IngredientIndex;
import com.dailiv.internal.data.remote.response.ingredient.Ingredient;
import com.dailiv.internal.data.remote.response.ingredient.IngredientsResponse;
import com.dailiv.internal.injector.component.DaggerFragmentComponent;
import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.view.base.AbstractFragment;
import com.dailiv.view.custom.EndlessScrollListener;
import com.dailiv.view.custom.IngredientGridDecorator;
import com.dailiv.view.custom.RangeAlertDialog;
import com.dailiv.view.custom.ReselectSpinner;
import com.dailiv.view.custom.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action2;

import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 4/1/18.
 */

public class ShopFragment extends AbstractFragment implements ShopView{

    @Inject
    ShopPresenter presenter;

    @BindView(R.id.rv_shop)
    RecyclerView rvShop;

    @BindView(R.id.sp_filter)
    ReselectSpinner spFilter;

    @BindArray(R.array.shop_filter)
    String[] filter;

    private ShopAdapter shopAdapter;

    private IngredientFilter ingredientFilter = new IngredientFilter();

    private List<FilterBy> filterList;

    private SpinnerAdapter spinnerArrayAdapter;

    private List<IngredientIndex> ingredients = new ArrayList<>();

    private RangeAlertDialog rangeAlertDialog;

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
        setAdapter();
        presenter.getIngredients(ingredientFilter);
        setRangeAlertDialog();
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

    private void setAdapter() {

        shopAdapter = new ShopAdapter(
                new ArrayList<>(),
                this::addToCartDummy,
//                this::addToCart,
                this::deleteCart,
                this::updateCart
        );

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvShop.setLayoutManager(gridLayoutManager);

        rvShop.setAdapter(shopAdapter);

        rvShop.addItemDecoration(new IngredientGridDecorator());

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

        spinnerArrayAdapter = new SpinnerAdapter(
                getActivity(), R.layout.item_spinner, filterList, resetFilter());

        spFilter.setAdapter(spinnerArrayAdapter);

        spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    return;
                }

//                initYesNoDialog(getContext(), "asd", "asd", () -> asd()).show();

                rangeAlertDialog.show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }

    public void setRangeAlertDialog() {
        rangeAlertDialog = new RangeAlertDialog(getContext(), getLayoutInflater()) {
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

    public Action2<Integer, Integer> onFilterPrice() {
        return (from, to) -> {
            System.out.println(from);
            System.out.println(to);
            ingredientFilter.setFromPrice(from);
            ingredientFilter.setToPrice(to);
        };
    }


    public void asd() {
        FilterBy filterBy = filterList.get(1);
        filterBy.setSelected(true);
        filterBy.setInfo("aaa");
        filterList.set(1, filterBy);
        spinnerArrayAdapter.setItems(filterList);
        spinnerArrayAdapter.notifyDataSetChanged();

    }

    public Action1<Integer> resetFilter() {
        return integer -> {
            System.out.println(integer);
            FilterBy filterBy = filterList.get(1);
            filterBy.setSelected(false);
            filterBy.setInfo("");
            filterList.set(1, filterBy);
            spinnerArrayAdapter.setItems(filterList);
            spinnerArrayAdapter.notifyDataSetChanged();
        };
    }

    public android.support.v7.app.AlertDialog initYesNoDialog(final Context context, final String title, final String message, final Action0 onYes) {
        final android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("yes", null)
                .setNegativeButton("no", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button yes = ((android.support.v7.app.AlertDialog) dialog).getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        onYes.call();
                    }
                });

                Button no = ((android.support.v7.app.AlertDialog) dialog).getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE);
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });


        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    //todo
    public void addToCart() {

        System.out.println("adding to cart");
    }

    //dummy
    public void addToCartDummy(int position) {

        IngredientIndex ingredientIndex = ingredients.get(position);
        ingredientIndex.setCartId(position);
        shopAdapter.updateIngredients(position, ingredientIndex);
        shopAdapter.notifyItemChanged(position);

        System.out.println("add to cart with cart id " + position);
    }

    public void deleteCart(int cartId) {

        System.out.println("delete cart with cart id " + cartId);
    }

    public void updateCart(int cartId, int quantity) {

        System.out.println("update cart with cart id " + cartId + " and quantity " + quantity);

    }

}
