package com.base;

import javafx.util.Pair;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Serializer {
    private final ArrayList<Pair<String, Integer>> entries = new ArrayList<>();
    private String closed = "";

    public void setInt(String name, int value) {
        entries.add(new Pair<>(name, value));
    }

    @Override
    public String toString() {
        return closed;
    }

    public void setSubEntryName(String name) {
        StringBuilder builder = new StringBuilder(closed);
        for (Pair<String, Integer> pair : entries) {
            builder.append(pair.getKey()).append(":").append(pair.getValue()).append('\n');
        }
        builder.append("!").append(name).append("!").append('\n');
        closed = builder.toString();
        entries.clear();
    }

    public void save(String fileName) throws IOException {
        setSubEntryName("");
        FileWriter fw = new FileWriter(fileName);
        fw.write(closed);
        fw.close();
    }
}