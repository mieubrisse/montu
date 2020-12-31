package com.strangegrotto.montu.parse.datamodel;

import com.google.common.base.Strings;
import com.strangegrotto.montu.view.ListMarker;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChecklistItemParseNode implements ParseNode {
    // The indentation string we'll use to indicate that items are nested
    private static final String NEST_INDENTATION = "   ";

    private final int nestLevel;
    private final ListMarker listMarker;
    private final List<MarkdownBlockNode> blockNodes; // These are actually nodes describing the current checklist item; they're not really children
    private final List<ChecklistItemParseNode> checklistItemNodes;

    public ChecklistItemParseNode(int nestLevel, ListMarker listMarker) {
        this.nestLevel = nestLevel;
        this.listMarker = listMarker;
        this.blockNodes = new ArrayList<>();
        this.checklistItemNodes = new ArrayList<>();
    }

    @Override
    public List<String> getLines() {
        var blockNodeLines = this.blockNodes.stream()
                .map(MarkdownBlockNode::getLines)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (blockNodeLines.size() == 0) {
            throw new IllegalStateException("Number of block nodes describing the item must be > 0");
        }

        var firstLineMarkerPrefix = this.listMarker + " ";
        var descriptionIndentation = Strings.repeat(" ", firstLineMarkerPrefix.length());

        var firstLineFullPrefix = firstLineMarkerPrefix + "[ ] ";
        var firstDescriptionLine = firstLineFullPrefix + blockNodeLines.get(0);
        var remainingDescriptionLines = blockNodeLines.stream()
                .skip(1)
                .map(line -> descriptionIndentation + line)
                .collect(Collectors.toList());
        var checklistLines = this.checklistItemNodes.stream()
                .map(ChecklistItemParseNode::getLines)
                .flatMap(List::stream)
                .map(line -> NEST_INDENTATION + line)
                .collect(Collectors.toList());
        var result = Stream.of(List.of(firstDescriptionLine), remainingDescriptionLines, checklistLines)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Component> getComponents() {
        // TODO
        return null;
    }

    @Override
    public void addBlockChild(MarkdownBlockNode node) {
        if (this.checklistItemNodes.size() > 0) {
            throw new IllegalStateException("Cannot add more text block elements after the checklist node contains subchecklist children!");
        }
        this.blockNodes.add(node);
    }

    @Override
    public void addChecklistItemChild(ChecklistItemParseNode node) {
        this.checklistItemNodes.add(node);
    }

    @Override
    public List<ParseNode> getChildren() {
        // NOTE: We DON'T return the block nodes that got attached as children because these are actually a part of
        //  the checklist item description itself!
        return List.copyOf(this.checklistItemNodes);
    }
}
