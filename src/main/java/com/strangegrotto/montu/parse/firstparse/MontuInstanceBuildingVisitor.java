package com.strangegrotto.montu.parse.firstparse;

import com.strangegrotto.montu.MontuInstance;
import com.strangegrotto.montu.parse.listmarkers.BulletListMarkerSupplier;
import com.strangegrotto.montu.parse.listmarkers.ListMarkerSupplier;
import com.strangegrotto.montu.parse.listmarkers.OrderedListMarkerSupplier;
import org.commonmark.ext.task.list.items.TaskListItemMarker;
import org.commonmark.node.*;

import java.util.*;

public class MontuInstanceBuildingVisitor extends AbstractVisitor {
    private final MontuInstanceBuilder resultBuilder;

    // ----------- Variables related to building the result --------------
    // TODO package these together into a single class!
    private Optional<UUID> parentIdOpt;
    private Optional<List<Block>> parentDescBlocksOpt;

    // How much indentation we should use
    private int indentationLevel;

    // When processing a list, this will be non-empty and list items will be able to consume it
    //  to get the marker that they should use when rendering themselves
    private Optional<ListMarkerSupplier> listMarkerSupplierOpt;

    public MontuInstanceBuildingVisitor() {
        this.resultBuilder = new MontuInstanceBuilder();

        this.parentIdOpt = Optional.empty();
        this.listMarkerSupplierOpt = Optional.empty();

        this.indentationLevel = 0;
    }

    public MontuInstance getMontuInstance() {
        return resultBuilder.build();

    }

    @Override
    public void visit(Document document) {
        this.visitChildren(document);
    }

    // =============================================================================================================================
    //                               List items & blocks, which is where the magic is
    // =============================================================================================================================
    @Override
    public void visit(BulletList bulletList) {
        var markerSupplier = new BulletListMarkerSupplier(bulletList.getBulletMarker());
        visitListNode(bulletList, markerSupplier);
    }

    @Override
    public void visit(OrderedList orderedList) {
        var markerSupplier = new OrderedListMarkerSupplier(
                orderedList.getStartNumber(),
                orderedList.getDelimiter()
        );
        visitListNode(orderedList, markerSupplier);
    }


    @Override
    public void visit(ListItem listItem) {
        // TODO Maybe remove this requirement and just call all list items checklist items?
        var uncastedCheckbox = listItem.getFirstChild();
        if (!(uncastedCheckbox instanceof TaskListItemMarker)) {
            throw new FirstParseException("Found a list item that wasn't a checklist item");
        }
        var checkbox = (TaskListItemMarker)uncastedCheckbox;

        var listMarkerSupplier = this.listMarkerSupplierOpt.orElseThrow(() -> new FirstParseException(
                "Encountered a list item and so expected to have a list marker provider, " +
                        "but none was found; this is a code bug"
        ));

        // Visit children to get an idea of what's going on below
        var currentParentOpt = this.parentIdOpt;
        var currentParentDescBlocksOopt = this.parentDescBlocksOpt;

        var id = UUID.randomUUID();
        this.parentIdOpt = Optional.of(id);
        this.parentDescBlocksOpt = Optional.of(new ArrayList<>());

        this.visitChildren(listItem);

        var childDescBlocks = this.parentDescBlocksOpt.get();
        this.parentIdOpt = currentParentOpt;
        this.parentDescBlocksOpt = currentParentDescBlocksOopt;

        this.resultBuilder.addChecklistItemComponent(
                this.parentIdOpt,
                id,
                this.indentationLevel,
                listMarkerSupplier,
                checkbox.isChecked(),
                childDescBlocks);
    }

    private void visitListNode(ListBlock listNode, ListMarkerSupplier itemMarkerSupplier) {
        // The root case is special - even though we'll encounter a list block, we don't want to indent
        //  because we want root-level checklist items to sit flush with the left margin
        var doIndent = this.parentIdOpt.isPresent();

        var currentListMarkerSupplierOpt = this.listMarkerSupplierOpt;
        this.listMarkerSupplierOpt = Optional.of(itemMarkerSupplier);
        if (doIndent) {
            this.indentationLevel++;
        }
        this.visitChildren(listNode);
        if (doIndent) {
            this.indentationLevel--;
        }
        this.listMarkerSupplierOpt = currentListMarkerSupplierOpt;
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
        if (!this.parentIdOpt.isPresent()) {
            // This is a root-level block outcome; we need to create a new component for it
            this.resultBuilder.addTextComponent(this.indentationLevel, blockNode);
        } else {
            // This is a block inside a checklist item, so we need to attach it as a description
            var parentDescBlocks = this.parentDescBlocksOpt.get();
            parentDescBlocks.add(blockNode);
        }
        // Block node children exploration will happen during rendering - don't do it here
    }

    // =============================================================================================================================
    //       Nodes that only show up in blocks, which this parser should never see since it doesn't process blocks
    // =============================================================================================================================
    @Override
    public void visit(CustomNode customNode) {
        if (customNode instanceof TaskListItemMarker) {
            return;
        }
        throw new FirstParseException("Intermediate parser should never see custom nodes outside of TaskListItemMarker");
    }

    @Override
    public void visit(Code code) {
        throw new FirstParseException("Intermediate parser shouldn't encounter any bare Code text");
    }

    @Override
    public void visit(Emphasis emphasis) {
        throw new FirstParseException("Intermediate parser shouldn't encounter any bare Emphasis nodes");
    }

    @Override
    public void visit(HardLineBreak hardLineBreak) {
        throw new FirstParseException("Intermediate parser shouldn't encounter any bare HardLineBreak nodes");
    }

    @Override
    public void visit(HtmlInline htmlInline) {
        throw new FirstParseException("Intermediate parser shouldn't encounter any bare inline HTML");
    }

    @Override
    public void visit(Image image) {
        throw new FirstParseException("Intermediate parser shouldn't encounter any bare images");
    }

    @Override
    public void visit(Link link) {
        throw new FirstParseException("Intermediate parser shouldn't encounter any bare Links");
    }

    @Override
    public void visit(SoftLineBreak softLineBreak) {
        throw new FirstParseException("Intermediate parser shouldn't encounter any bare soft line breaks");
    }

    @Override
    public void visit(StrongEmphasis strongEmphasis) {
        throw new FirstParseException("Intermediate parser shouldn't encounter any bare StrongEmphasis");
    }

    @Override
    public void visit(Text text) {
        throw new FirstParseException("Intermediate parser shouldn't encounter any bare Text");
    }

    @Override
    public void visit(LinkReferenceDefinition linkReferenceDefinition) {
        throw new FirstParseException("Intermediate parser shouldn't encounter any bare link reference definitions");
    }
}
