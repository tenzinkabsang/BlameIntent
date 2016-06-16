package com.kabz.blameintent.data;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

abstract public class DataBoundAdapter<T extends ViewDataBinding>
        extends RecyclerView.Adapter<DataBoundAdapter.DataBoundViewHolder<T>>{
    final int mLayoutId;
    final Class<T> mBinderInterface;
    public DataBoundAdapter(int layoutId, Class<T> binderInterface) {
        mLayoutId = layoutId;
        mBinderInterface = binderInterface;
    }

    @Override
    public DataBoundAdapter.DataBoundViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        T binder = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId,
                parent, false);

        return new DataBoundViewHolder(binder);
    }

    public static class DataBoundViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public final T dataBinder;
        public DataBoundViewHolder(T viewBinder){
            super(viewBinder.getRoot());
            dataBinder = viewBinder;
        }
    }
}
