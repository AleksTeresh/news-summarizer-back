package fi.redgrenade.summarizer.representations.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.redgrenade.summarizer.db.tables.pojos.Article;

import java.util.List;

/**
 * Created by aleksandr on 24.11.2017.
 */
public class ArticleView {

    @JsonProperty
    private Long   id;

    @JsonProperty
    private String header;

    @JsonProperty
    private String content;

    @JsonProperty
    private String summary;

    @JsonProperty
    private Long timestamp;

    @JsonProperty
    private List<String> keyWords;

    public ArticleView () {}

    public ArticleView(
            Long id,
            String header,
            String content,
            String summary,
            Long timestamp,
            List<String> keyWords
    ) {
        this.id = id;
        this.header = header;
        this.content = content;
        this.summary = summary;
        this.timestamp = timestamp;
        this.keyWords = keyWords;
    }

    public static ArticleView fromEntity(Article article, List<String> keyWords) {
        return new ArticleView(
                article.getId(),
                article.getHeader(),
                article.getContent(),
                article.getSummary(),
                article.getTimestamp().getTime(),
                keyWords
        );
    }
}
