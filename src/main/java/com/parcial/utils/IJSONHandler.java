package com.parcial.utils;

import java.util.List;

public interface IJSONHandler<T> {

    List<T> readFromFile();

    void writeToFile(List<T> items);

    T addItem(T item);
}
