package com.pan.gitsample.api;

import com.pan.gitsample.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by Pandurang.
 */

public interface ApiInterface {
    @GET
    Call<ArrayList<User>> getContacts(@Url String url);
}

