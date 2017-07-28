package com.example.naresh.rxjava;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by naresh on 25/7/17.
 */

public interface GitHubService {
  @GET("users/{user}/starred")
  Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);

  @GET("users/{user}")
  Call<List<GitHubRepo>> getStarredRepositories1(@Path("user") String userName);
}
