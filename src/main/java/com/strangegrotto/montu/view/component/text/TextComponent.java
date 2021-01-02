package com.strangegrotto.montu.view.component.text;

import com.googlecode.lanterna.gui2.AbstractComponent;
import com.googlecode.lanterna.gui2.ComponentRenderer;
import com.strangegrotto.montu.view.component.base.DisplayLinesFilter;
import com.strangegrotto.montu.view.component.base.MontuComponent;
import com.strangegrotto.montu.view.component.base.ShowAllLinesFilter;

import java.util.List;

public class TextComponent extends AbstractComponent<TextComponent> implements MontuComponent {
    private final int indentationLevel;
    private final List<String> lines;
    private DisplayLinesFilter linesFilter;

    public TextComponent(int indentationLevel, List<String> lines) {
        this.indentationLevel = indentationLevel;
        this.lines = lines;
        this.linesFilter = new ShowAllLinesFilter();
    }

    @Override
    protected ComponentRenderer<TextComponent> createDefaultRenderer() {
        return new TextComponentRenderer();
    }

    @Override
    public int getIndentationLevel() {
        return this.indentationLevel;
    }

    @Override
    public List<String> getLines() {
        return this.lines;
    }

    @Override
    public DisplayLinesFilter getLinesFilter() {
        return this.linesFilter;
    }

    @Override
    public void setLinesFilter(DisplayLinesFilter filter) {
        this.linesFilter = filter;
    }
}
