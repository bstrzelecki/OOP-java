package com.base;

import javafx.util.Pair;

import java.util.ArrayList;

public class SerializationContext {

    private final ArrayList<Pair<String, Integer>> entries = new ArrayList<>();

    public void populate(String line) {
        String name = line.split(":")[0];
        int value = Integer.parseInt(line.split(":")[1]);

        entries.add(new Pair<>(name, value));
    }

    public int getInt(String name) {
        for (Pair<String, Integer> pair : entries) {
            if (pair.getKey().equals(name)) {
                return pair.getValue();
            }
        }
        return -1;
    }
}
