package fi.redgrenade.summarizer.schedulers;

import com.google.api.client.util.DateTime;
import fi.redgrenade.summarizer.db.tables.Article;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.Date;

/**
 * Created by aleksandr on 24.11.2017.
 */
public class ArticleSummarizer {

    @Scheduled(cron = "0 * * * * *")
    public void summarizeArticles () {


    }
}
