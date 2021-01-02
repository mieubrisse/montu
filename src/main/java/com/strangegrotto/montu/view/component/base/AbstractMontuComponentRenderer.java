package com.strangegrotto.montu.view.component.base;

import com.google.common.base.Strings;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.googlecode.lanterna.gui2.TextGUIGraphics;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMontuComponentRenderer<T extends MontuComponent> implements ComponentRenderer<T> {
    private static final int SPACES_PER_INDENTATION_LEVEL = 4;

    @Override
    public TerminalSize getPreferredSize(T component) {
        var linesWithPrefix = getLinesWithPrefix(component);
        if (linesWithPrefix.size() == 0) {
            return TerminalSize.ZERO;
        }

        var longestLineLenOpt = linesWithPrefix.stream()
                .map(String::length)
                .max(Integer::compareTo);
        var longestLineLen = longestLineLenOpt.orElseThrow(() -> new IllegalStateException(
                "Component doesn't have any lines; this must be a code bug"
        ));
        var preferredSize = new TerminalSize(longestLineLen, linesWithPrefix.size());
        // TODO Debugging
        System.out.println("Size: " + preferredSize.getColumns() + ", " + preferredSize.getRows());
        return preferredSize;
    }

    @Override
    public void drawComponent(TextGUIGraphics graphics, T component) {
        var linesWithPrefix = getLinesWithPrefix(component);
        if (linesWithPrefix.size() == 0) {
            return;
        }

        var termSize = graphics.getTextGUI().getScreen().getTerminalSize();
        var termWidth = termSize.getColumns();

        var truncatedLines = new ArrayList<String>(linesWithPrefix.size());
        for (var line : linesWithPrefix) {
            var truncateLength = Math.min(termWidth, line.length());
            var truncatedLine = line.substring(0, truncateLength);
            truncatedLines.add(truncatedLine);
        }

        for (int i = 0; i < truncatedLines.size(); i++) {
            var truncatedLine = truncatedLines.get(i);
            graphics.putString(0, i, truncatedLine);
        }
    }

    // Adds the gutter string and indentation to each component line
    private List<String> getLinesWithPrefix(T component) {
        var indentationStr = getIndentationStr(component);
        var componentLines = component.getLines();
        var filteredComponentLines = component.getLinesFilter().filterDisplayLines(componentLines);
        var result = new ArrayList<String>();
        for (var line : filteredComponentLines) {
            result.add(indentationStr + line);
        }
        return result;
    }

    // The indentation string that will be prefixed to every line the component declares
    protected String getIndentationStr(T component) {
        var indentationLevel = component.getIndentationLevel();
        var indentationStr = Strings.repeat(" ", indentationLevel * SPACES_PER_INDENTATION_LEVEL);
        return indentationStr;
    }
}
