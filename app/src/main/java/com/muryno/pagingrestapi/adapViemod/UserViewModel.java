package com.muryno.pagingrestapi.adapViemod;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.muryno.pagingrestapi.model.User;
import com.muryno.pagingrestapi.repsitory.NetworkState;
import com.muryno.pagingrestapi.repsitory.byItem.GitHubUserDataSourceFactory;
import com.muryno.pagingrestapi.repsitory.byItem.ItemKeyedUserDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by brijesh on 18/9/17.
 */

public class UserViewModel extends ViewModel {

    public LiveData<PagedList<User>> userList;
    public LiveData<NetworkState> networkState;
    Executor executor;
    LiveData<ItemKeyedUserDataSource> tDataSource;

    public UserViewModel() {
        executor = Executors.newFixedThreadPool(5);
        GitHubUserDataSourceFactory githubUserDataSourceFacteory = new GitHubUserDataSourceFactory(executor);

        tDataSource = githubUserDataSourceFacteory.getMutableLiveData();

        networkState = Transformations.switchMap(githubUserDataSourceFacteory.getMutableLiveData(), dataSource -> dataSource.getNetworkState());

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        userList = (new LivePagedListBuilder(githubUserDataSourceFacteory, pagedListConfig))
                .build();
    }
}
