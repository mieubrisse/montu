package com.strangegrotto.montu;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.strangegrotto.montu.view.BulletListMarker;
import com.strangegrotto.montu.view.ChecklistItemInteractable;
import com.strangegrotto.montu.view.OrdenalListMarker;
import com.strangegrotto.montu.view.TextComponent;

import java.io.IOException;
import java.util.List;

public class MontuInstance {
    private final List<Component> components;

    public MontuInstance(List<Component> components) {
        this.components = components;
    }

    public void run() throws IOException  {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create panel to hold components
        Panel parentPanel = new Panel(new LinearLayout());
        this.components.stream().forEach(parentPanel::addComponent);

        // Create window to hold the panel
        BasicWindow window = new BasicWindow();
        window.setComponent(parentPanel);

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(
                screen,
                new DefaultWindowManager(),
                new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindowAndWait(window);

    }

}