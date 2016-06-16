package com.kabz.blameintent.crimes;

import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
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
import com.kabz.blameintent.data.DataBoundAdapter;
import com.kabz.blameintent.databinding.FragmentCrimeListBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class CrimeListFragment extends Fragment implements CrimesContract.View, Observable{

    @Inject
    CrimeRepository mCrimeRepository;

    @Bindable


    CrimesContract.Presenter mPresenter;
    private FragmentCrimeListBinding mCrimeListBinding;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApp)getActivity().getApplication()).getComponent().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new CrimesPresenter(mCrimeRepository, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCrimeListBinding = FragmentCrimeListBinding.inflate(inflater, container, false);

        return mCrimeListBinding.getRoot();
    }

    @Override
    public void show(List<Crime> model) {

    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {

    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback onPropertyChangedCallback) {

    }

    public class CrimeAdapter extends DataBoundAdapter<FragmentCrimeListBinding> implements View.OnClickListener, Observable {

        private final List<Crime> crimeList = new ArrayList<>();
        private final PropertyChangeRegistry mListeners = new PropertyChangeRegistry();

        public CrimeAdapter(Collection<Crime> crimes){
            super(R.layout.list_item_crime, FragmentCrimeListBinding.class);
            crimeList.addAll(crimes);
        }

        @Override
        public DataBoundViewHolder<FragmentCrimeListBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            DataBoundViewHolder<FragmentCrimeListBinding> vh = super.onCreateViewHolder(parent, viewType);
            return vh;
        }

        @Override
        public void onBindViewHolder(DataBoundAdapter.DataBoundViewHolder<FragmentCrimeListBinding> holder, int position) {
        }

        @Override
        public int getItemCount() {
            return crimeList.size();
        }

        @Override
        public void onClick(View v) {
        }

        @Override
        public void addOnPropertyChangedCallback(OnPropertyChangedCallback listener) {
            mListeners.add(listener);
        }

        @Override
        public void removeOnPropertyChangedCallback(OnPropertyChangedCallback listener) {
            mListeners.remove(listener);
        }
    }
}
