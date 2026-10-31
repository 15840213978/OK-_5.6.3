package com.fongmi.android.ok.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.fongmi.android.ok.bean.Style;
import com.fongmi.android.ok.bean.Vod;
import com.fongmi.android.ok.databinding.AdapterVodListBinding;
import com.fongmi.android.ok.databinding.AdapterVodOvalBinding;
import com.fongmi.android.ok.databinding.AdapterVodRectBinding;
import com.fongmi.android.ok.ui.base.BaseVodHolder;
import com.fongmi.android.ok.ui.base.ViewType;
import com.fongmi.android.ok.ui.holder.VodListHolder;
import com.fongmi.android.ok.ui.holder.VodOvalHolder;
import com.fongmi.android.ok.ui.holder.VodRectHolder;

public class VodAdapter extends BaseDiffAdapter<Vod, BaseVodHolder> {

    private final OnClickListener listener;
    private final Style style;
    private final int[] size;

    public VodAdapter(OnClickListener listener, Style style, int[] size) {
        this.listener = listener;
        this.style = style;
        this.size = size;
    }

    public interface OnClickListener {

        void onItemClick(Vod item);

        boolean onLongClick(Vod item);
    }

    public Style getStyle() {
        return style;
    }

    @Override
    public int getItemViewType(int position) {
        return style.getViewType();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVodHolder holder, int position) {
        holder.initView(getItem(position));
    }

    @Override
    public void onViewRecycled(@NonNull BaseVodHolder holder) {
        holder.unbind();
    }

    @NonNull
    @Override
    public BaseVodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return switch (viewType) {
            case ViewType.LIST -> new VodListHolder(AdapterVodListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), listener);
            case ViewType.OVAL -> new VodOvalHolder(AdapterVodOvalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), listener).size(size);
            default -> new VodRectHolder(AdapterVodRectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), listener).size(size);
        };
    }
}
