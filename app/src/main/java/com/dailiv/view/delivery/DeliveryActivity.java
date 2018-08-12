package com.dailiv.view.delivery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.Checkout;
import com.dailiv.internal.data.remote.response.checkout.CouponResponse;
import com.dailiv.internal.data.remote.response.profile.ProfileResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.util.IConstants;
import com.dailiv.util.common.Navigator;
import com.dailiv.view.base.AbstractActivity;
import com.dailiv.view.payment.PaymentActivity;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by aldo on 5/10/18.
 */

public class DeliveryActivity extends AbstractActivity implements DeliveryView{

    @Inject
    DeliveryPresenter presenter;

    @Inject
    Navigator navigator;

    @BindView(R.id.toolbar_delivery)
    Toolbar toolbar;

    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;

    @BindView(R.id.et_notes)
    EditText etNotes;

    @BindView(R.id.et_coupon)
    EditText etCoupon;

    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.tv_subtotal)
    TextView tvSubtotal;

    @BindView(R.id.tv_discount)
    TextView tvDiscount;

    @BindView(R.id.tv_delivery_fee)
    TextView tvDeliveryFee;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    private Checkout checkout;

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
    public void onCheckDiscountCoupon(CouponResponse couponResponse) {

        double discountRate = Double.parseDouble(couponResponse.discount);

        checkout.setDiscountRate(discountRate);

        updateAmount();
    }

    @Override
    public void onGetProfile(ProfileResponse profileResponse) {

        etPhone.setText(profileResponse.user.phone);
    }

    @Override
    public void onHideProgressBar() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_delivery;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();
        setCheckout();
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

        etCoupon.setOnFocusChangeListener((v, hasFocus) -> {
            String code = ((EditText) v).getText().toString();
            if(!hasFocus && !code.equals("")) {
                presenter.checkCoupon(code);
            }
        });

        if(checkout.getPhoneNumber() == null || checkout.getPhoneNumber().equals("")) {
            presenter.getProfile();
        }
    }

    private void updateAmount() {

        tvSubtotal.setText(checkout.getSubtotalString());
        tvDeliveryFee.setText(checkout.getDeliveryFeeString());
        tvDiscount.setText(checkout.getDiscountString());
        tvTotal.setText(checkout.getTotalString());
    }

    @OnClick(R.id.btn_proceed_to_payment)
    public void proceedToPayment() {
        String noteFormat = "Notes : %s , AddressDetail : %s";

        String notes = String.format(noteFormat, etNotes.getText().toString(), etAddressDetail. getText().toString());

        String phone = etPhone.getText().toString();

        String coupon = etCoupon.getText().toString();

        if(phone.equals("")){
            Toast.makeText(this, R.string.please_enter_phone_number, Toast.LENGTH_SHORT).show();
            return;
        }
        checkout.setPhoneNumber(phone);

        checkout.setNote(notes);

        if(!coupon.equals("")) {
            checkout.setCouponCode(coupon);
        }

        navigator.openCheckoutActivity(this, PaymentActivity.class, checkout);
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
