package com.psk.mvvmdemo.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psk.mvvmdemo.R;
import com.psk.mvvmdemo.data.AppUtils;
import com.psk.mvvmdemo.databinding.ItemUserBinding;
import com.psk.mvvmdemo.model.People;
import com.psk.mvvmdemo.viewModel.ItemViewModel;

import java.util.Collections;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<People> peopleList;

    public UserAdapter() {
        this.peopleList = Collections.emptyList();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding itemUserBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_user, parent, false);
        return new UserViewHolder(itemUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bindPeople(peopleList.get(position));
    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        ItemUserBinding itemUserBinding;

        public UserViewHolder(ItemUserBinding itemUserBinding) {
            super(itemUserBinding.itemPeople);
            this.itemUserBinding = itemUserBinding;
            AppUtils.overrideFontsRegular(itemView.getContext(), itemUserBinding.labelName);
            AppUtils.overrideFontsRegular(itemView.getContext(), itemUserBinding.labelPhone);
            AppUtils.overrideFontsRegular(itemView.getContext(), itemUserBinding.labelMail);
        }

        void bindPeople(People people) {
            if (itemUserBinding.getItemViewModel() == null) {
                itemUserBinding.setItemViewModel(new ItemViewModel(itemView.getContext(), people));
            } else {
                itemUserBinding.getItemViewModel().setPeople(people);
            }
        }

    }

    public void setPeopleList(List<People> peopleList) {
        this.peopleList = peopleList;
        notifyDataSetChanged();
    }

}
