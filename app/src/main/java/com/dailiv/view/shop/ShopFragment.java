package com.dailiv.view.shop;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.IngredientFilter;
import com.dailiv.internal.data.local.pojo.IngredientIndex;
import com.dailiv.internal.data.remote.response.ingredient.IngredientsResponse;
import com.dailiv.internal.injector.component.DaggerFragmentComponent;
import com.dailiv.internal.injector.module.FragmentModule;
import com.dailiv.view.base.AbstractFragment;
import com.dailiv.view.custom.EndlessScrollListener;
import com.dailiv.view.custom.IngredientGridDecorator;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 4/1/18.
 */

public class ShopFragment extends AbstractFragment implements ShopView{

    @Inject
    ShopPresenter presenter;

    @BindView(R.id.rv_shop)
    RecyclerView rvShop;

    private ShopAdapter shopAdapter;

    private IngredientFilter ingredientFilter = new IngredientFilter();

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
    }

    @Override
    public void onShowProgressBar() {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    public void showResponse(IngredientsResponse response) {

        List<IngredientIndex> ingredientIndices = mapListToList(response.data, IngredientIndex::new);

        shopAdapter.setIngredients(ingredientIndices);
        shopAdapter.notifyDataSetChanged();
    }

    private void setAdapter() {

        shopAdapter = new ShopAdapter();

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
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
}
