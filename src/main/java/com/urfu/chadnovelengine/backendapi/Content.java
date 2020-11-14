package com.urfu.chadnovelengine.backendapi;

public class Content {
    public final String name;
    public ContentType contentType;

    public Content(String name, String type) {
        this.name = name;
        switch (type) {
            case "image" -> contentType = ContentType.IMAGE;
            case "music" -> contentType = ContentType.MUSIC;
            case "video" -> contentType = ContentType.VIDEO;
            case "doc" -> contentType = ContentType.DOCUMENT;
        }
    }
}

enum ContentType { IMAGE, MUSIC, VIDEO, DOCUMENT }
