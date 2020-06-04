package com.itheima.rabbitmq.utils;

import java.util.ArrayList;
import java.util.List;

public class AverageListUtils {

    public <T> List<List<T>> getAverRange(List<T> list, int average) {
        List<List<T>> newArrayList = new ArrayList<List<T>>();
        int start = 0;
        int end = 0;

        for (int i = 0; i < list.size() / average; i++) {
            List<T> partList = new ArrayList<T>();
            end = start + average;
            if (start >= list.size()) {
                break;
            }
            if (end >= list.size()) {
                end = list.size();
            }
            partList = list.subList(start, end);
            newArrayList.add(partList);
            start = end;
        }
        return newArrayList;
    }
}
