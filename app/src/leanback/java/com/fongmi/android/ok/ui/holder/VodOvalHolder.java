package com.fongmi.android.ok.ui.holder;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.fongmi.android.ok.bean.Vod;
import com.fongmi.android.ok.databinding.AdapterVodOvalBinding;
import com.fongmi.android.ok.ui.base.BaseVodHolder;
import com.fongmi.android.ok.ui.presenter.VodPresenter;
import com.fongmi.android.ok.utils.ImgUtil;

public class VodOvalHolder extends BaseVodHolder {

    private final VodPresenter.OnClickListener listener;
    private final AdapterVodOvalBinding binding;

    public VodOvalHolder(@NonNull AdapterVodOvalBinding binding, VodPresenter.OnClickListener listener) {
        super(binding.getRoot());
        this.binding = binding;
        this.listener = listener;
    }

    public VodOvalHolder size(int[] size) {
        binding.image.getLayoutParams().width = size[0];
        binding.image.getLayoutParams().height = size[1];
        return this;
    }

    @Override
    public void initView(Vod item) {
        binding.name.setText(item.getName());
        binding.name.setVisibility(item.getNameVisible());
        binding.getRoot().setOnClickListener(v -> listener.onItemClick(item));
        binding.getRoot().setOnLongClickListener(v -> listener.onLongClick(item));
        ImgUtil.load(item.getName(), item.getPic(), binding.image);
    }

    @Override
    public void unbind() {
        Glide.with(binding.image).clear(binding.image);
    }
}
