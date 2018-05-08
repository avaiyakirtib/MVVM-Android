package com.psk.mvvmdemo.viewModel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psk.mvvmdemo.data.AppConstants;
import com.psk.mvvmdemo.data.DataManager;
import com.psk.mvvmdemo.model.MainModel;
import com.psk.mvvmdemo.model.People;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class UserViewModel extends Observable {

    public ObservableInt peopleRecycler;
    private List<People> peopleList;
    private Context context;

    public UserViewModel(@NonNull Context context) {
        this.context = context;
        this.peopleList = new ArrayList<>();
        peopleRecycler = new ObservableInt(View.GONE);

        fetchPeopleList();
    }

    private void fetchPeopleList() {
        DataManager.runGetAction(AppConstants.apiUrl, new DataManager.DataHandler() {
            @Override
            public void handleResult(String data, String error) {
                if (error != null) {
                    peopleRecycler.set(View.GONE);
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                } else {
                    peopleRecycler.set(View.VISIBLE);
                    MainModel mainModel = new Gson().fromJson(
                            data, new TypeToken<MainModel>() {
                            }.getType()
                    );
                    changePeopleDataSet(mainModel.getResults());
                }
            }
        });
    }

    private void changePeopleDataSet(ArrayList<People> data) {
        peopleList.addAll(data);
        setChanged();
        notifyObservers();
    }

    public List<People> getPeopleList() {
        return peopleList;
    }

}
