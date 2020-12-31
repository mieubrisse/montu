package com.strangegrotto.montu.parse.datamodel;

import com.google.common.base.Strings;
import com.strangegrotto.montu.model.ChildCompletable;
import com.strangegrotto.montu.model.Completable;
import com.strangegrotto.montu.parse.secondparse.SecondParseVisitor;
import com.strangegrotto.montu.view.ChecklistItemInteractable;
import com.strangegrotto.montu.view.ListMarker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChecklistItemParseNode implements ParseNode {
    private final boolean isCompleted;
    private final int nestLevel;
    private final ListMarker listMarker;
    private final List<MarkdownBlockNode> descNodes; // These are actually nodes describing the current checklist item; they're not really children
    private final List<ChecklistItemParseNode> children;

    // Package class to hold the interactable and the completable which holds a reference to it
    public static class InteractableAndCompletable {
        private final ChecklistItemInteractable interactable;
        private final Completable completable;

        public InteractableAndCompletable(ChecklistItemInteractable interactable, Completable completable) {
            this.interactable = interactable;
            this.completable = completable;
        }

        public ChecklistItemInteractable getInteractable() {
            return interactable;
        }

        public Completable getCompletable() {
            return completable;
        }
    }

    public ChecklistItemParseNode(int nestLevel, ListMarker listMarker, boolean isCompleted) {
        this.nestLevel = nestLevel;
        this.listMarker = listMarker;
        this.isCompleted = isCompleted;
        this.descNodes = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    @Override
    public void addBlockChild(MarkdownBlockNode node) {
        if (this.children.size() > 0) {
            throw new IllegalStateException("Cannot add more text block elements after the checklist node contains subchecklist children!");
        }
        this.descNodes.add(node);
    }

    @Override
    public void addChecklistItemChild(ChecklistItemParseNode node) {
        this.children.add(node);
    }

    @Override
    public List<ParseNode> getChildren() {
        // NOTE: We DON'T return the block nodes that got attached as children because these are actually a part of
        //  the checklist item description itself!
        return List.copyOf(this.children);
    }

    @Override
    public void accept(SecondParseVisitor visitor) {
        visitor.visit(this);
    }

    public InteractableAndCompletable getInteractableAndCompletable(Completable parentCompletable) {
        // TODO insert spaces between certain blocks
        var lines = this.descNodes.stream()
                .map(MarkdownBlockNode::getLines)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        var interactable = new ChecklistItemInteractable(this.nestLevel, this.listMarker, this.isCompleted, lines);
        var completable = new ChildCompletable(parentCompletable, interactable, this.isCompleted);

        return new InteractableAndCompletable(interactable, completable);
    }

    @Override
    public List<String> getLines() {

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
        var checklistLines = this.children.stream()
                .map(ChecklistItemParseNode::getLines)
                .flatMap(List::stream)
                .map(line -> NEST_INDENTATION + line)
                .collect(Collectors.toList());
        var result = Stream.of(List.of(firstDescriptionLine), remainingDescriptionLines, checklistLines)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        return result;
    }
}
