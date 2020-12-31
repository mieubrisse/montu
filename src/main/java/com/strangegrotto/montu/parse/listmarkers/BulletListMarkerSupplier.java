package com.strangegrotto.montu.parse.listmarkers;

import com.strangegrotto.montu.view.BulletListMarker;
import com.strangegrotto.montu.view.ListMarker;

public class BulletListMarkerSupplier implements ListMarkerSupplier {
    private final char bullet;

    public BulletListMarkerSupplier(char bullet) {
        this.bullet = bullet;
    }

    @Override
    public ListMarker get() {
        return new BulletListMarker(this.bullet);
    }
}
