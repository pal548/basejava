package ru.javawebinar.basejava.model;

public enum ContactType {
    EMAIL("e-mail") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("mailto:"+value, value);
        }
    },
    PHONE("Телефон"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("skype:"+value, value);
        }
    },
    VK("страница ВКонтакте") {
        @Override
        public String toHtml0(String value) {
            return toLink(value, getTitle());
        }
    },
    FB("страница в Facebook"){
        @Override
        public String toHtml0(String value) {
            return toLink(value, getTitle());
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href, String title) {
        return "<a href=\"" + href + "\">" + title + "</a>";
    }


}
