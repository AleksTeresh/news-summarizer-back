package fi.redgrenade.summarizer.schedulers;

import fi.redgrenade.summarizer.dao.ExArticleDao;
import fi.redgrenade.summarizer.db.tables.pojos.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    @Scheduled(cron = "0 * * * * *")
    public void summarizeArticles () {
        Timestamp referenceTimestamp = new Timestamp(new Date().getTime() - TimeUnit.MINUTES.toMillis(60000));
        System.out.printf(referenceTimestamp.toString());
        List<fi.redgrenade.summarizer.db.tables.pojos.Article> articles =
                articleDao.fetchwithCreateTimeGreaterThan(referenceTimestamp);
        System.out.println(articles.size());

        articles.forEach(p -> {
            String articleSummary;
            try {
                articleSummary = executePythonScript(p.getContent());
            } catch (Exception e) {
                articleSummary = "";
            }


            Article updatedArticle = p;
            updatedArticle.setSummary(articleSummary);

            articleDao.update(updatedArticle);
        });
    }

    private String executePythonScript(String articleBody) throws IOException {
        String s = null;
        String result = "";

        try {
            String[] command = new String[]{"python3", "../news-summarizer-ai/pointer-generator/make_abstract.py", articleBody};

            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            // System.out.println("Here is the standard output of the command:\n");
            System.out.println("Here is the standard output of the command:\n");
            boolean passedDelimiter = false;
            while ((s = stdInput.readLine()) != null) {

                if (passedDelimiter) {
                    result += s;
                }
                System.out.println(s + "   " + (Objects.equals(s, "DIMAAAAA")));
                if (Objects.equals(s, "DIMAAAAA")) {
                    passedDelimiter = true;
                }
            }
            // System.out.println(result);

            // read any errors from the attempted command
//            System.out.println("Here is the standard error of the command (if any):\n");
//            while ((s = stdError.readLine()) != null) {
//                System.out.println(s);
//            }
        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        }

        return result;
    }
}
