package com.dailiv.view.profile.other;

import com.dailiv.internal.data.remote.response.profile.ProfileResponse;
import com.dailiv.view.base.IBaseView;

/**
 * Created by aldo on 6/3/18.
 */

public interface OtherProfileView extends IBaseView{

    void showResponse(ProfileResponse profileResponse);

}
