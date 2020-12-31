package com.strangegrotto.montu.parse.secondparse;

import com.googlecode.lanterna.gui2.Component;
import com.strangegrotto.montu.model.Completable;
import com.strangegrotto.montu.model.RootCompletable;
import com.strangegrotto.montu.parse.datamodel.ChecklistItemParseNode;
import com.strangegrotto.montu.parse.datamodel.MarkdownBlockNode;
import com.strangegrotto.montu.parse.datamodel.ParseNode;
import com.strangegrotto.montu.parse.datamodel.RootParseNode;

import java.util.List;

public class SecondParseVisitor {
    private final Completable rootCompletable;
    private final Completable parentCompletable;

    private final List<Component> components;

    public SecondParseVisitor() {
        var rootCompletable = new RootCompletable();
        this.rootCompletable = rootCompletable;
        this.parentCompletable = rootCompletable;
    }

    public void visit(RootParseNode rootParseNode) {
        visitChildren(rootParseNode);
    }

    public void visit(MarkdownBlockNode markdownBlockNode) {
        markdownBlockNode.
    }

    public void visit(ChecklistItemParseNode checklistItemParseNode) {

    }

    private void visitChildren(ParseNode node) {
        for (var child : node.getChildren()) {
            child.accept(this);
        }
    }
}
