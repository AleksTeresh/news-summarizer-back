package fi.redgrenade.summarizer.dao;

import fi.redgrenade.summarizer.db.Tables;
import fi.redgrenade.summarizer.db.tables.daos.ArticleDao;
import fi.redgrenade.summarizer.db.tables.pojos.Article;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

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

    public List<Article> fetchwithCreateTimeGreaterThan (Timestamp timestamp) {
        return dsl.select()
                .from(Tables.ARTICLE)
                .where(Tables.ARTICLE.ROWCREATETIME.gt(timestamp))
                .fetch(p -> new Article(
                    p.get(Tables.ARTICLE.ID),
                    p.get(Tables.ARTICLE.CONTENT),
                    p.get(Tables.ARTICLE.HEADER),
                    p.get(Tables.ARTICLE.SUMMARY),
                    p.get(Tables.ARTICLE.TIMESTAMP),
                    p.get(Tables.ARTICLE.ROWCREATETIME)
                ));
    }
}
