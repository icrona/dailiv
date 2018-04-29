package com.dailiv.view.cart;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Cart;
import com.dailiv.internal.data.remote.response.cart.CartResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.custom.RecyclerViewDecorator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 4/28/18.
 */

public class CartActivity extends AbstractActivity implements CartView {

    @Inject
    CartPresenter presenter;

    @BindView(R.id.rv_cart)
    RecyclerView rvCart;

    @BindView(R.id.toolbar_cart)
    Toolbar toolbar;

    private CartAdapter cartAdapter;

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
    public void showCartResponse(List<CartResponse> cartResponses) {

        cartAdapter.setCartList(mapListToList(cartResponses, Cart::new));
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();
        setAdapter();
        presenter.getCartList();
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

    private void setAdapter() {

        cartAdapter = new CartAdapter(
                new ArrayList<>(),
                this::deleteCart,
                this::updateCart
                //todo
        );

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        rvCart.setLayoutManager(linearLayoutManager);

        rvCart.setAdapter(cartAdapter);

        rvCart.addItemDecoration(new RecyclerViewDecorator());

        cartAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                System.out.println(cartAdapter.getGrandTotal());
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                System.out.println(cartAdapter.getGrandTotal());
            }
        });

    }

    public void deleteCart(int cartId) {

        presenter.deleteCart(cartId);

        System.out.println("delete cart with cart id " + cartId);
    }

    public void updateCart(int cartId, int quantity) {

        presenter.updateCart(cartId, quantity);

        System.out.println("update cart with cart id " + cartId + " and quantity " + quantity);

    }
}
