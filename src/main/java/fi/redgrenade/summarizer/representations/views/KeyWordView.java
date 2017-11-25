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
    private String word;

    @JsonProperty
    private int weight;

    public KeyWordView () {}

    public KeyWordView(
            Long id,
            String word,
            int weight
    ) {
        this.id = id;
        this.word = word;
        this.weight = weight;
    }

    public static KeyWordView fromEntity(KeyWord keyWord) {
        return new KeyWordView(
                keyWord.getId(),
                keyWord.getWord(),
                0
        );
    }

    public void setWord (String word) {
        this.word = word;
    }
}
