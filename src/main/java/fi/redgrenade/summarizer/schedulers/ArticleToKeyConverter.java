package fi.redgrenade.summarizer.schedulers;

import com.google.gson.Gson;
import fi.redgrenade.summarizer.dao.ExArticleKeyWordDao;
import fi.redgrenade.summarizer.dao.ExKeyWordDao;
import fi.redgrenade.summarizer.schedulers.models.Article;
import org.jooq.util.derby.sys.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by aleksandr on 24.11.2017.
 */

@Component
public class ArticleToKeyConverter {
    private ExKeyWordDao keyWordDao;
    private ExArticleKeyWordDao articleKeyWordDao;
    private Gson gson;

    @Autowired
    public ArticleToKeyConverter(ExKeyWordDao keyWordDao, ExArticleKeyWordDao articleKeyWordDao) {
        File[] articleFiles = getFileList();
        Date timeStamp = new Date();
        gson = new Gson();
    }

    @Scheduled(cron = "* * * * * *")
    public void fetchArticles() {
        File[] articleFiles = getFileList();

        Date timeStamp = new Date();

        for (File articleFile : articleFiles) {
            String filePath = articleFile.getPath();

            try {
                BasicFileAttributes view
                        = Files.getFileAttributeView(Paths.get(filePath), BasicFileAttributeView.class)
                        .readAttributes();

                if (view.creationTime().toInstant().compareTo(timeStamp.toInstant().minusSeconds(6000000)) > 0) {
                    Article article = parseArticleFile(filePath);

                    executePythonKeyWordScript(article.body, article.title);
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private File[] getFileList() {
        File folder = new File("articles");
        return folder.listFiles();
    }

    private Article parseArticleFile(String articleFilePath) throws FileNotFoundException {
        String fileContents = new Scanner(new File(articleFilePath)).useDelimiter("\\Z").next();
        Article article = gson.fromJson(fileContents, Article.class);

        return article;
    }

    private void executePythonKeyWordScript(String articleBody, String articleTitle) throws IOException {
        String s = null;

        try {
            // run the Unix "ps -ef" command
            // using the Runtime exec method:

            String[] command = new String[] {"python", "rake.py", articleBody, articleTitle, "5"};

            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        }
    }
}
