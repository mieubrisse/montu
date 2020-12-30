package com.strangegrotto.montu.view;

import com.googlecode.lanterna.gui2.AbstractComponent;
import com.googlecode.lanterna.gui2.ComponentRenderer;

import java.util.List;

public class TextComponent extends AbstractComponent<TextComponent> implements MontuComponent {
    private final int indentationLevel;
    private final List<String> lines;

    public TextComponent(int indentationLevel, List<String> lines) {
        this.indentationLevel = indentationLevel;
        this.lines = lines;
    }

    @Override
    protected ComponentRenderer<TextComponent> createDefaultRenderer() {
        return new TextComponentRenderer(this.indentationLevel);
    }

    @Override
    public GutterMarker getGutterMarker() {
        return GutterMarker.BLANK;
    }

    @Override
    public List<String> getLines() {
        return this.lines;
    }
}
