package com.example.naresh.rxjava;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import com.example.naresh.rxjava.databinding.ActivityMainBinding;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  ActivityMainBinding activityMainBinding;
  private Subscription subscription, subScription1;
  private GitHubRepoAdapter adapter;
  Observable<List<GitHubRepo>> stringObservable, getStringObservable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    setUpRecyclerView();
    activityMainBinding.buttonSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getStarsRepos(activityMainBinding.editTextUsername.getText().toString());
        getStarsRepos1(activityMainBinding.editTextUsername1.getText().toString());
      }
    });
  }

  private void setUpRecyclerView() {
    adapter = new GitHubRepoAdapter();
    activityMainBinding.listViewRepos.setLayoutManager(new LinearLayoutManager(this));
    activityMainBinding.listViewRepos.setAdapter(adapter);
  }

  private void getStarsRepos(String userName) {
//    GitHubService apiService = ApiClient.getClient().create(GitHubService.class);
//
//    Call<List<GitHubRepo>> reListCall = apiService.getStarredRepositories1(userName);
//    reListCall.enqueue(new Callback<List<GitHubRepo>>() {
//      @Override
//      public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
//        Log.d(TAG, "onResponse: " + response);
//      }
//
//      @Override
//      public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
//        Log.d(TAG, "onFailure: " + t.getMessage());
//      }
//    });
    GitHubClient client = GitHubClient.getInstance();
    stringObservable = client.getStarredRepos(userName)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    stringObservable.subscribe(new Action1<List<GitHubRepo>>() {
      @Override
      public void call(List<GitHubRepo> gitHubRepos) {
        Log.d(TAG, "getStarsRepos: " + gitHubRepos);
        adapter.addAllData(gitHubRepos);
      }
    });
  }

  private void getStarsRepos1(String userName) {
    GitHubClient client = GitHubClient.getInstance();
    getStringObservable = client.getStarredRepos(userName)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    Log.d(TAG, "getStarsRepos: " + getStringObservable);
    Observable<List<GitHubRepo>> zipped = Observable.zip(stringObservable, getStringObservable,
        new Func2<List<GitHubRepo>, List<GitHubRepo>, List<GitHubRepo>>() {
          @Override
          public List<GitHubRepo> call(List<GitHubRepo> gitHubRepos,
              List<GitHubRepo> gitHubRepos2) {
            Log.d(TAG, "call: " + gitHubRepos + "Second" + gitHubRepos2);
            List<GitHubRepo> gitHubRepoList = new ArrayList<>();
            gitHubRepoList.addAll(gitHubRepos);
            gitHubRepoList.addAll(gitHubRepos2);
            return gitHubRepoList;
          }
        });
    zipped.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<List<GitHubRepo>>() {
          @Override
          public void call(List<GitHubRepo> gitHubRepoList) {
            Log.d(TAG, "result from google and yahoo: " + gitHubRepoList);
            adapter.addAllData(gitHubRepoList);
          }
        });
  }
}
