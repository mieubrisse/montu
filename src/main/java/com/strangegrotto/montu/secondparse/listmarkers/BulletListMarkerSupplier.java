package com.strangegrotto.montu.secondparse.listmarkers;

public class BulletListMarkerSupplier implements ListMarkerSupplier {
    private final char bullet;

    public BulletListMarkerSupplier(char bullet) {
        this.bullet = bullet;
    }

    @Override
    public String get() {
        return Character.toString(this.bullet);
    }
}
