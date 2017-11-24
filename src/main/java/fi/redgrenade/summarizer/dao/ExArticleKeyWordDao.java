package fi.redgrenade.summarizer.dao;

import fi.redgrenade.summarizer.db.tables.daos.ArticleKeyWordDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
