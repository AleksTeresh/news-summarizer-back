package fi.redgrenade.summarizer.controllers;

import fi.redgrenade.summarizer.dao.ExArticleKeyWordDao;
import fi.redgrenade.summarizer.dao.ExKeyWordDao;
import fi.redgrenade.summarizer.representations.views.KeyWordView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by aleksandr on 24.11.2017.
 */
@RestController
public class KeyWordController {
    // private final ExArticleDao articleDao;
    private final ExKeyWordDao keyWordDao;
    private final ExArticleKeyWordDao articleKeyWordDao;

    public KeyWordController (
            // ExArticleDao articleDao,
            ExArticleKeyWordDao articleKeyWordDao,
            ExKeyWordDao keyWordDao
    ) {
        // this.articleDao = articleDao;
        this.articleKeyWordDao = articleKeyWordDao;
        this.keyWordDao = keyWordDao;
    }

    @RequestMapping(method = RequestMethod.GET, value="/keywords")
    public List<KeyWordView> getKeyWords(
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset
    ) {
        return articleKeyWordDao.fetchKeyWords(limit, offset);
    }
}
