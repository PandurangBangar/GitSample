package com.pan.gitsample.views;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.pan.gitsample.R;
import com.pan.gitsample.adapter.ContactListAdapter;
import com.pan.gitsample.api.ApiInterface;
import com.pan.gitsample.interfaces.OnItemClickListener;
import com.pan.gitsample.manager.UrlManager;
import com.pan.gitsample.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Created by Pandurang.
 */
public class Home extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private final String TAG = Home.class.getSimpleName();


    private ContactListAdapter adapter;
    private ArrayList<User> list;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.textView_error)
    TextView textViewError;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(0);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initToolbar();
        initialiseUI();
    }

    public void initialiseUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        textViewError.setVisibility(View.GONE);
        swipeRefreshLayout.setOnRefreshListener(this);
        fetchData();
    }

    public void initToolbar() {

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
            TextView title = (TextView) toolbar.findViewById(R.id.toolbartitle);
            title.setText(getString(R.string.app_name));
        }
    }


    public void loadList(ArrayList<User> response) {
        list = new ArrayList<>();
        list = response;
        if (list != null) {
            if (list.size() == 0) {
                textViewError.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            if (adapter == null)
                adapter = new ContactListAdapter(this, list);
            else {
                adapter.setListData(list);
                adapter.notifyDataSetChanged();
            }

            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(View view, int position) {
                    startContactDetailActivity(adapter.getItem(position));
                }
            });

        } else {
            textViewError.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }


    public void fetchData() {

        textViewError.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        Retrofit r = new Retrofit.Builder().baseUrl(UrlManager.SERVERURL).addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface pi = r.create(ApiInterface.class);
        Call<ArrayList<User>> call = pi.getContacts("users");
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response != null && response.isSuccessful() && response.body() != null) {
                    textViewError.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    loadList(response.body());
                } else {

                    textViewError.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                textViewError.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.filter(newText);
                }

                return false;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ascending:
                Collections.sort(list, new Comparator<User>() {
                    @Override
                    public int compare(User first, User second) {
                        return first.getName().compareToIgnoreCase(second.getName());
                    }
                });
                loadList(list);
                return true;
            case R.id.action_descending:
                Collections.sort(list, new Comparator<User>() {
                    @Override
                    public int compare(User first, User second) {
                        return second.getName().compareToIgnoreCase(first.getName());
                    }
                });
                loadList(list);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        fetchData();
    }
}
