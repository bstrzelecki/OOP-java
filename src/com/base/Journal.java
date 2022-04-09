package com.base;

import java.util.ArrayList;

public class Journal {
    private final ArrayList<String> entries = new ArrayList<>();

    public void addEntry(String entry) {
        entries.add(entry + "\n");
    }

    public void clear() {
        entries.clear();
    }

    @Override
    public String toString() {
        return String.join("", entries);
    }
}
