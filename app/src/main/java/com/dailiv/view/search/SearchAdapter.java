package com.dailiv.view.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dailiv.R;
import com.dailiv.internal.data.local.pojo.SearchResult;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Setter;

import static java.util.Collections.emptyList;

/**
 * Created by aldo on 3/31/18.
 */

@Setter
@AllArgsConstructor
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapterViewHolder> {

    private List<SearchResult> searchResults;

    @Override
    public SearchAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_search, parent, false);

        return new SearchAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SearchAdapterViewHolder holder, int position) {

        holder.getTextView().setText(searchResults.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public SearchAdapter() {
        this.searchResults = emptyList();
    }
}
