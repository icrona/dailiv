package com.dailiv.view.profile.history;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dailiv.App;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.OrderHistory;
import com.dailiv.internal.data.remote.response.history.OrderHistoryResponse;
import com.dailiv.internal.injector.component.DaggerActivityComponent;
import com.dailiv.internal.injector.module.ActivityModule;
import com.dailiv.view.base.AbstractActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 5/11/18.
 */

public class OrderHistoryActivity extends AbstractActivity implements OrderHistoryView{

    @Inject
    OrderHistoryPresenter presenter;

    private OrderHistoryAdapter orderHistoryAdapter;

    @BindView(R.id.toolbar_order_history)
    Toolbar toolbar;

    @BindView(R.id.rv_order_history)
    RecyclerView rvOrderHistory;

    @Override
    public void onDetach() {
        presenter.onDetach();
    }

    @Override
    public void onShowProgressBar() {

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
    public void onHideProgressBar() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_order_history;
    }

    @Override
    protected void initComponents(Bundle savedInstanceState) {
        inject();
        onAttach();
        setToolbar();
        setAdapter();
        presenter.getOrderHistory();
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
        orderHistoryAdapter = new OrderHistoryAdapter();

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        rvOrderHistory.setLayoutManager(linearLayoutManager);

        rvOrderHistory.setAdapter(orderHistoryAdapter);

    }

    @Override
    public void showResponse(List<OrderHistoryResponse> response) {

        List<OrderHistory> orderHistories = mapListToList(response, OrderHistory::new);

        orderHistoryAdapter.setOrderHistories(orderHistories);

        orderHistoryAdapter.notifyDataSetChanged();
    }
}
