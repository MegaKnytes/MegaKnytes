package com.MegaKnytes.DecisionTable.interfaces;

public interface Command<T> {
    T getData();

    void setData(T data);
}