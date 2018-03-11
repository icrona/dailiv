package com.dailiv.internal.data.remote.response.notification;

import com.dailiv.internal.data.remote.response.Pagination;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by aldo on 3/11/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationsResponse extends Pagination {

    public List<Notification> data;

}
