package com.strangegrotto.montu;

import com.strangegrotto.montu.model.Model;
import com.strangegrotto.montu.view.View;

import java.io.IOException;

public class MontuInstance {
    private final View view;

    public MontuInstance(View view) {
        this.view = view;
    }

    public void run() throws IOException  {
        this.view.present();
    }

}