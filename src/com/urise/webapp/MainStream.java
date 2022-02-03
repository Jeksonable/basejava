package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainStream {
    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        System.out.println(minValue(new int[]{9, 2, 7, 4}));
        System.out.println(minValue(new int[]{7, 6, 7, 3, 8, 3, 5, 7}));

        System.out.println(oddOrEven(Arrays.asList(1, 2, 3, 3, 2, 3)));
        System.out.println(oddOrEven(Arrays.asList(9, 8)));
        System.out.println(oddOrEven(Arrays.asList(9, 2, 7, 4, 5)));
        System.out.println(oddOrEven(Arrays.asList(7, 6, 7, 3, 8, 3, 5, 7)));
    }

    private static int minValue(int[] values) {
//        return Integer.parseInt(Arrays.stream(values)
//                .distinct()
//                .sorted()
//                .mapToObj(String::valueOf)
//                .collect(Collectors.joining()));
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
//        int sum = integers.stream().mapToInt(Integer::intValue).sum();
//        if (sum % 2 == 0) {
//            return integers.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
//        }
//        return integers.stream().filter(i -> i % 2 != 0).collect(Collectors.toList());
        Map<Boolean, List<Integer>> map = integers.stream()
                .collect(Collectors.partitioningBy(i -> i % 2 == 0));
        return map.get(map.get(false).size() % 2 != 0);
    }
}
