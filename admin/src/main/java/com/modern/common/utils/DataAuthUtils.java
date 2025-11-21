package com.modern.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> TODO <p>
 *
 * @Author nut
 * @Date 2023/1/3 15:38
 * @Version 1.0.0
 */
public class DataAuthUtils {

    public static void main(String[] args) {

        System.out.println((2064>>>4)&1);
        types.stream().filter(p->((2064>>>p)&1)==1).findFirst().ifPresent(System.out::println);



    }

    private static final List<Integer> types = new ArrayList<>();
    static {
        types.add(0);
        types.add(1);
        types.add(2);
        types.add(3);
        types.add(4);
        types.add(5);
        types.add(6);
        types.add(7);
        types.add(8);
        types.add(9);
        types.add(10);
        types.add(11);
        types.add(12);
        types.add(13);
        types.add(14);
        types.add(15);
        types.add(16);
        types.add(17);
        types.add(18);
    }

}
