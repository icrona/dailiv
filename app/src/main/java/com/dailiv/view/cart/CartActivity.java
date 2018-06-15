package com.dailiv.view.cart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Cart;
import com.dailiv.internal.data.local.pojo.Checkout;
import com.dailiv.internal.data.local.pojo.Location;
import com.dailiv.internal.data.remote.response.cart.CartResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.IConstants;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.custom.RecyclerViewDecorator;
import com.dailiv.view.delivery.DeliveryActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.dailiv.util.common.CollectionUtil.mapListToList;
import static com.dailiv.util.common.Preferences.getLocation;

/**
 * Created by aldo on 4/28/18.
 */

public class CartActivity extends AbstractActivity implements CartView {

    @Inject
    CartPresenter presenter;

    @Inject
    Navigator navigator;

    @BindView(R.id.rv_cart)
    RecyclerView rvCart;

    @BindView(R.id.toolbar_cart)
    Toolbar toolbar;

    @BindView(R.id.rl_cart)
    RelativeLayout rlCart;

    @BindView(R.id.tv_subtotal)
    TextView tvSubtotal;

    @BindView(R.id.tv_discount)
    TextView tvDiscount;

    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    private CartAdapter cartAdapter;

    private Checkout checkout = new Checkout();

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

        if(cartResponses.isEmpty()) {

            rlCart.setVisibility(View.GONE);
        }
        else{

            rlCart.setVisibility(View.VISIBLE);
            cartAdapter.setCartList(mapListToList(cartResponses, Cart::new));
            cartAdapter.notifyDataSetChanged();

            updateSubtotal(cartAdapter.getGrandTotal());
        }
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
        setForCheckout();
    }

    private void setForCheckout() {

        Location location = getLocation();

        checkout.setStoreId(location.getStoreId());
        checkout.setLocationId(location.getLocationId());

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
                updateSubtotal(cartAdapter.getGrandTotal());
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                updateSubtotal(cartAdapter.getGrandTotal());
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

    private void updateSubtotal(int subtotal) {

        checkout.setSubtotal(subtotal);
        updateAmount();
    }

    @Override
    public void onGetDeliveryFee(int deliveryFee) {

        checkout.setDeliveryFee(deliveryFee);
        updateAmount();
    }

    private void updateAmount() {

        tvSubtotal.setText(checkout.getSubtotalString());
        tvDeliveryFee.setText(checkout.getDeliveryFeeString());
        tvDiscount.setText(checkout.getDiscountString());
        tvTotal.setText(checkout.getTotalString());
    }

    @OnClick(R.id.btn_checkout)
    public void checkout() {
        navigator.openCheckoutActivity(this, DeliveryActivity.class, checkout);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == IConstants.CHECKOUT_REQUEST_CODE) {
            final Parcelable parcelable = data.getParcelableExtra(IConstants.CHECKOUT);
            checkout = Parcels.unwrap(parcelable);
            updateAmount();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
