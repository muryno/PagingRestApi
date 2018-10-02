package com.muryno.pagingrestapi.api;

/**
 * Created by brijesh on 20/9/17.
 */


import com.muryno.pagingrestapi.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubService {
    @GET("/users")
    Call<List<User>> getUser(@Query("since") long since, @Query("per_page") int perPage);
}
