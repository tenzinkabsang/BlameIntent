package com.kabz.blameintent.addcrime;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.kabz.blameintent.R;
import com.kabz.blameintent.databinding.ImageZoomBinding;

public class ImageZoomFragment extends DialogFragment {

    private static final String IMAGE_PATH = "crime-image-path";

    public static ImageZoomFragment newInstance(final String imagePathUrl) {
        Bundle args = new Bundle();
        args.putString(IMAGE_PATH, imagePathUrl);

        ImageZoomFragment fragment = new ImageZoomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String imagePath = getArguments().getString(IMAGE_PATH);

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final ImageZoomBinding binding= DataBindingUtil.inflate(inflater, R.layout.image_zoom, null, false);
        binding.setImageUrl(imagePath);

        return new AlertDialog.Builder(getActivity())
                .setView(binding.getRoot())
                .setNegativeButton(android.R.string.ok, (dialog, which) -> {
                   dialog.dismiss();
                })
                .create();
    }
}
