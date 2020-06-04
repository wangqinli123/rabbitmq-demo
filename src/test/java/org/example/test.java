package org.example;

import com.itheima.rabbitmq.utils.AverageListUtils;

import java.util.ArrayList;
import java.util.List;

public class test {

    public static void main(String[] args) {

        AverageListUtils listUtils = new AverageListUtils();

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(10);
        list.add(11);
        List<List<Integer>> averRange = listUtils.getAverRange(list, 4);

        System.out.println(averRange);

    }
}
