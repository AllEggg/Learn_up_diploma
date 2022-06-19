import java.util.Objects;

public class Suggest {
    private final String keyWord;
    private final String title;
    private final String url;

    public Suggest(String keyWord, String title, String url) {
        this.keyWord = keyWord;
        this.title = title;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Suggest{" +
                "keyWord='" + keyWord + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Suggest)) return false;
        Suggest suggest = (Suggest) o;
        return Objects.equals(getTitle(), suggest.getTitle()) && Objects.equals(getUrl(), suggest.getUrl());
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getKeyWord() {
        return keyWord;
    }
}
