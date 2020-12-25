package com.stock.trading.graph;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private final T element;
    private final List<Line> lines;

    public Node(T element) {
        this.element = element;
        this.lines = new ArrayList<>();
    }

    public T getElement() {
        return element;
    }

    public List<Line> getLines() {
        return lines;
    }
}
