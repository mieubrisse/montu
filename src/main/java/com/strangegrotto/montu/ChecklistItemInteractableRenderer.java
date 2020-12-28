package com.strangegrotto.montu;

import com.google.common.base.Strings;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.ThemeStyle;
import com.googlecode.lanterna.gui2.InteractableRenderer;
import com.googlecode.lanterna.gui2.TextGUIGraphics;
import com.strangegrotto.montu.render.ChecklistItemInteractable;

public class ChecklistItemInteractableRenderer implements InteractableRenderer<ChecklistItemInteractable> {
    @Override
    public TerminalPosition getCursorLocation(ChecklistItemInteractable component) {
        // Don't show the cursor
        return null;
    }

    @Override
    public TerminalSize getPreferredSize(ChecklistItemInteractable component) {
        // TODO Handle multiline strings
        int width = getItemString(component).length();
        return new TerminalSize(width, 1);
    }

    @Override
    public void drawComponent(TextGUIGraphics graphics, ChecklistItemInteractable component) {
        var theme = component.getTheme();
        var definition = theme.getDefinition(ChecklistItemInteractable.class);

        ThemeStyle style;
        if (component.isFocused()) {
            style = definition.getSelected();
        } else {
            style = definition.getNormal();
        }
        var itemString = getItemString(component);

        graphics.putString(0, 0, itemString, style.getSGRs());
    }

    private static String getItemString(ChecklistItemInteractable item) {
        String leader;
        if (item.isFocused()) {
            leader = "-> ";
        } else {
            leader = "   ";
        }
        var indentation = Strings.repeat("   ", item.getNestLevel());
        var checkboxCore = item.isChecked() ? "x" : " ";
        return leader + indentation + item.getOrdenalString() + " [" + checkboxCore + "] " + item.getText();
    }
}
