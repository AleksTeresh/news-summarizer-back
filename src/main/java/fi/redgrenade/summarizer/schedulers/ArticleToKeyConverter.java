package fi.redgrenade.summarizer.schedulers;

import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by aleksandr on 24.11.2017.
 */

public class ArticleToKeyConverter {

    @Scheduled(cron = "0 * * * * *")
    public void fetchArticles () {

    }
}
