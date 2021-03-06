/*
 * This file is generated by jOOQ.
*/
package fi.redgrenade.summarizer.db;


import javax.annotation.Generated;

import org.jooq.Sequence;
import org.jooq.impl.SequenceImpl;


/**
 * Convenience access to all sequences in public
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.article_id_seq</code>
     */
    public static final Sequence<Long> ARTICLE_ID_SEQ = new SequenceImpl<Long>("article_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.article_key_word_article_id_seq</code>
     */
    public static final Sequence<Long> ARTICLE_KEY_WORD_ARTICLE_ID_SEQ = new SequenceImpl<Long>("article_key_word_article_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.article_key_word_key_word_id_seq</code>
     */
    public static final Sequence<Long> ARTICLE_KEY_WORD_KEY_WORD_ID_SEQ = new SequenceImpl<Long>("article_key_word_key_word_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));

    /**
     * The sequence <code>public.key_word_id_seq</code>
     */
    public static final Sequence<Long> KEY_WORD_ID_SEQ = new SequenceImpl<Long>("key_word_id_seq", Public.PUBLIC, org.jooq.impl.SQLDataType.BIGINT.nullable(false));
}
