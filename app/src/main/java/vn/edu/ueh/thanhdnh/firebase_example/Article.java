package vn.edu.ueh.thanhdnh.firebase_example;

import java.io.Serializable;

public class Article implements Serializable {
    private String id;
    private String title;
    private String content;
    private String img_cover;
    private int view;

    public Article() {}

    public Article(String id, String title, String content, String img_cover, int view) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.img_cover = img_cover;
        this.view = view;
    }

    public Article(String title, String content, String img_cover, int view) {
        this.title = title;
        this.content = content;
        this.img_cover = img_cover;
        this.view = view;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImg_cover() { return img_cover; }
    public void setImg_cover(String img_cover) { this.img_cover = img_cover; }

    public int getView() { return view; }
    public void setView(int view) { this.view = view; }
}