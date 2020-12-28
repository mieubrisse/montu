package com.strangegrotto.montu;

import com.google.common.base.Strings;
import org.commonmark.node.*;

public class DebuggingVisitor extends AbstractVisitor {
    private int nestLevel = 0;

    @Override
    protected void visitChildren(Node parent) {
        var indentation = Strings.repeat("  ", nestLevel);
        System.out.println(indentation + parent.toString());
        Node node = parent.getFirstChild();
        this.nestLevel++;
        while (node != null) {
            // A subclass of this visitor might modify the node, resulting in getNext returning a different node or no
            // node after visiting it. So get the next node before visiting.
            Node next = node.getNext();
            node.accept(this);
            node = next;
        }
        this.nestLevel--;
    }
}
