package com.psk.mvvmdemo.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.psk.mvvmdemo.model.People;
import com.psk.mvvmdemo.view.UserDetailsActivity;
import com.squareup.picasso.Picasso;

public class ItemViewModel extends BaseObservable {

    private Context context;
    private People people;

    public ItemViewModel(Context context, People people) {
        this.context = context;
        this.people = people;
    }

    public String getFullName() {
        people.fullName =
                people.name.title + "." + people.name.firts + " " + people.name.last;
        return people.fullName;
    }

    public String getCell() {
        return people.cell;
    }

    public String getMail() {
        return people.mail;
    }

    public String getPictureProfile() {
        return people.picture.medium;
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Picasso.get().load(url).into(imageView);
    }

    public void onItemClick(View view) {
        context.startActivity(UserDetailsActivity.launchDetail(view.getContext(), people));
    }

    public void setPeople(People people) {
        this.people = people;
        notifyChange();
    }

}
