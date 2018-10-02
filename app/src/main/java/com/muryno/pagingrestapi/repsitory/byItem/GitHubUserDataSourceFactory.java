package com.muryno.pagingrestapi.repsitory.byItem;


import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import java.util.concurrent.Executor;

/**
 * Created by brijesh on 25/12/17.
 */

public class GitHubUserDataSourceFactory extends DataSource.Factory {

    MutableLiveData<ItemKeyedUserDataSource> mutableLiveData;
    ItemKeyedUserDataSource itemKeyedUserDataSource;
    Executor executor;

    public GitHubUserDataSourceFactory(Executor executor) {
        this.mutableLiveData = new MutableLiveData<ItemKeyedUserDataSource>();
        this.executor = executor;
    }


//A data source factory simply creates the data source.
// Because of the dependency on the Twitter client, we need to pass it here too:

    @Override
    public DataSource create() {
        itemKeyedUserDataSource = new ItemKeyedUserDataSource(executor);
        mutableLiveData.postValue(itemKeyedUserDataSource);
        return itemKeyedUserDataSource;
    }

    public MutableLiveData<ItemKeyedUserDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

}
