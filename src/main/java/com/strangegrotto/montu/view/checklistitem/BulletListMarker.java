package com.strangegrotto.montu.view.checklistitem;

public class BulletListMarker implements ListMarker {
    private final char bullet;

    public BulletListMarker(char bullet) {
        this.bullet = bullet;
    }

    @Override
    public String getMarker() {
        return String.valueOf(this.bullet);
    }
}
