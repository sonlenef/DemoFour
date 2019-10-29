package me.lesonnnn.demofour.model;

public class Image {
    private String mLink;
    private String mDescription;

    public Image() {
    }

    private Image(String link, String description) {
        mLink = link;
        mDescription = description;
    }

    public String getLink() {
        return mLink;
    }

    public String getDescription() {
        return mDescription;
    }

    public static class ImageBuild {
        private String mLink;
        private String mDescription;

        public ImageBuild setLink(String link) {
            mLink = link;
            return this;
        }

        public ImageBuild setDescription(String description) {
            mDescription = description;
            return this;
        }

        public Image build() {
            return new Image(mLink, mDescription);
        }
    }

    public static class ItemEntry {
        public static final String LINK = "name";
        public static final String DESCRIPTION = "description";
    }
}
