package fi.redgrenade.summarizer.dao;

import fi.redgrenade.summarizer.db.Tables;
import fi.redgrenade.summarizer.db.tables.daos.ArticleDao;
import fi.redgrenade.summarizer.db.tables.pojos.Article;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 24.11.2017.
 */
@Repository
public class ExArticleDao extends ArticleDao {

    private DSLContext dsl;

    @Autowired
    public ExArticleDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }

    public List<Article> fetchwithCreateTimeGreaterThan(Timestamp timestamp) {
        return dsl.select()
                .from(Tables.ARTICLE)
                .where(Tables.ARTICLE.ROWCREATETIME.gt(timestamp))
                .fetch(p -> new Article(
                        p.get(Tables.ARTICLE.ID),
                        p.get(Tables.ARTICLE.CONTENT),
                        p.get(Tables.ARTICLE.HEADER),
                        p.get(Tables.ARTICLE.SUMMARY),
                        p.get(Tables.ARTICLE.TIMESTAMP),
                        p.get(Tables.ARTICLE.ROWCREATETIME),
                        p.get(Tables.ARTICLE.CATEGORY),
                        p.get(Tables.ARTICLE.IMAGEURL),
                        p.get(Tables.ARTICLE.EMOTION)
                ));
    }

    public List<Article> fetch(
            List<String> keyWords,
            int limit,
            int offset,
            String category
    ) {
        if (keyWords.size() > 0 || !category.equals("")) {
            Condition condition =
                    Tables.ARTICLE.CATEGORY.contains(category);

            String[] keywordArray = new String[keyWords.size()];

            condition = condition.and(Tables.ARTICLE.ID.in(
                    dsl.select(Tables.ARTICLE_KEY_WORD.ARTICLE_ID)
                            .from(Tables.ARTICLE_KEY_WORD)
                            .where(Tables.ARTICLE_KEY_WORD.KEY_WORD_ID.in(
                                    dsl.select(Tables.KEY_WORD.ID)
                                            .from(Tables.KEY_WORD)
                                            .where(Tables.KEY_WORD.WORD.in(keyWords.toArray(keywordArray)))
                            ))));

            return dsl.select()
                    .from(Tables.ARTICLE)
                    .where(condition)
                    .orderBy(Tables.ARTICLE.TIMESTAMP)
                    .offset(offset)
                    .limit(limit)
                    .stream()
                    .map(p -> new Article(
                            p.get(Tables.ARTICLE.ID),
                            p.get(Tables.ARTICLE.CONTENT),
                            p.get(Tables.ARTICLE.HEADER),
                            p.get(Tables.ARTICLE.SUMMARY),
                            p.get(Tables.ARTICLE.TIMESTAMP),
                            p.get(Tables.ARTICLE.ROWCREATETIME),
                            p.get(Tables.ARTICLE.CATEGORY),
                            p.get(Tables.ARTICLE.IMAGEURL),
                            p.get(Tables.ARTICLE.EMOTION)
                    )).collect(Collectors.toList());
        } else {
            return dsl.select()
                    .from(Tables.ARTICLE)
                    .offset(offset)
                    .limit(limit)
                    .stream()
                    .map(p -> new Article(
                            p.get(Tables.ARTICLE.ID),
                            p.get(Tables.ARTICLE.CONTENT),
                            p.get(Tables.ARTICLE.HEADER),
                            p.get(Tables.ARTICLE.SUMMARY),
                            p.get(Tables.ARTICLE.TIMESTAMP),
                            p.get(Tables.ARTICLE.ROWCREATETIME),
                            p.get(Tables.ARTICLE.CATEGORY),
                            p.get(Tables.ARTICLE.IMAGEURL),
                            p.get(Tables.ARTICLE.EMOTION)
                    )).collect(Collectors.toList());
        }
    }
}
