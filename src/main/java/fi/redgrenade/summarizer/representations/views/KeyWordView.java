package fi.redgrenade.summarizer.representations.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import fi.redgrenade.summarizer.db.tables.pojos.KeyWord;

/**
 * Created by aleksandr on 24.11.2017.
 */
public class KeyWordView {

    @JsonProperty
    private Long   id;

    @JsonProperty
    private Long articleId;

    @JsonProperty
    private String word;

    public KeyWordView () {}

    public KeyWordView(
            Long id,
            Long articleId,
            String word
    ) {
        this.id = id;
        this.articleId = articleId;
        this.word = word;
    }

    public static KeyWordView fromEntity(KeyWord keyWord, Long articleId) {
        return new KeyWordView(
                keyWord.getId(),
                articleId,
                keyWord.getWord()
        );
    }
}
