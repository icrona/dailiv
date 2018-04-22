package com.dailiv.view.custom;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.FilterBy;

import java.util.List;

import lombok.Setter;
import rx.functions.Action1;

import static android.text.TextUtils.join;
import static com.annimon.stream.Collectors.toList;
import static com.dailiv.util.common.CollectionUtil.mapListToList;

/**
 * Created by aldo on 4/8/18.
 */

public class FilterByAdapter extends ArrayAdapter<FilterBy> {

    private final LayoutInflater mInflater;
    private final Context mContext;

    @Setter
    private List<FilterBy> items;
    private final int mResource;

    private Action1<Integer> resetAction;

    public FilterByAdapter(@NonNull Context context, @LayoutRes int resource,
                           @NonNull List<FilterBy> objects, Action1<Integer> resetAction) {
        super(context, resource, 0, objects);

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = resource;
        items = objects;
        this.resetAction = resetAction;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createDropDownView(position, convertView, parent);
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent);
    }

    private View createDropDownView(int position, View convertView, ViewGroup parent) {
        View mView = mInflater.inflate(mResource, parent, false);

        TextView textView = mView.findViewById(R.id.tv_spinner_text);

        textView.setText(items.get(position).getText());

        TextView reset = mView.findViewById(R.id.tv_reset);

        reset.setVisibility(items.get(position).isSelected() ? View.VISIBLE : View.INVISIBLE);

        reset.setOnClickListener(view -> {
                System.out.println(view);

                resetAction.call(position);
            }
        );

        if(position == 0){

            textView.setVisibility(View.GONE);
            reset.setVisibility(View.GONE);
        }

        return mView;
    }

    private View createView(int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(mResource, parent, false);

        TextView textView = view.findViewById(R.id.tv_spinner_text);

        textView.setText(determineSpinnerView(position));

        return view;
    }

    private String determineSpinnerView(int position) {

        List<FilterBy> selected = getSelected();

        if(selected.size() == 1) {
            return items.get(position).getSpinnerView();
        }

        if(selected.size() == 0) {
            return items.get(0).getSpinnerView();
        }

        return join(", ", mapListToList(selected, FilterBy::getText));

    }

    private List<FilterBy> getSelected() {

        return Stream.of(items)
                .filter(FilterBy::isSelected)
                .collect(toList());
    }

}
