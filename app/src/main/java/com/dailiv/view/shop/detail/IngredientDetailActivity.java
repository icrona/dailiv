package com.dailiv.view.shop.detail;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.IngredientIndex;
import com.dailiv.internal.data.remote.response.ingredient.Ingredient;
import com.dailiv.internal.data.remote.response.ingredient.IngredientDetailResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.custom.EndlessScrollListener;
import com.dailiv.view.custom.RecyclerViewDecorator;
import com.dailiv.view.shop.ShopAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.himanshusoni.quantityview.QuantityView;

import static com.annimon.stream.Collectors.toList;
import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 4/29/18.
 */

public class IngredientDetailActivity extends AbstractActivity implements IngredientDetailView{


    @Inject
    IngredientDetailPresenter presenter;

    @Inject
    Navigator navigator;

    @BindView(R.id.toolbar_ingredient_detail)
    Toolbar toolbar;

    @BindView(R.id.iv_ingredient)
    ImageView ivIngredient;

    @BindView(R.id.tv_ingredient_name)
    TextView tvIngredientName;

    @BindView(R.id.tv_ingredient_price)
    TextView tvIngredientPrice;

    @BindView(R.id.tv_ingredient_unit)
    TextView tvIngredientUnit;

    @BindView(R.id.tv_ingredient_category)
    TextView tvIngredientCategory;

    @BindView(R.id.ll_ingredient_qty)
    LinearLayout llQuantity;

    @BindView(R.id.qv_ingredient)
    QuantityView qvIngredient;

    @BindView(R.id.btn_add_to_cart)
    Button btnAddToCart;

    @BindView(R.id.rv_shop)
    RecyclerView rvShop;

    private ShopAdapter shopAdapter;

    private IngredientIndex ingredient;

    private List<IngredientIndex> similarIngredients = new ArrayList<>();

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
    public void showDetail(IngredientDetailResponse response) {

        //show detail

        ingredient = new IngredientIndex(response.ingredient);
        tvIngredientName.setText(ingredient.getName());
        tvIngredientPrice.setText(ingredient.getPriceString());
        tvIngredientUnit.setText(ingredient.getMinUnit());
        tvIngredientCategory.setText(response.ingredient.category);

        Glide.get(ivIngredient.getContext()).setMemoryCategory(MemoryCategory.HIGH);

        Glide.with(ivIngredient.getContext())
                .load(ingredient.getImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_home)
                .error(R.mipmap.ic_home)
                .dontAnimate()
                .into(ivIngredient);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                llQuantity.setVisibility(View.VISIBLE);
                qvIngredient.setQuantity(1);

                ingredient.setCartedAmount(1);

                addToCart(ingredient.getStoreIngredientId());
            }
        });

        qvIngredient.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(int oldQuantity, int newQuantity, boolean programmatically) {

                int cartId = ingredient.getCartId();

                if(newQuantity == 0) {
                    llQuantity.setVisibility(View.GONE);
                    btnAddToCart.setVisibility(View.VISIBLE);
                    deleteCart(cartId);
                }
                else{
                    updateCart(cartId, newQuantity);
                }

                ingredient.setCartedAmount(newQuantity);
            }

            @Override
            public void onLimitReached() {

            }
        });

        if(ingredient.isCarted()){
            btnAddToCart.setVisibility(View.GONE);
            llQuantity.setVisibility(View.VISIBLE);
            qvIngredient.setQuantity(ingredient.getCartedAmount());
        }
        else{
            btnAddToCart.setVisibility(View.VISIBLE);
            llQuantity.setVisibility(View.GONE);
            qvIngredient.setQuantity(0);
        }

        //show similar ingredients

        List<IngredientIndex> ingredientIndices = mapListToList(response.similarIngredient, IngredientIndex::new);

        similarIngredients.addAll(ingredientIndices);
        shopAdapter.setIngredients(ingredientIndices);

        shopAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_ingredient_detail;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();
        setAdapter();
        final Bundle bundle = getIntent().getExtras();
        presenter.getIngredientDetail(bundle.getString("identifier"));
    }

    private void setToolbar() {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setNavigationIcon(
                ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);
    }

    private void setAdapter() {

        shopAdapter = new ShopAdapter(
                new ArrayList<>(),
                this::addToCart,
                this::deleteCart,
                this::updateCart,
                this::navigateToDetail
        );

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvShop.setLayoutManager(gridLayoutManager);

        rvShop.setAdapter(shopAdapter);

        rvShop.addItemDecoration(new RecyclerViewDecorator());
    }

    private void navigateToDetail(String identifier) {

        navigator.openDetails(this, IngredientDetailActivity.class, identifier);
    }

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
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddToCart(int cartId, int cartedAmount, int ingredientId) {

        if(ingredientId == ingredient.getId()){
            ingredient.setCartId(cartId);
            ingredient.setCartedAmount(cartedAmount);
        }
        else{
            List<IngredientIndex> list = Stream.of(similarIngredients)
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
    }
}
