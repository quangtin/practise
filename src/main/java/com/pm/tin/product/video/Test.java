package com.pm.tin.product.video;

import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        changeFinalForRecords();
    }
    
    private static void changeFinalForRecords() throws NoSuchFieldException, IllegalAccessException {
        final var point = new Point(12, 35);
        System.out.println(point);
        Field xField = point.getClass().getDeclaredField("x");
        xField.setAccessible(true);
        int newVal = 1000;
        xField.setInt(point, newVal);
        System.out.println(point);
    }
}
