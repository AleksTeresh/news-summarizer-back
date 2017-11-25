package fi.redgrenade.summarizer.dao;

import fi.redgrenade.summarizer.db.Tables;
import fi.redgrenade.summarizer.db.tables.ArticleKeyWord;
import fi.redgrenade.summarizer.db.tables.daos.ArticleKeyWordDao;
import fi.redgrenade.summarizer.db.tables.records.ArticleKeyWordRecord;
import fi.redgrenade.summarizer.representations.views.KeyWordView;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 24.11.2017.
 */
@Repository
public class ExArticleKeyWordDao extends ArticleKeyWordDao {
    private DSLContext dsl;

    @Autowired
    public ExArticleKeyWordDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }

    public List<KeyWordView> fetchKeyWords(int limit, int offset) {
//        Result<Record2<String, Integer>> result =
        return this.dsl.select(Tables.KEY_WORD.WORD, Tables.KEY_WORD.ID, DSL.count().neg().as("weight"))
                .from(Tables.KEY_WORD)
                .join(Tables.ARTICLE_KEY_WORD)
                .on(Tables.ARTICLE_KEY_WORD.KEY_WORD_ID.equal(Tables.KEY_WORD.ID))
                .groupBy(Tables.KEY_WORD.WORD, Tables.KEY_WORD.ID)
                .orderBy(2)
                .offset(offset)
                .limit(limit)
                .fetch()
                .stream()
                .map(p -> new KeyWordView(
                        p.value2(),
                        p.value1(),
                        -p.value3())
                ).collect(Collectors.toList());
    }
}
