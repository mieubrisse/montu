package com.strangegrotto.montu.parse.firstparse;

import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import com.googlecode.lanterna.gui2.Component;
import com.strangegrotto.montu.MontuInstance;
import com.strangegrotto.montu.model.Model;
import com.strangegrotto.montu.parse.listmarkers.ListMarkerSupplier;
import com.strangegrotto.montu.parse.render.MultipleBlockNodeRenderer;
import com.strangegrotto.montu.view.ChecklistItemInteractable;
import com.strangegrotto.montu.view.TextComponent;
import org.commonmark.node.Block;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// TODO Move next to MontuInstance and make constructor private
public class MontuInstanceBuilder {
    // Components in their on-screen order
    private final List<UUID> componentDisplayOrder;

    private final Map<UUID, Component> components;

    private final Map<UUID, Boolean> isCompleted;

    // Tracks completable parents, if any
    // A missing entry indicates no parent
    private final Map<UUID, UUID> parents;

    // Tracks completable children
    private final SetMultimap<UUID, UUID> children;


    public MontuInstanceBuilder() {
        this.componentDisplayOrder = List.of();
        this.components = Map.of();
        this.isCompleted = Map.of();
        this.parents = Map.of();
        this.children = MultimapBuilder.hashKeys().hashSetValues().build();
    }

    public void addTextComponent(int indentationLevel, Block block) {
        var lines = MultipleBlockNodeRenderer.render(List.of(block));
        var component = new TextComponent(indentationLevel, lines);
        addComponent(UUID.randomUUID(), component);
    }

    public void addChecklistItemComponent(
            Optional<UUID> parentIdOpt,
            UUID id,
            int indentationLevel,
            ListMarkerSupplier listMarkerSupplier,
            boolean isChecked,
            List<Block> descBlocks) {
        if (parentIdOpt.isPresent()) {
            var parentId = parentIdOpt.get();
            this.parents.put(id, parentId);
            this.children.put(parentId, id);
        }

        this.isCompleted.put(id, isChecked);
        List<String> descLines = MultipleBlockNodeRenderer.render(descBlocks);
        var component = new ChecklistItemInteractable(
                indentationLevel,
                listMarkerSupplier.get(),
                isChecked,
                descLines);

        addComponent(id, component);
    }

    public MontuInstance build() {
        var componentsForInstance = this.componentDisplayOrder.stream()
                .map(id -> this.components.get(id))
                .collect(Collectors.toList());
        // TODO rest of components
        return new MontuInstance(componentsForInstance);
    }

    // =============================================================================================================================
    //                                        Private helper methods
    // =============================================================================================================================
    private void addComponent(UUID id, Component component) {
        this.componentDisplayOrder.add(id);
        this.components.put(id, component);
    }
}
