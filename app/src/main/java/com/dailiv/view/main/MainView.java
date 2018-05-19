package com.dailiv.view.main;

import com.dailiv.internal.data.local.pojo.SearchResult;
import com.dailiv.internal.data.remote.response.review.ReviewNeededResponse;
import com.dailiv.view.base.IBaseView;
import com.dailiv.view.base.IView;

import java.util.List;

/**
 * Created by aldo on 3/3/18.
 */

public interface MainView extends IBaseView{

    void onGetSearchResult(List<SearchResult> list);

    void onGetReviewNeeded(ReviewNeededResponse reviewNeededResponse);
}
