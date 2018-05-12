package com.dailiv.view.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Checkout;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.IConstants;
import com.dailiv.util.common.Common;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.profile.history.OrderHistoryActivity;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.custom.NonScrollExpandableListView;
import com.dailiv.view.custom.PaymentMethodListAdapter;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

import static com.annimon.stream.Collectors.toList;
import static java.util.Collections.emptyList;

/**
 * Created by aldo on 5/10/18.
 */

public class PaymentActivity extends AbstractActivity implements PaymentView{

    @Inject
    PaymentPresenter presenter;

    @Inject
    Common common;

    @Inject
    Navigator navigator;

    @BindView(R.id.toolbar_payment)
    Toolbar toolbar;

    @BindView(R.id.tv_subtotal)
    TextView tvSubtotal;

    @BindView(R.id.tv_discount)
    TextView tvDiscount;

    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @BindView(R.id.elv_payment)
    NonScrollExpandableListView elvPayment;

    @BindString(R.string.cash_on_delivery)
    String sCashOnDelivery;

    @BindString(R.string.atm)
    String sAtm;

    @BindString(R.string.credit_card)
    String sCreditCard;

    @BindArray(R.array.cod_details)
    String[] codDetails;

    private Checkout checkout;

    private PaymentMethodListAdapter paymentMethodListAdapter;

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
    public void onCheckoutResponse(boolean response) {

    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();
        setCheckout();
        setAdapter();
    }

    private void setToolbar() {
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        toolbar.setNavigationIcon(
                ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.hideOverflowMenu();
        setSupportActionBar(toolbar);
    }

    private void setAdapter() {

        paymentMethodListAdapter = new PaymentMethodListAdapter();
        elvPayment.setAdapter(paymentMethodListAdapter);

        final DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        final int width = displayMetrics.widthPixels;

        elvPayment.setIndicatorBoundsRelative(
                width - common.getDpFromPixel(getApplicationContext(), 50),
                width - common.getDpFromPixel(getApplicationContext(), 10));

        Map<String, List<String>> itemListMap = new LinkedHashMap<String, List<String>>(){{
            put(sCashOnDelivery, Arrays.asList(codDetails));
            put(sAtm, emptyList());
            put(sCreditCard, emptyList());
        }};

        List<String> headerList = Stream.of(itemListMap)
                .map(Map.Entry::getKey)
                .collect(toList());

        paymentMethodListAdapter.setHeaderList(headerList);
        paymentMethodListAdapter.setItemListMap(itemListMap);

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finishActivity();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity() {
        final Intent intent = new Intent();
        final Bundle bundle = new Bundle();
        bundle.putParcelable(IConstants.CHECKOUT, Parcels.wrap
                (checkout));
        intent.putExtras(bundle);
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    private void setCheckout() {

        checkout = Parcels.unwrap(getIntent().getParcelableExtra(IConstants.CHECKOUT));

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

        //todo
        //presenter.checkout(checkout);
        navigator.openActivity(this, OrderHistoryActivity.class);
    }
}
