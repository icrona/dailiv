package com.dailiv.view.location;

import com.dailiv.internal.data.remote.response.location.LocationResponse;
import com.dailiv.view.base.IBaseView;

/**
 * Created by aldo on 3/19/18.
 */

public interface LocationView extends IBaseView{

    void onShowProgressBar();

    void onHideProgressBar();

    void onShowError(final String message);

    void onLocationChosen();

    void onGetLocation(LocationResponse locationResponse);

}
