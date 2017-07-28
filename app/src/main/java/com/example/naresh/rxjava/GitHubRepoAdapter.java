package com.example.naresh.rxjava;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.naresh.rxjava.databinding.ItemGithubRepoBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by naresh on 25/7/17.
 */

public class GitHubRepoAdapter
    extends RecyclerView.Adapter<GitHubRepoAdapter.GitHubRepoViewHolder> {
  List<GitHubRepo> gitHubRepoList = new ArrayList<>();

  @Override
  public GitHubRepoAdapter.GitHubRepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ItemGithubRepoBinding itemGithubRepoBinding =
        DataBindingUtil.inflate(inflater, R.layout.item_github_repo, parent, false);
    return new GitHubRepoViewHolder(itemGithubRepoBinding);
  }

  public void addAllData(List<GitHubRepo> gitHubRepos) {
    gitHubRepoList.clear();
    gitHubRepoList.addAll(gitHubRepos);
    notifyDataSetChanged();
  }

  @Override
  public void onBindViewHolder(GitHubRepoAdapter.GitHubRepoViewHolder holder, int position) {
    holder.bindGithubData(gitHubRepoList.get(position));
  }

  @Override
  public int getItemCount() {
    return gitHubRepoList.size();
  }

  public class GitHubRepoViewHolder extends RecyclerView.ViewHolder {
    ItemGithubRepoBinding itemGithubRepoBinding;

    public GitHubRepoViewHolder(ItemGithubRepoBinding itemView) {
      super(itemView.getRoot());
      this.itemGithubRepoBinding = itemView;
    }

    public void bindGithubData(GitHubRepo gitHubRepo) {
      itemGithubRepoBinding.textLanguage.setText(gitHubRepo.language);
      itemGithubRepoBinding.textRepoDescription.setText(gitHubRepo.description);
      itemGithubRepoBinding.textRepoName.setText(gitHubRepo.name);
      itemGithubRepoBinding.textStars.setText(String.valueOf(gitHubRepo.stargazersCount));
    }
  }
}
