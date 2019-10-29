package me.lesonnnn.demofour.model;

import java.util.List;

public class Item {
    private String mName;
    private List<Image> mImages;

    public Item() {
    }

    public String getName() {
        return mName;
    }

    public List<Image> getImages() {
        return mImages;
    }

    private Item(String name, List<Image> images) {
        mName = name;
        mImages = images;
    }

    public static class ItemBuild {
        private String mName;
        private List<Image> mImages;

        public ItemBuild setName(String name) {
            mName = name;
            return this;
        }

        public ItemBuild setImages(List<Image> images) {
            mImages = images;
            return this;
        }

        public Item build() {
            return new Item(mName, mImages);
        }
    }

    public static class ItemEntry {
        public static final String NAME = "name";
        public static final String IMAGES = "images";
    }
}
