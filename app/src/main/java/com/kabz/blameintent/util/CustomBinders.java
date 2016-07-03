package com.kabz.blameintent.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public final class CustomBinders {
    private CustomBinders() {

    }

    @BindingAdapter("bind:imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context ctx = imageView.getContext();
        Glide.with(ctx).load(url).into(imageView);
    }
}
