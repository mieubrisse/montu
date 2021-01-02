package com.strangegrotto.montu.view;

import com.google.common.base.Preconditions;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.strangegrotto.montu.controller.Controller;
import com.strangegrotto.montu.view.checklistitem.ChecklistItemInteractable;

import java.io.IOException;
import java.util.*;

public class View {
    private final List<MontuComponent> components;
    private final List<String> allComponentLines;

    // For component N, yields the index in allComponentLines where the component's lines start
    private final Map<Integer, Integer> allComponentLinesIndices;
    private final List<ChecklistItemInteractable> checklistItems;

    // Because there's a necessary circular dependency between Controller -> Model -> View -> Controller,
    //  we have to break it with a non-final variable
    private Optional<MontuWindow> windowOpt;

    public View(List<MontuComponent> components, Set<Integer> checklistItemIndices) {
        Preconditions.checkArgument(
                checklistItemIndices.size() <= components.size(),
                "Number of checklist item compnents cannot be > number of components"
        );
        this.components = components;

        // Generate a list of all the component lines in sequential order, and a map of
        //  component_index -> index_into_all_component_lines where that component's lines start
        this.allComponentLines = new ArrayList<>();
        this.allComponentLinesIndices = new HashMap<>();
        var allComponentLinesIdx = 0;
        for (int componentIdx = 0; componentIdx < this.components.size(); componentIdx++) {
            var component = this.components.get(componentIdx);
            var lines = component.getLines();
            if (lines.size() > 0) {
                this.allComponentLinesIndices.put(componentIdx, allComponentLinesIdx);
            }
            for (var line : lines) {
                this.allComponentLines.add(line);
                allComponentLinesIdx++;
            }
        }

        var checklistItems = new ArrayList<ChecklistItemInteractable>();
        for (int i = 0; i < components.size(); i++) {
            var component = components.get(i);
            if (checklistItemIndices.contains(i)) {
                var interactable = (ChecklistItemInteractable)component;
                checklistItems.add(interactable);
            }
        }
        this.checklistItems = checklistItems;
        this.windowOpt = Optional.empty();
    }

    // This is the registration function to set the window, which breaks the circular dependency
    public void registerController(Controller controller) {
        // Create window to hold the panel
        var window = new MontuWindow(controller);

        // Create panel to hold components
        Panel parentPanel = new Panel(new LinearLayout());
        this.components.stream().forEach(parentPanel::addComponent);

        window.setComponent(parentPanel);
        window.setHints(Set.of(Window.Hint.NO_DECORATIONS, Window.Hint.FULL_SCREEN));

        this.windowOpt = Optional.of(window);
    }

    public int getNumChecklistItems() {
        return this.checklistItems.size();
    }

    public void present() throws IOException {
        Preconditions.checkState(
                this.windowOpt.isPresent(),
                "No controller has been registered so no window can be created");
        var window = this.windowOpt.get();

        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(
                screen,
                new DefaultWindowManager(),
                new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindowAndWait(window);
    }

    public void focusChecklistItem(int index) {
        Preconditions.checkState(
                this.windowOpt.isPresent(),
                "No controller has been registered so no window is created and no item can be focused"
        );
        Preconditions.checkState(index >= 0, "Cannot focus a checklist item with index less than 0");
        Preconditions.checkState(index < this.checklistItems.size(), "Requested to focus a checklist item index that's greater than the array has");

        if (index == 0) {
            // Stack all components on bottom
        } else if (index == this.checklistItems.size() - 1) {
            // Stack all components on top
        }

        var interactableToFocus = this.checklistItems.get(index);
        var window = this.windowOpt.get();
        window.setFocusedInteractable(interactableToFocus);
    }

    // TODO DEBUGGING
    public void recalculateView(Window window, int index) {
        var windowSize = window.getSize();
        var windowHeight = windowSize.getRows();

        var linesUsed =
        var viewStartIndex = index;


        this.windowOpt.get().invalidate();
    }

    public void setChecklistItemState(int index, boolean isChecked) {
        var component = this.checklistItems.get(index);
        component.setState(isChecked);
    }
}
