package fi.redgrenade.summarizer.representations.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.redgrenade.summarizer.representations.views.ArticleView;

import java.util.List;

/**
 * Created by aleksandr on 24.11.2017.
 */
public class ArticleResponse {
    @JsonProperty
    private Long count;

    @JsonProperty
    private List<ArticleView> articles;

    public ArticleResponse() {}

    public ArticleResponse (
            List<ArticleView> articles,
            Long count
    ) {
        this.articles = articles;
        this.count = count;
    }

}
