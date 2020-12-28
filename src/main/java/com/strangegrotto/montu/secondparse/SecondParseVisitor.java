package com.strangegrotto.montu.secondparse;

import com.strangegrotto.montu.secondparse.output.*;
import com.strangegrotto.montu.secondparse.listmarkers.BulletListMarkerSupplier;
import com.strangegrotto.montu.secondparse.listmarkers.ListMarkerSupplier;
import com.strangegrotto.montu.secondparse.listmarkers.OrderedListMarkerSupplier;
import org.commonmark.ext.task.list.items.TaskListItemMarker;
import org.commonmark.node.*;

import java.util.List;
import java.util.Optional;

public class SecondParseVisitor extends AbstractVisitor {
    private Optional<ParseNode> rootOpt;

    private Optional<ContainerParseNode> parentOpt;

    // How deeply nested we are when we visit ListItems
    private int listNestLevel;

    // When processing a list, this will be non-empty and list items will be able to consume it
    //  to get the marker that they should use when rendering themselves
    private Optional<ListMarkerSupplier> listMarkerSupplierOpt;

    public SecondParseVisitor() {
        this.rootOpt = Optional.empty();
        this.parentOpt = Optional.empty();
        this.listMarkerSupplierOpt = Optional.empty();

        // We start this at -1 so that the first BulletList/OrderedLists (the root-level ones) will get nest level 0 and 0 indentation
        this.listNestLevel = -1;
    }

    public Optional<ParseNode> getRootOpt() {
        return this.rootOpt;
    }

    @Override
    public void visit(Document document) {
        var rootNode = new RootParseNode();
        this.rootOpt = Optional.of(rootNode);
        this.parentOpt = Optional.of(rootNode);
        this.visitChildren(document);
    }

    @Override
    public void visit(BulletList bulletList) {
        var currentListMarkerSupplier = this.listMarkerSupplierOpt;

        this.listNestLevel++;
        this.listMarkerSupplierOpt = Optional.of(new BulletListMarkerSupplier(bulletList.getBulletMarker()));
        this.visitChildren(bulletList);

        this.listNestLevel--;
        this.listMarkerSupplierOpt = currentListMarkerSupplier;
    }

    @Override
    public void visit(OrderedList orderedList) {
        var currentListMarkerSupplierOpt = this.listMarkerSupplierOpt;

        this.listNestLevel++;
        this.listMarkerSupplierOpt = Optional.of(
                new OrderedListMarkerSupplier(
                        orderedList.getStartNumber(),
                        orderedList.getDelimiter()
                )
        );
        this.visitChildren(orderedList);

        this.listNestLevel--;
        this.listMarkerSupplierOpt = currentListMarkerSupplierOpt;
    }


    @Override
    public void visit(ListItem listItem) {
        var parent = this.parentOpt.orElseThrow(() -> new SecondParseException(
                "No parent node exists, indicating this visitor wasn't called on the " +
                    "Document root; this is a code bug"
        ));

        // TODO Maybe remove this requirement and just call all list items checklist items?
        var firstChild = listItem.getFirstChild();
        if (!(firstChild instanceof TaskListItemMarker)) {
            throw new SecondParseException("Found a list item that wasn't a checklist item");
        }

        var listMarkerSupplier = listMarkerSupplierOpt.orElseThrow(() -> new SecondParseException(
                "Encountered a list item and so expected to have a list marker provider, " +
                    "but none was found; this is a code bug"
        ));
        var listMarker = listMarkerSupplier.get();

        var newNode = new ChecklistItemParseNode(this.listNestLevel, listMarker);
        parent.addChecklistItemChild(newNode);

        var currentParentOpt = this.parentOpt;

        this.parentOpt = Optional.of(newNode);
        this.visitChildren(listItem);

        this.parentOpt = currentParentOpt;
    }

    // =============================================================================================================================
    //                           Non-list blocks whose processing gets deferred until rendering
    // =============================================================================================================================
    @Override
    public void visit(Paragraph paragraph) {
        addBlockToParentIfPresent(paragraph);
    }

    @Override
    public void visit(Heading heading) {
        addBlockToParentIfPresent(heading);
    }

    @Override
    public void visit(BlockQuote blockQuote) {
        addBlockToParentIfPresent(blockQuote);
    }

    @Override
    public void visit(ThematicBreak thematicBreak) {
        addBlockToParentIfPresent(thematicBreak);
    }

    @Override
    public void visit(HtmlBlock htmlBlock) {
        addBlockToParentIfPresent(htmlBlock);
    }

    @Override
    public void visit(IndentedCodeBlock indentedCodeBlock) {
        addBlockToParentIfPresent(indentedCodeBlock);
    }

    @Override
    public void visit(FencedCodeBlock fencedCodeBlock) {
        addBlockToParentIfPresent(fencedCodeBlock);
    }

    @Override
    public void visit(CustomBlock customBlock) {
        addBlockToParentIfPresent(customBlock);
    }

    private void addBlockToParentIfPresent(Block blockNode) {
        var parent = this.parentOpt.orElseThrow(() -> new SecondParseException(
                "No parent node exists, indicating this visitor wasn't called on the " +
                    "Document root; this is a code bug"
        ));
        parent.addBlockChild(new BlockParseNode(blockNode));
    }

    // =============================================================================================================================
    //       Nodes that only show up in blocks, which this parser should never see since it doesn't process blocks
    // =============================================================================================================================
    @Override
    public void visit(CustomNode customNode) {
        if (customNode instanceof TaskListItemMarker) {
            return;
        }
        throw new SecondParseException("Intermediate parser should never see custom nodes outside of TaskListItemMarker");
    }

    @Override
    public void visit(Code code) {
        throw new SecondParseException("Intermediate parser shouldn't encounter any bare Code text");
    }

    @Override
    public void visit(Emphasis emphasis) {
        throw new SecondParseException("Intermediate parser shouldn't encounter any bare Emphasis nodes");
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
        throw new SecondParseException("Intermediate parser shouldn't encounter any bare HardLineBreak nodes");
    }

    @Override
    public void visit(HtmlInline htmlInline) {
        throw new SecondParseException("Intermediate parser shouldn't encounter any bare inline HTML");
    }

    @Override
    public void visit(Image image) {
        throw new SecondParseException("Intermediate parser shouldn't encounter any bare images");
    }

    @Override
    public void visit(Link link) {
        throw new SecondParseException("Intermediate parser shouldn't encounter any bare Links");
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        throw new SecondParseException("Intermediate parser shouldn't encounter any bare soft line breaks");
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        throw new SecondParseException("Intermediate parser shouldn't encounter any bare StrongEmphasis");
    }

    @Override
    public void visit(Text text) {
        throw new SecondParseException("Intermediate parser shouldn't encounter any bare Text");
    }

    @Override
    public void visit(LinkReferenceDefinition linkReferenceDefinition) {
        throw new SecondParseException("Intermediate parser shouldn't encounter any bare link reference definitions");
    }
}
