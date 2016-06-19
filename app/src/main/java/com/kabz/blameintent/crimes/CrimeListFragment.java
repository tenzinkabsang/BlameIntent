package com.kabz.blameintent.crimes;

import android.content.Context;
import android.databinding.PropertyChangeRegistry;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kabz.blameintent.MyApp;
import com.kabz.blameintent.R;
import com.kabz.blameintent.adapters.DataBoundAdapter;
import com.kabz.blameintent.adapters.DataBoundViewHolder;
import com.kabz.blameintent.data.Crime;
import com.kabz.blameintent.data.CrimeRepository;
import com.kabz.blameintent.databinding.FragmentCrimeListBinding;
import com.kabz.blameintent.databinding.ListItemCrimeBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class CrimeListFragment extends Fragment implements CrimesContract.View {

    @Inject
    CrimeRepository mCrimeRepository;

    Crime selectedCrime;

    CrimeAdapter crimeAdapter;

    private final PropertyChangeRegistry mListeners = new PropertyChangeRegistry();

    CrimesContract.Presenter mPresenter;
    private FragmentCrimeListBinding mFragmentView;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApp)getActivity().getApplication()).getComponent().inject(this);
        crimeAdapter = new CrimeAdapter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new CrimesPresenter(mCrimeRepository, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadCrimes(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentView = FragmentCrimeListBinding.inflate(inflater, container, false);

        Context context = getActivity();

        // swiping action
        mFragmentView.refreshCrimeLayout.setColorSchemeColors(
                ContextCompat.getColor(context, R.color.colorPrimary),
                ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.colorPrimaryDark)
        );
        mFragmentView.refreshCrimeLayout.setOnRefreshListener(() -> mPresenter.loadCrimes(true));

        // recycler view
        mFragmentView.crimeRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mFragmentView.crimeRecyclerView.setHasFixedSize(true);
        mFragmentView.crimeRecyclerView.setAdapter(crimeAdapter);


        return mFragmentView.getRoot();
    }

    @Override
    public void show(List<Crime> model) {
        crimeAdapter.replaceData(model);
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if(getView() == null) return;

        final SwipeRefreshLayout srl = (SwipeRefreshLayout) getView().findViewById(R.id.refresh_crime_layout);

        // make sure setRefreshing is called after the layout is done with everything else.
        srl.post(() -> srl.setRefreshing(active));
    }

    public Crime getSelectedCrime(){
        return selectedCrime;
    }

    public void setCrime(Crime selected){
        if(selected == selectedCrime) return;

        selectedCrime = selected;
        //mListeners.notifyChange(this, BR.selectedCrime);
    }

    public class CrimeAdapter extends DataBoundAdapter<ListItemCrimeBinding> {

        private final List<Crime> crimeList = new ArrayList<>();

        public CrimeAdapter(){
            super(R.layout.list_item_crime);
        }

        public void replaceData(Collection<Crime> crimes) {
            crimeList.clear();
            crimeList.addAll(crimes);
            notifyDataSetChanged();
        }

        @Override
        protected void bindItem(DataBoundViewHolder<ListItemCrimeBinding> holder, int position, List<Object> payloads) {
            holder.binding.setModel(crimeList.get(position));
            holder.binding.setPresenter(mPresenter);
        }

        @Override
        public DataBoundViewHolder<ListItemCrimeBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            DataBoundViewHolder<ListItemCrimeBinding> vh = super.onCreateViewHolder(parent, viewType);
            return vh;
        }

        @Override
        public int getItemCount() {
            int count = crimeList.size();
            return count;
        }
    }
}
