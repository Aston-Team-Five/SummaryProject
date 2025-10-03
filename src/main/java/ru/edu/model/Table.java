package ru.edu.model;

import java.util.Objects;

public class Table {

    private String color;

    private int height;

    public Table(String color, int height) {
        this.color = color;
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return height == table.height && Objects.equals(color, table.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, height);
    }

    @Override
    public String toString() {
        return "Table{" +
                "color='" + color + '\'' +
                ", height=" + height +
                '}';
    }
}
