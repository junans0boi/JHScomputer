package com.example.jhscomputer.youtube.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YouTubeResponse {
    private List<Item> items;

    @Getter
    @Setter
    public static class Item {
        private Id id;
        private Snippet snippet;
    }

    @Getter
    @Setter
    public static class Id {
        private String kind;
        private String videoId;
    }

    @Getter
    @Setter
    public static class Snippet {
        private String title;
        private String publishedAt;
        private Thumbnails thumbnails;
    }

    @Getter
    @Setter
    public static class Thumbnails {
        private Thumbnail high;
    }

    @Getter
    @Setter
    public static class Thumbnail {
        private String url;
    }
}