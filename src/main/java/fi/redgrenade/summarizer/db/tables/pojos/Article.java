/*
 * This file is generated by jOOQ.
*/
package fi.redgrenade.summarizer.db.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


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
public class Article implements Serializable {

    private static final long serialVersionUID = -2028658257;

    private Long      id;
    private String    content;
    private String    header;
    private String    summary;
    private Timestamp timestamp;
    private Timestamp rowcreatetime;

    public Article() {}

    public Article(Article value) {
        this.id = value.id;
        this.content = value.content;
        this.header = value.header;
        this.summary = value.summary;
        this.timestamp = value.timestamp;
        this.rowcreatetime = value.rowcreatetime;
    }

    public Article(
        Long      id,
        String    content,
        String    header,
        String    summary,
        Timestamp timestamp,
        Timestamp rowcreatetime
    ) {
        this.id = id;
        this.content = content;
        this.header = header;
        this.summary = summary;
        this.timestamp = timestamp;
        this.rowcreatetime = rowcreatetime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Timestamp getRowcreatetime() {
        return this.rowcreatetime;
    }

    public void setRowcreatetime(Timestamp rowcreatetime) {
        this.rowcreatetime = rowcreatetime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Article (");

        sb.append(id);
        sb.append(", ").append(content);
        sb.append(", ").append(header);
        sb.append(", ").append(summary);
        sb.append(", ").append(timestamp);
        sb.append(", ").append(rowcreatetime);

        sb.append(")");
        return sb.toString();
    }
}
