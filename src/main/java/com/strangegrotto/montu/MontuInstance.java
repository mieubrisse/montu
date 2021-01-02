package com.strangegrotto.montu;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.strangegrotto.montu.view.View;

import java.io.IOException;
import java.util.List;

public class MontuInstance {
    private final View view;

    public MontuInstance(View view) {
        this.view = view;
    }

    public void run() throws IOException  {
        this.view.present();
    }

}