package com.stock.trading.graph;

import com.stock.trading.entity.LotTradeRecord;
import com.stock.trading.entity.TenderTradeRecord;

public class Line {

    private final Node<TenderTradeRecord> tenderElement;
    private final Node<LotTradeRecord> lotElement;
    private final double length;

    public Line(Node<TenderTradeRecord> tenderElement, Node<LotTradeRecord> lotElement, double length) {
        this.tenderElement = tenderElement;
        this.lotElement = lotElement;
        this.length = length;
    }

    public Node<TenderTradeRecord> getTenderElement() {
        return tenderElement;
    }

    public Node<LotTradeRecord> getLotElement() {
        return lotElement;
    }

    public double getLength() {
        return length;
    }

    public void remove() {
        tenderElement.getLines().remove(this);
        lotElement.getLines().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        var other = (Line) o;
        return this.lotElement.getElement().getId().equals(other.lotElement.getElement().getId())
                && this.tenderElement.getElement().getId().equals(other.tenderElement.getElement().getId());
    }

    @Override
    public int hashCode() {
        return 31 * tenderElement.getElement().getId().hashCode()
                + lotElement.getElement().getId().hashCode();
    }
}