package com.duyp.architecture.mvp.base.repo;

import com.duyp.architecture.mvp.data.remote.GithubService;

import lombok.Getter;

/**
 * Created by duypham on 9/15/17.
 *
 */

@Getter
public abstract class BaseRepo {

    private final GithubService githubService;

    public BaseRepo(GithubService githubService) {
        this.githubService = githubService;
    }
}
