package com.kabz.blameintent.adapters;

import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

abstract public class BaseDataBoundAdapter<T extends ViewDataBinding>
    extends RecyclerView.Adapter<DataBoundViewHolder<T>>{

    private static final Object DB_PAYLOAD = new Object();

    /**
     * Override this method to handle binding your items into views
     */
    protected abstract void bindItem(DataBoundViewHolder<T> holder, int position,
                                     List<Object> payloads);

    @LayoutRes
    abstract public int getItemLayoutId(int position);

    @Nullable
    private RecyclerView mRecyclerView;

    /**
     * This is used to block items from updating themselves. RecyclerView wants to know when an
     * item is invalidated and it prefers to refresh it via onRebind. It also helps with performance
     * since data binding will not update views that are not changed.
     */
    private final OnRebindCallback mOnRebindCallback = new OnRebindCallback() {
        @Override
        public boolean onPreBind(ViewDataBinding binding) {
            if(mRecyclerView == null || mRecyclerView.isComputingLayout())
                return true;

            int childAdapterPosition = mRecyclerView.getChildAdapterPosition(binding.getRoot());
            if(childAdapterPosition == RecyclerView.NO_POSITION)
                return true;

            notifyItemChanged(childAdapterPosition, DB_PAYLOAD);
            return false;
        }
    };

    @Override
    @CallSuper
    public DataBoundViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        DataBoundViewHolder<T> vh = DataBoundViewHolder.create(parent, viewType);
        vh.binding.addOnRebindCallback(mOnRebindCallback);
        return vh;
    }

    @Override
    public void onBindViewHolder(DataBoundViewHolder<T> holder, int position,
                                 List<Object> payloads) {
        // when a VH is rebound to the same item, we don't have to call the setters
        if(payloads.isEmpty() || hasNonDataBindingInvalidate(payloads))
            bindItem(holder, position, payloads);

        holder.binding.executePendingBindings();
    }

    private boolean hasNonDataBindingInvalidate(List<Object> payloads) {
        for(Object payload: payloads) {
            if(payload != DB_PAYLOAD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final void onBindViewHolder(DataBoundViewHolder<T> holder, int position) {
        throw new IllegalArgumentException("just overridden to make final.");
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = null;
    }

    @Override
    public final int getItemViewType(int position) {
        return getItemLayoutId(position);
    }
}
