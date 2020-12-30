package com.strangegrotto.montu.view;

import com.google.common.base.Strings;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.googlecode.lanterna.gui2.TextGUIGraphics;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMontuComponentRenderer<T extends MontuComponent> implements ComponentRenderer<T> {
    // How much space to leave between the gutter marker and the actual text
    private static final int GUTTER_MARKER_BUFFER = 1;

    private static final int SPACES_PER_INDENTATION_LEVEL = 3;

    // Number of levels of indentation this component resides at
    private final int indentationLevel;

    protected AbstractMontuComponentRenderer(int indentationLevel) {
        this.indentationLevel = indentationLevel;
    }

    @Override
    public TerminalSize getPreferredSize(T component) {
        var linesWithPrefix = getLinesWithPrefix(component);
        var longestLineLenOpt = linesWithPrefix.stream()
                .map(String::length)
                .max(Integer::compareTo);
        var longestLineLen = longestLineLenOpt.orElseThrow(() -> new IllegalStateException(
                "Component doesn't have any lines; this must be a code bug"
        ));
        return new TerminalSize(longestLineLen, linesWithPrefix.size());
    }

    @Override
    public void drawComponent(TextGUIGraphics graphics, T component) {
        var linesWithPrefix = getLinesWithPrefix(component);

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

        var indentationLevel = this.indentationLevel;
        var indentationStr = Strings.repeat(" ", indentationLevel * SPACES_PER_INDENTATION_LEVEL);

        var componentLines = component.getLines();
        var result = new ArrayList<String>();
        for (int i = 0; i < componentLines.size(); i++) {
            // Only the first line of the component will receive the gutter marker
            GutterMarker gutterMarker = GutterMarker.BLANK;
            if (i == 0) {
                gutterMarker = component.getGutterMarker();
            }
            var gutterStr = gutterMarker.getStr() + Strings.repeat(" ", GUTTER_MARKER_BUFFER);

            var componentLine = componentLines.get(i);
            result.add(gutterStr + indentationStr + componentLine);
        }
        return result;
    }
}
