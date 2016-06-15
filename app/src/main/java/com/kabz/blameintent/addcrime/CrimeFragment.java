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

import java.util.UUID;

import javax.inject.Inject;

public class CrimeFragment extends Fragment implements AddCrimeContract.View {

    @Inject
    CrimeRepository mCrimeRepository;

    private AddCrimeContract.Presenter mPresenter;
    private FragmentCrimeBinding mBinding;

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

        mPresenter.start(UUID.randomUUID());

        //binding.setCrime(mCrime);

        return mBinding.getRoot();
    }

    @Override
    public void show(Crime crime) {
        mBinding.setCrime(crime);
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }
}
