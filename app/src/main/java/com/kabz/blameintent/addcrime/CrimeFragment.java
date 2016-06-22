package com.kabz.blameintent.addcrime;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kabz.blameintent.MyApp;
import com.kabz.blameintent.R;
import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;
import com.kabz.blameintent.databinding.FragmentCrimeBinding;

import javax.inject.Inject;

public class CrimeFragment extends Fragment implements AddCrimeContract.View {

    private static final String ARG_CRIME_ID = "fragment-crime-id";
    @Inject
    CrimeRepository mCrimeRepository;

    private AddCrimeContract.Presenter mPresenter;
    private FragmentCrimeBinding mBinding;

    public static CrimeFragment newInstance(int crimeId) {
        Bundle args = new Bundle();
        args.putInt(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApp) getActivity().getApplication()).getComponent().inject(this);
        mPresenter = new AddCrimePresenter(mCrimeRepository, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //FragmentCrimeBinding binding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_crime);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_crime, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        int crimeId = getArguments().getInt(ARG_CRIME_ID);
        mPresenter.start(crimeId);
    }

    @Override
    public void show(Crime crime) {
        mBinding.setCrime(crime);
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }
}
