package ru.edu.model;

import java.util.Objects;

/**
 * Immutable box
 */
public final class Box implements Comparable<Box> {

    public static Box getSingleBox() {
        return new Box(1, 1, 1);
    }

    private final int width;
    private final int height;
    private final int depth;
    private int hash;

    private Box(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = Objects.hash(width, height, depth);
        }
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Box box = (Box) o;
        return width == box.width && height == box.height && depth == box.depth;
    }

    @Override
    public String toString() {
        return "Box{" +
                "width=" + width +
                ", height=" + height +
                ", depth=" + depth +
                '}';
    }

    @Override
    public int compareTo(Box o) {
        int thisVolume = width * height * depth;
        int thatVolume = o.width * o.height * o.depth;
        return thisVolume - thatVolume;
    }

    public static final class Builder {

        private int width;

        private int height;

        private int depth;

        public static Builder builder() {
            return new Builder();
        }

        private Builder() {
        }

        public Builder withWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder withHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder withDepth(int depth) {
            this.depth = depth;
            return this;
        }

        public Box build() {
            if (width <= 0) {
                throw new IllegalStateException("Width must be greater than 0");
            }
            if (height <= 0) {
                throw new IllegalStateException("Height must be greater than 0");
            }
            if (depth <= 0) {
                throw new IllegalStateException("Depth must be greater than 0");
            }
            return new Box(width, height, depth);
        }
    }

}
