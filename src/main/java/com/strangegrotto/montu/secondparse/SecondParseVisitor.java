package com.strangegrotto.montu.secondparse;

import com.strangegrotto.montu.ChecklistParseException;
import com.strangegrotto.montu.secondparse.output.*;
import com.strangegrotto.montu.secondparse.listmarkers.BulletListMarkerSupplier;
import com.strangegrotto.montu.secondparse.listmarkers.ListMarkerSupplier;
import com.strangegrotto.montu.secondparse.listmarkers.OrderedListMarkerSupplier;
import org.commonmark.ext.task.list.items.TaskListItemMarker;
import org.commonmark.node.*;

import java.util.List;
import java.util.Optional;

public class SecondParseVisitor extends AbstractVisitor {
    private ContainerParseNode parent;

    // When processing a list, this will be non-empty and list items will be able to consume it
    //  to get the marker that they should use when rendering themselves
    private Optional<ListMarkerSupplier> listMarkerSupplierOpt;

    public SecondParseVisitor(List<ParseNode> rootNodes) {
        this.parent = new RootParseNode();
        this.listMarkerSupplierOpt = Optional.empty();
    }

    @Override
    public void visit(BulletList bulletList) {
        var currentListMarkerSupplier = this.listMarkerSupplierOpt;

        this.listMarkerSupplierOpt = Optional.of(new BulletListMarkerSupplier(bulletList.getBulletMarker()));
        this.visitChildren(bulletList);

        this.listMarkerSupplierOpt = currentListMarkerSupplier;
    }

    @Override
    public void visit(OrderedList orderedList) {
        var currentListMarkerSupplierOpt = this.listMarkerSupplierOpt;

        this.listMarkerSupplierOpt = Optional.of(
                new OrderedListMarkerSupplier(
                        orderedList.getStartNumber(),
                        orderedList.getDelimiter()
                )
        );
        this.visitChildren(orderedList);

        this.listMarkerSupplierOpt = currentListMarkerSupplierOpt;
    }


    @Override
    public void visit(ListItem listItem) {
        // TODO Maybe remove this requirement and just call all list items checklist items?
        var firstChild = listItem.getFirstChild();
        if (!(firstChild instanceof TaskListItemMarker)) {
            throw new ChecklistParseException("Found a list item that wasn't a checklist item");
        }

        if (!this.listMarkerSupplierOpt.isPresent()) {
            throw new ChecklistParseException("Encountered a list item and so Expected to have a list marker provider, " +
                    "but none was found; this is a code bug");
        }
        var listMarkerSupplier = listMarkerSupplierOpt.get();
        var listMarker = listMarkerSupplier.get();

        var newNode = new ChecklistItemParseNode(listMarker);
        this.parent.addChild(newNode);

        var currentParent = this.parent;

        this.parent = newNode;
        this.visitChildren(listItem);

        this.parent = currentParent;
    }

    // =============================================================================================================================
    //                           Non-list blocks whose processing gets deferred until rendering
    // =============================================================================================================================
    @Override
    public void visit(Paragraph paragraph) {
        this.parent.addChild(new BlockParseNode(paragraph));
    }

    @Override
    public void visit(Heading heading) {
        this.parent.addChild(new BlockParseNode(heading));
    }

    @Override
    public void visit(BlockQuote blockQuote) {
        this.parent.addChild(new BlockParseNode(blockQuote));
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
        this.parent.addChild(new BlockParseNode(thematicBreak));
    }

    @Override
    public void visit(HtmlBlock htmlBlock) {
        this.parent.addChild(new BlockParseNode(htmlBlock));
    }

    @Override
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        this.parent.addChild(new BlockParseNode(indentedCodeBlock));
    }

    @Override
    public void visit(FencedCodeBlock fencedCodeBlock) {
        this.parent.addChild(new BlockParseNode(fencedCodeBlock));
    }

    @Override
    public void visit(CustomBlock customBlock) {
        this.parent.addChild(new BlockParseNode(customBlock));
    }

    // =============================================================================================================================
    //       Nodes that only show up in blocks, which this parser should never see since it doesn't process blocks
    // =============================================================================================================================
    @Override
    public void visit(CustomNode customNode) {
        if (customNode instanceof TaskListItemMarker) {
            return;
        }
        throw new ChecklistParseException("Intermediate parser should never see custom nodes outside of TaskListItemMarker");
    }

    @Override
    public void visit(Code code) {
        throw new ChecklistParseException("Intermediate parser shouldn't encounter any bare Code text");
    }

    @Override
    public void visit(Emphasis emphasis) {
        throw new ChecklistParseException("Intermediate parser shouldn't encounter any bare Emphasis nodes");
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
        throw new ChecklistParseException("Intermediate parser shouldn't encounter any bare HardLineBreak nodes");
    }

    @Override
    public void visit(HtmlInline htmlInline) {
        throw new ChecklistParseException("Intermediate parser shouldn't encounter any bare inline HTML");
    }

    @Override
    public void visit(Image image) {
        throw new ChecklistParseException("Intermediate parser shouldn't encounter any bare images");
    }

    @Override
    public void visit(Link link) {
        throw new ChecklistParseException("Intermediate parser shouldn't encounter any bare Links");
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        throw new ChecklistParseException("Intermediate parser shouldn't encounter any bare soft line breaks");
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        throw new ChecklistParseException("Intermediate parser shouldn't encounter any bare StrongEmphasis");
    }

    @Override
    public void visit(Text text) {
        throw new ChecklistParseException("Intermediate parser shouldn't encounter any bare Text");
    }

    @Override
    public void visit(LinkReferenceDefinition linkReferenceDefinition) {
        throw new ChecklistParseException("Intermediate parser shouldn't encounter any bare link reference definitions");
    }
}
