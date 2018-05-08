package com.psk.mvvmdemo.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.psk.mvvmdemo.R;
import com.psk.mvvmdemo.databinding.MainActivityBinding;
import com.psk.mvvmdemo.viewModel.UserViewModel;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private MainActivityBinding mainActivityBinding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();

        setupListPeopleView(mainActivityBinding.peopleList);
        setupObserver(userViewModel);
    }

    private void initViews() {
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        userViewModel = new UserViewModel(this);
        mainActivityBinding.setMainViewModel(userViewModel);
    }

    private void setupListPeopleView(RecyclerView listPeople) {
        UserAdapter adapter = new UserAdapter();
        listPeople.setAdapter(adapter);
        listPeople.setLayoutManager(new LinearLayoutManager(this));
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof UserViewModel) {
            UserAdapter peopleAdapter = (UserAdapter) mainActivityBinding.peopleList.getAdapter();
            UserViewModel peopleViewModel = (UserViewModel) o;
            peopleAdapter.setPeopleList(peopleViewModel.getPeopleList());
        }
    }

}
