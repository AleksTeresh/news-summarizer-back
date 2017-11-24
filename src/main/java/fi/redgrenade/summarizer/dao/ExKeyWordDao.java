package fi.redgrenade.summarizer.dao;

import fi.redgrenade.summarizer.db.tables.daos.KeyWordDao;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by aleksandr on 24.11.2017.
 */
@Repository
public class ExKeyWordDao extends KeyWordDao {

    private DSLContext dsl;

    @Autowired
    public ExKeyWordDao(DSLContext dsl) {
        super(dsl.configuration());

        this.dsl = dsl;
    }
}
