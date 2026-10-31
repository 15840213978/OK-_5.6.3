package com.fongmi.android.ok.impl;

public interface Diffable<T> {

    boolean isSameItem(T other);

    boolean isSameContent(T other);
}
