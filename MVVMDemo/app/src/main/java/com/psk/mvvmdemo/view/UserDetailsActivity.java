package com.psk.mvvmdemo.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.psk.mvvmdemo.R;
import com.psk.mvvmdemo.databinding.UserDetailsActivityBinding;
import com.psk.mvvmdemo.model.People;
import com.psk.mvvmdemo.viewModel.UserDetailsViewModel;

public class UserDetailsActivity extends AppCompatActivity {

    UserDetailsActivityBinding userDetailsActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setSupportActionBar(userDetailsActivityBinding.toolbar);
        displayHomeAsUpEnabled();
        getExtrasFromIntent();
    }

    protected void initViews() {
        userDetailsActivityBinding =
                DataBindingUtil.setContentView(this, R.layout.user_details_activity);
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static Intent launchDetail(Context context, People people) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra("PEOPLE", people);
        return intent;
    }

    private void getExtrasFromIntent() {
        People people = (People) getIntent().getSerializableExtra("PEOPLE");
        UserDetailsViewModel peopleDetailViewModel = new UserDetailsViewModel(people);
        userDetailsActivityBinding.setUserDetailsViewModel(peopleDetailViewModel);
        setTitle(people.name.title + "." + people.name.firts + " " + people.name.last);
    }

}
