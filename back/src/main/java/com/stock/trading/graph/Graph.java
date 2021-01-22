package com.stock.trading.graph;

import com.stock.entity.business.LotRecord;
import com.stock.entity.business.TenderRecord;
import com.stock.trading.entity.LotTradeRecord;
import com.stock.trading.entity.TenderTradeRecord;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Graph {

    private List<Node<TenderTradeRecord>> tenderNodes;
    private List<Node<LotTradeRecord>> lotNodes;

    private Graph(List<Node<TenderTradeRecord>> tenderNodes, List<Node<LotTradeRecord>> lotNodes) {
        this.tenderNodes = tenderNodes;
        this.lotNodes = lotNodes;
    }

    public List<Node<LotTradeRecord>> getLotNodes() {
        return lotNodes;
    }

    public List<Node<TenderTradeRecord>> getTenderNodes() {
        return tenderNodes;
    }

    public void acceptLineRules(Predicate<Line> predicate) {
        var linesRoRemove = lotNodes.stream()
                .map(Node::getLines)
                .flatMap(List::stream)
                .filter(predicate)
                .collect(Collectors.toList());

        linesRoRemove.forEach(Line::remove);
    }

    public void removeEmptyNotes() {
        lotNodes.stream()
                .filter(l -> l.getElement().getMaxVolume() < l.getElement().getMinVolume())
                .collect(Collectors.toList())
                .stream()
                .map(Node::getLines)
                .flatMap(List::stream)
                .collect(Collectors.toList())
                .forEach(Line::remove);

        tenderNodes.stream()
                .filter(t -> t.getElement().getMaxVolume() < t.getElement().getMinVolume())
                .collect(Collectors.toList())
                .stream()
                .map(Node::getLines)
                .flatMap(List::stream)
                .collect(Collectors.toList())
                .forEach(Line::remove);

        lotNodes = lotNodes.stream()
                .filter(l -> !l.getLines().isEmpty())
                .collect(Collectors.toList());

        tenderNodes = tenderNodes.stream()
                .filter(t -> !t.getLines().isEmpty())
                .collect(Collectors.toList());
    }

    public int getTotalLotVolume() {
        return lotNodes.stream()
                .map(Node::getElement)
                .mapToInt(LotRecord::getMaxVolume)
                .sum();
    }

    public int getTotalTenderVolume() {
        return tenderNodes.stream()
                .map(Node::getElement)
                .mapToInt(TenderRecord::getMaxVolume)
                .sum();
    }

    public static class GraphBuilder {

        private GraphBuilder() {
        }

        public static Graph createGraph(List<LotTradeRecord> lotList, List<TenderTradeRecord> tenderList) {
            var lotNodes = lotList.stream().map(Node<LotTradeRecord>::new).collect(Collectors.toList());
            var tenderNodes = tenderList.stream().map(Node<TenderTradeRecord>::new).collect(Collectors.toList());
            lotNodes.forEach(lotNode ->
                    tenderNodes.forEach(tenderNode -> {
                        var line = new Line(
                                tenderNode,
                                lotNode,
                                getDistance(lotNode.getElement(), tenderNode.getElement())
                        );
                        tenderNode.getLines().add(line);
                        lotNode.getLines().add(line);
                    })
            );
            return new Graph(tenderNodes, lotNodes);
        }

        private static double getDistance(LotTradeRecord lot, TenderTradeRecord tender) {
            var R = 6371; // Radius of the earth
            var latDistance = Math.toRadians(tender.getLatitude() - lot.getLatitude());
            var lonDistance = Math.toRadians(tender.getLongitude() - lot.getLongitude());
            var a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                    + Math.cos(Math.toRadians(lot.getLatitude()))
                    * Math.cos(Math.toRadians(tender.getLatitude()))
                    * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
            var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            return R * c;// convert to km
        }
    }
}
