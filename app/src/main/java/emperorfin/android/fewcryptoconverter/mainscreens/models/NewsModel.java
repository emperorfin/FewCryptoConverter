package emperorfin.android.fewcryptoconverter.mainscreens.models;

import androidx.annotation.DrawableRes;

public class NewsModel {
    @DrawableRes
    private int iconDrawable;

    private String title;
    private String author;
    private String publishTime;
    private String newsUrl;
    private String iconUrl;

    public NewsModel() {
    }

    public NewsModel(@DrawableRes int icon, String title, String author, String publishTime, String url, String iconUrl) {
        this.iconDrawable = icon;
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.newsUrl = url;
        this.iconUrl = iconUrl;
    }

    @DrawableRes
    public int getIconDrawable() {
        return iconDrawable;
    }

    public NewsModel setIconDrawable(@DrawableRes int iconDrawable) {
        this.iconDrawable = iconDrawable;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NewsModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public NewsModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public NewsModel setPublishTime(String publishTime) {
        this.publishTime = publishTime;
        return this;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public NewsModel setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
        return this;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public NewsModel setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }
}
