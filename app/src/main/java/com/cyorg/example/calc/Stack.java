package com.cyorg.example.calc;

import java.util.ArrayList;

/**
 * Created by HCL on 12/23/2016.
 */
public class Stack {

    private static final ArrayList<String> stack = new ArrayList<>();

    private Stack(){}

    public static void push(String value)   {
        stack.add(value);
    }

    public static String pop()  {
        int index = stack.size() - 1;

        String value = stack.get(index);
        stack.remove(index);

        return value;
    }
}
