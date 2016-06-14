package com.kabz.blameintent.data;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.kabz.blameintent.BR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract public class BaseAdapter<TModel, TBinding extends ViewDataBinding> extends DataBoundAdapter<TBinding>
        implements View.OnClickListener, Observable {

    private final List<TModel>     mList      = new ArrayList<>();
    private final PropertyChangeRegistry mListeners = new PropertyChangeRegistry();

    abstract void createViewHolder(TBinding binding);
    abstract void bindViewHolder(TModel model);

    public BaseAdapter(int layoutId, Class<TBinding> clazz, Collection<TModel> modelz) {
        super(layoutId, clazz);
        mList.addAll(modelz);
    }

    @Override
    public DataBoundViewHolder<TBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        DataBoundViewHolder<TBinding> vh = super.onCreateViewHolder(parent, viewType);
        createViewHolder(vh.dataBinder);
        return vh;
    }

    @Override
    public void onBindViewHolder(DataBoundViewHolder<TBinding> vh, int position) {
        bindViewHolder(mList.get(position));
        vh.dataBinder.executePendingBindings();
    }

    @Bindable
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(TModel model) {
        if(mList.contains(model)) return;

        mList.add(model);
        notifyItemInserted(mList.size() - 1);
        mListeners.notifyChange(this, BR.itemCount);
    }

    public void remove(TModel model) {
        int i = mList.indexOf(model);
        if(i < 0) return;

        mList.remove(i);
        notifyItemRemoved(i);
        mListeners.notifyChange(this, BR.itemCount);
    }

    @Override
    public void onClick(View v) {
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) v.getLayoutParams();
        final int pos = lp.getViewAdapterPosition();
        /*if(pos > -1 && pos < mList.size()) {
            v.requestFocus();
            setSelected(mList.get(pos));
        } else {
            setSelected(null);
        }*/
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
