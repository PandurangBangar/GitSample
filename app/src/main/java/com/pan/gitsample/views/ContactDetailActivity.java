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
import android.text.TextUtils;
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
public class ContactDetailActivity extends BaseActivity {

    private final String TAG = ContactDetailActivity.class.getSimpleName();


    private User user;


    @BindView(R.id.text_view_name)
    TextView textViewName;

    @BindView(R.id.text_view_phone)
    TextView textViewPhone;

    @BindView(R.id.text_view_website)
    TextView textViewWebsite;

    @BindView(R.id.text_view_email)
    TextView textViewEmail;

    @BindView(R.id.text_view_username)
    TextView textViewUserName;

    @BindView(R.id.text_view_street)
    TextView textViewStreet;

    @BindView(R.id.text_view_suite)
    TextView textViewSuite;

    @BindView(R.id.text_view_city)
    TextView textViewCity;

    @BindView(R.id.text_view_zipcode)
    TextView textViewZipCode;

    @BindView(R.id.text_view_company_name)
    TextView textViewCompanyName;

    @BindView(R.id.text_view_catch_phrase)
    TextView textViewCatchPharse;

    @BindView(R.id.text_view_bs)
    TextView textViewBs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(0);
        setContentView(R.layout.activity_contact_detail);
        ButterKnife.bind(this);
        user = (User) getIntent().getExtras().getSerializable("object");
        updateUi();
    }

    public void updateUi() {
        if (user == null) {
            onBackPressed();
            return;
        }
        textViewName.setText(TextUtils.isEmpty(user.getName()) ? "-" : user.getName());
        textViewPhone.setText("Phone: " + (TextUtils.isEmpty(user.getPhone()) ? "-" : user.getPhone()));
        textViewWebsite.setText("Web-Site: " + (TextUtils.isEmpty(user.getWebsite()) ? "-" : user.getWebsite()));
        textViewEmail.setText("Email: " + (TextUtils.isEmpty(user.getEmail()) ? "-" : user.getEmail()));
        textViewUserName.setText("User Name: " + (TextUtils.isEmpty(user.getUsername()) ? "-" : user.getUsername()));
        textViewStreet.setText("Street: " + (TextUtils.isEmpty(user.getAddress().getStreet()) ? "-" : user.getAddress().getStreet()));
        textViewSuite.setText("Suite: " + (TextUtils.isEmpty(user.getAddress().getSuite()) ? "-" : user.getAddress().getSuite()));
        textViewCity.setText("City: " + (TextUtils.isEmpty(user.getAddress().getCity()) ? "-" : user.getAddress().getCity()));
        textViewZipCode.setText("Zip Code: " + (TextUtils.isEmpty(user.getAddress().getZipcode()) ? "-" : user.getAddress().getZipcode()));
        textViewCompanyName.setText("Company Name: " + (TextUtils.isEmpty(user.getCompany().getName()) ? "-" : user.getCompany().getName()));
        textViewCatchPharse.setText("Catch Pharse: " + (TextUtils.isEmpty(user.getCompany().getCatchPhrase()) ? "-" : user.getCompany().getCatchPhrase()));
        textViewBs.setText("BS: " + (TextUtils.isEmpty(user.getCompany().getBs()) ? "-" : user.getCompany().getBs()));
    }


}
