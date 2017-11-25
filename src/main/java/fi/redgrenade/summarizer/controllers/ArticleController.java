package fi.redgrenade.summarizer.controllers;

import fi.redgrenade.summarizer.dao.ExArticleDao;
import fi.redgrenade.summarizer.dao.ExArticleKeyWordDao;
import fi.redgrenade.summarizer.dao.ExKeyWordDao;
import fi.redgrenade.summarizer.db.tables.pojos.ArticleKeyWord;
import fi.redgrenade.summarizer.representations.responses.ArticleResponse;
import fi.redgrenade.summarizer.representations.views.ArticleView;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 24.11.2017.
 */
@RestController
public class ArticleController {
    private final ExArticleDao articleDao;
    private final ExArticleKeyWordDao articleKeyWordDao;
    private final ExKeyWordDao keyWordDao;

    public ArticleController (
            ExArticleDao articleDao,
            ExArticleKeyWordDao articleKeyWordDao,
            ExKeyWordDao keyWordDao
    ) {
        this.articleDao = articleDao;
        this.articleKeyWordDao = articleKeyWordDao;
        this.keyWordDao = keyWordDao;
    }

    @RequestMapping(method = RequestMethod.GET, value="/articles")
    public ArticleResponse getArticles(
            @RequestParam(value = "keyWords", defaultValue = "", required = false) List<String> keyWords,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "category", defaultValue = "", required = false) String category
    ) {
        long count = articleDao.count();
        List<ArticleView> articles = articleDao.fetch(keyWords, limit, offset, category)
                .stream()
                .map(p -> {
                    List<ArticleKeyWord> articleWordTuples = articleKeyWordDao.fetchByArticleId(p.getId());
                    Long[] articleWordTuplesArray = new Long[articleWordTuples.size()];
                    articleWordTuplesArray = articleWordTuples
                            .stream()
                            .map(s -> s.getKeyWordId())
                            .collect(Collectors.toList())
                            .toArray(articleWordTuplesArray);

                    return ArticleView.fromEntity(
                            p,
                            keyWordDao.fetchById(articleWordTuplesArray)
                                    .stream()
                                    .map(s -> s.getWord()).collect(Collectors.toList())
                    );
                }).collect(Collectors.toList());

        return new ArticleResponse(articles, count);
    }

    @RequestMapping(method = RequestMethod.GET, value = "articles/{id}")
    public ArticleView getAuthor(@PathVariable("id") Long id) {
        List<ArticleKeyWord> articleWordTuples = articleKeyWordDao.fetchByArticleId(id);
        Long[] articleWordTuplesArray = new Long[articleWordTuples.size()];
        articleWordTuplesArray = articleWordTuples
                .stream()
                .map(p -> p.getKeyWordId())
                .collect(Collectors.toList())
                .toArray(articleWordTuplesArray);

        return ArticleView.fromEntity(
                articleDao.fetchOneById(id),
                keyWordDao.fetchById(articleWordTuplesArray)
                    .stream()
                .map(p -> p.getWord()).collect(Collectors.toList())
        );
    }
}
