package com.strangegrotto.montu.parse.firstparse;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.googlecode.lanterna.gui2.Component;
import com.strangegrotto.montu.MontuInstance;
import com.strangegrotto.montu.controller.Controller;
import com.strangegrotto.montu.model.Model;
import com.strangegrotto.montu.parse.render.MultipleBlockNodeRenderer;
import com.strangegrotto.montu.view.View;
import com.strangegrotto.montu.view.component.base.MontuComponent;
import com.strangegrotto.montu.view.component.checklistitem.ChecklistItemInteractable;
import com.strangegrotto.montu.view.component.checklistitem.ListMarker;
import com.strangegrotto.montu.view.component.text.TextComponent;
import org.commonmark.node.Block;

import java.util.*;

// TODO Move next to MontuInstance and make constructor private
public class MontuInstanceBuilder {
    // Components in their on-screen order
    private final List<UUID> componentDisplayOrder;

    private final Set<UUID> interactableComponents;

    private final Map<UUID, MontuComponent> components;

    private final Map<UUID, DeferredChecklistComponentInfo> deferredChecklistComponentInfo;

    private final Map<UUID, Boolean> isCompleted;

    // Tracks completable parents, if any
    // A missing entry indicates no parent
    private final Map<UUID, UUID> parents;

    // Tracks completable children
    private final SetMultimap<UUID, UUID> children;



    // This is the package class used to store information between the call to addChecklistItemComponent
    //  and the call to addChecklistItemComponentDescription
    private static class DeferredChecklistComponentInfo {
        private final int indenatationLevel;
        private final ListMarker listMarker;
        private final boolean isChecked;

        public DeferredChecklistComponentInfo(int indenatationLevel, ListMarker listMarker, boolean isChecked) {
            this.indenatationLevel = indenatationLevel;
            this.listMarker = listMarker;
            this.isChecked = isChecked;
        }
    }


    public MontuInstanceBuilder() {
        this.componentDisplayOrder = new ArrayList<>();
        this.interactableComponents = new HashSet<>();
        this.components = new HashMap<>();
        this.deferredChecklistComponentInfo = new HashMap<>();
        this.isCompleted = new HashMap<>();
        this.parents = new HashMap<>();
        this.children = MultimapBuilder.hashKeys().hashSetValues().build();
    }

    public void addTextComponent(int indentationLevel, Block block) {
        var lines = MultipleBlockNodeRenderer.render(List.of(block));
        var component = new TextComponent(indentationLevel, lines);
        var id = UUID.randomUUID();
        this.componentDisplayOrder.add(id);
        this.components.put(id, component);
    }

    public void addChecklistItemComponent(
            Optional<UUID> parentIdOpt,
            UUID id,
            int indentationLevel,
            ListMarker listMarker,
            boolean isChecked) {
        if (parentIdOpt.isPresent()) {
            var parentId = parentIdOpt.get();
            this.parents.put(id, parentId);
            this.children.put(parentId, id);
        }
        this.isCompleted.put(id, isChecked);
        this.componentDisplayOrder.add(id);
        this.interactableComponents.add(id);
        var deferredComponentInfo = new DeferredChecklistComponentInfo(
                indentationLevel,
                listMarker,
                isChecked);
        this.deferredChecklistComponentInfo.put(id, deferredComponentInfo);
    }

    // The description needs to be filled in afterwards because we only know that after we parse the
    //  children of a Markdown ListItem. Sucks that it's not atomic, but not the end of the world
    public void addChecklistItemComponentDescription(UUID id, List<Block> descBlocks) {
        List<String> descLines = MultipleBlockNodeRenderer.render(descBlocks);
        var deferredComponentInfo = this.deferredChecklistComponentInfo.get(id);
        var component = new ChecklistItemInteractable(
                deferredComponentInfo.indenatationLevel,
                deferredComponentInfo.listMarker,
                deferredComponentInfo.isChecked,
                descLines);
        this.components.put(id, component);
    }

    public MontuInstance build() {
        var allComponents = new ArrayList<MontuComponent>();
        var checklistItemComponentIndices = new HashSet<Integer>();
        var isCompleteArray = new ArrayList<Boolean>();
        for (int i = 0; i < componentDisplayOrder.size(); i++) {
            var id = this.componentDisplayOrder.get(i);
            var component = this.components.get(id);
            allComponents.add(component);

            if (this.interactableComponents.contains(id)) {
                checklistItemComponentIndices.add(i);
                isCompleteArray.add(this.isCompleted.get(id));
            }
        }

        var view = new View(allComponents, checklistItemComponentIndices);
        var model = new Model(view, isCompleteArray);
        var controller = new Controller(model);
        view.registerController(controller);

        /*
        // Try and move the cursor down an item, so that it starts on the first item (if there is any)
        model.moveCursorDownOneItem();

         */

        return new MontuInstance(view);
    }
}
