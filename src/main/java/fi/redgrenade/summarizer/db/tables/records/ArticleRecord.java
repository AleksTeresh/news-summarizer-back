/*
 * This file is generated by jOOQ.
*/
package fi.redgrenade.summarizer.db.tables.records;


import fi.redgrenade.summarizer.db.tables.Article;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ArticleRecord extends UpdatableRecordImpl<ArticleRecord> implements Record5<Long, String, String, String, Timestamp> {

    private static final long serialVersionUID = 771401226;

    /**
     * Setter for <code>public.article.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.article.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.article.content</code>.
     */
    public void setContent(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.article.content</code>.
     */
    public String getContent() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.article.header</code>.
     */
    public void setHeader(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.article.header</code>.
     */
    public String getHeader() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.article.summary</code>.
     */
    public void setSummary(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.article.summary</code>.
     */
    public String getSummary() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.article.timestamp</code>.
     */
    public void setTimestamp(Timestamp value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.article.timestamp</code>.
     */
    public Timestamp getTimestamp() {
        return (Timestamp) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, String, String, Timestamp> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row5<Long, String, String, String, Timestamp> valuesRow() {
        return (Row5) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return Article.ARTICLE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Article.ARTICLE.CONTENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Article.ARTICLE.HEADER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Article.ARTICLE.SUMMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field5() {
        return Article.ARTICLE.TIMESTAMP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getHeader();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getSummary();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value5() {
        return getTimestamp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value2(String value) {
        setContent(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value3(String value) {
        setHeader(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value4(String value) {
        setSummary(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord value5(Timestamp value) {
        setTimestamp(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleRecord values(Long value1, String value2, String value3, String value4, Timestamp value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ArticleRecord
     */
    public ArticleRecord() {
        super(Article.ARTICLE);
    }

    /**
     * Create a detached, initialised ArticleRecord
     */
    public ArticleRecord(Long id, String content, String header, String summary, Timestamp timestamp) {
        super(Article.ARTICLE);

        set(0, id);
        set(1, content);
        set(2, header);
        set(3, summary);
        set(4, timestamp);
    }
}