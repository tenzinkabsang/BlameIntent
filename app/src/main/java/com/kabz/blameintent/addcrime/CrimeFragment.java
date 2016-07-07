package com.kabz.blameintent.addcrime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kabz.blameintent.MyApp;
import com.kabz.blameintent.R;
import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;
import com.kabz.blameintent.databinding.FragmentCrimeBinding;
import com.kabz.blameintent.util.BlameUtils;
import com.kabz.blameintent.util.DateUtils;

import java.io.File;
import java.util.Date;

import javax.inject.Inject;

public class CrimeFragment extends Fragment implements AddCrimeContract.View {

    private static final String ARG_CRIME_ID = "fragment-crime-id";
    private static final String DIALOG_DATE     = "dialog-date";
    private static final String DIALOG_IMAGE = "dialog-image";
    private static final int    REQUEST_DATE    = 0;
    private static final int    REQUEST_CONTACT = 1;
    private static final int    REQUEST_PHOTO   = 2;

    public static final String TAG = "CrimeFragment";

    /**
     * Required interface for hosting activities
     */
    public interface Callbacks {
        void onCrimeChanged(Crime crime);
    }

    private Callbacks mCallbacks;

    @Inject
    CrimeRepository mCrimeRepository;

    private Crime mCrime;
    private File mPhotoFile;

    private AddCrimeContract.Presenter mPresenter;
    private FragmentCrimeBinding       mBinding;
    private PackageManager             mPackageManager;

    public static CrimeFragment newInstance(int crimeId) {
        Bundle args = new Bundle();
        args.putInt(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApp) getActivity().getApplication()).getComponent().inject(this);
        Log.i(TAG, "onCreate: called");
        mPresenter = new AddCrimePresenter(mCrimeRepository, this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //FragmentCrimeBinding binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_crime);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_crime, container, false);
        mBinding.crimeDateBtn.setOnClickListener(v -> {
            DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
            dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
            dialog.show(getFragmentManager(), DIALOG_DATE);
        });

        mBinding.cameraAndTitle.crimePhoto.setOnClickListener(v -> {
            if(mCrime.getImageUrl() == null) return;

            ImageZoomFragment dialog = ImageZoomFragment.newInstance(mCrime.getImageUrl());
            dialog.show(getFragmentManager(), DIALOG_IMAGE);
        });

        mBinding.saveCrimeBtn.setOnClickListener(v -> {
            mPresenter.saveCrime(mCrime);

        });

        // report crime
        mBinding.crimeReportBtn.setOnClickListener(v -> {
            Intent intent = ShareCompat.IntentBuilder
                    .from(getActivity())
                    .setType("text/plain")
                    .setChooserTitle(R.string.send_report)
                    .setSubject(getString(R.string.crime_report_subject))
                    .setText(getCrimeReport())
                    .getIntent();

            startActivity(intent);
        });

        // pick contact
        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mPackageManager = getActivity().getPackageManager();
        if (pickContact.resolveActivity(mPackageManager) == null) {
            mBinding.crimeSuspectBtn.setEnabled(false);
        }
        mBinding.crimeSuspectBtn.setOnClickListener(v -> startActivityForResult(pickContact, REQUEST_CONTACT));

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.crime_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                mPresenter.deleteCrime(mCrime);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null) return;

        switch (requestCode) {
            case REQUEST_DATE:
                Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mCrime.setDate(date);
                break;

            case REQUEST_CONTACT:
                setSuspect(data);
                break;

            case REQUEST_PHOTO:
                mCrime.setImageUrl(mPhotoFile.getPath());
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setSuspect(Intent data) {
        Uri contactUri = data.getData();

        // Specify which fields you want your query to return
        String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};

        // ContactUri is similar to a `where clause`
        Cursor c = getActivity().getContentResolver()
                .query(contactUri, queryFields, null, null, null);

        try {
            if (c.getCount() == 0) return;

            c.moveToFirst();
            final String suspect = c.getString(0);
            mCrime.setSuspect(suspect);
        } finally {
            c.close();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "onResume: called");
        int crimeId = getArguments().getInt(ARG_CRIME_ID);
        mPresenter.start(crimeId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.stop();
    }

    @Override
    public void initialize(Crime crime, File photoFile) {
        mCrime = crime;
        mPhotoFile = photoFile;

        // setup camera
        Intent  captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(mPackageManager) != null;

        if (canTakePhoto) {
            final Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            mBinding.cameraAndTitle.crimeCameraBtn.setOnClickListener(v ->
                    startActivityForResult(captureImage, REQUEST_PHOTO)
            );
        }

        mBinding.setCrime(mCrime);
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void crimeRemoved(Boolean success, Crime crime) {
        if (!success) return;

        mCallbacks.onCrimeChanged(crime);
    }

    @Override
    public void crimeSaved(Crime crime) {
        BlameUtils.hideKeyboard(getActivity());
        mCallbacks.onCrimeChanged(crime);
    }

    private String getCrimeReport() {
        String solvedString = mCrime.isSolved() ? getString(R.string.crime_report_solved) : getString(R.string.crime_report_unsolved);

        String dateString = DateUtils.formatShort(mCrime.getDate());

        String suspect = mCrime.getSuspect() == null
                ? getString(R.string.crime_report_no_suspect)
                : getString(R.string.crime_report_suspect, mCrime.getSuspect());

        final String report = getString(R.string.crime_report,
                mCrime.getTitle(), dateString, solvedString, suspect);

        return report;
    }
}
