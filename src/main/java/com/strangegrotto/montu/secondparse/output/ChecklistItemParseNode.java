package com.strangegrotto.montu.secondparse.output;

public class ChecklistItemParseNode extends ContainerParseNode {
    private final String listMarker;

    public ChecklistItemParseNode(String listMarker) {
        this.listMarker = listMarker;
    }
}
