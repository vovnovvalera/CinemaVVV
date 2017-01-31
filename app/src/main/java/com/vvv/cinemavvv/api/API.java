package com.vvv.cinemavvv.api;

import com.vvv.cinemavvv.api.model.ListCinema;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by valera on 31.01.2017.
 */

public interface API {
    @GET("v2/57cffac8260000181e650041")
    Observable<ListCinema> getListCinema();
}
