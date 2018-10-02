package com.muryno.pagingrestapi;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.muryno.pagingrestapi.adapViemod.UserAdapter;
import com.muryno.pagingrestapi.adapViemod.UserViewModel;
import com.muryno.pagingrestapi.model.User;
import com.muryno.pagingrestapi.repsitory.byItem.GitHubUserDataSourceFactory;
import com.muryno.pagingrestapi.util.ListItemClickListener;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements ListItemClickListener {

    private UserViewModel viewModel;
    private String TAG = "MainActivity";
    SwipeRefreshLayout swipeLayout;
    Executor executor;
    GitHubUserDataSourceFactory dataSourceFactory;
    UserAdapter userUserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.userList);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

       userUserAdapter = new UserAdapter((ListItemClickListener) this);



            viewModel.userList.observe(this, users -> {
                userUserAdapter.submitList(users);
                swipeLayout.setRefreshing(false);
            });

        viewModel.networkState.observe(this, networkState -> {
            userUserAdapter.setNetworkState(networkState);
            Log.d(TAG, "Network State Change");
        });

        recyclerView.setAdapter(userUserAdapter);
        swipeLayout = findViewById(R.id.swipeContainer);
        // Adding Listener
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code here
                Toast.makeText(getApplicationContext(), "Works!", Toast.LENGTH_LONG).show();

                // To keep animation for 4 seconds
                dataSourceFactory=new GitHubUserDataSourceFactory(executor);
                dataSourceFactory.create().invalidate();
                refreshContent();
            }
        });



        // Scheme colors for animation

        swipeLayout.setColorSchemeColors(

                getResources().getColor(android.R.color.holo_blue_bright),

                getResources().getColor(android.R.color.holo_green_light),

                getResources().getColor(android.R.color.holo_orange_light),

                getResources().getColor(android.R.color.holo_red_light)

        );
    }

    private void refreshContent(){


        new Handler().postDelayed(() -> {

            // Stop animation (This will be after 3 seconds)


        }, 4000); // Delay in millis
        viewModel.userList.observe(this, users -> {
            userUserAdapter.submitList(users);
        });
        swipeLayout.setRefreshing(false);
    }

    @Override
    public void onRetryClick(View view, int position) {
        Log.d(TAG, "Position " + position);
    }
}