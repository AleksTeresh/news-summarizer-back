package fi.redgrenade.summarizer.schedulers;

import fi.redgrenade.summarizer.dao.ExArticleDao;
import fi.redgrenade.summarizer.db.tables.pojos.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by aleksandr on 24.11.2017.
 */
public class ArticleSummarizer {
    private final RestTemplate restTemplate;

    private final ExArticleDao articleDao;

    @Autowired
    public ArticleSummarizer (
            ExArticleDao articleDao
    ) {
        this.restTemplate = new RestTemplate();

        this.articleDao = articleDao;
    }

  //     @Scheduled(cron = "0 * * * * *")
    public void summarizeArticles () {
        Timestamp referenceTimestamp = new Timestamp(new Date().getTime() - TimeUnit.MINUTES.toMillis(10000)); // TODO: change the number in braces
        List<fi.redgrenade.summarizer.db.tables.pojos.Article> articles =
                articleDao.fetchwithCreateTimeGreaterThan(referenceTimestamp);

        articles.forEach(p -> {
            String articleSummary = restTemplate.postForObject(
                    "https://localhost/api/article",
                    p,
                    String.class
            );

            Article updatedArticle = p;
            updatedArticle.setSummary(articleSummary);

            articleDao.update(updatedArticle);
        });
    }
}
