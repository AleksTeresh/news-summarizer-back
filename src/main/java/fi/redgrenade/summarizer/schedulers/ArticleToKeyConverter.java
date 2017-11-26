package fi.redgrenade.summarizer.schedulers;

import com.google.gson.Gson;
import fi.redgrenade.summarizer.dao.ExArticleDao;
import fi.redgrenade.summarizer.dao.ExArticleKeyWordDao;
import fi.redgrenade.summarizer.dao.ExKeyWordDao;
import fi.redgrenade.summarizer.db.tables.pojos.ArticleKeyWord;
import fi.redgrenade.summarizer.db.tables.pojos.KeyWord;
import fi.redgrenade.summarizer.schedulers.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by aleksandr on 24.11.2017.
 */

@Component
public class ArticleToKeyConverter {
    private ExKeyWordDao keyWordDao;
    private ExArticleKeyWordDao articleKeyWordDao;
    private ExArticleDao articleDao;
    private Gson gson;

    @Autowired
    public ArticleToKeyConverter(ExArticleDao articleDao, ExKeyWordDao keyWordDao, ExArticleKeyWordDao articleKeyWordDao) {
        this.keyWordDao = keyWordDao;
        this.articleDao = articleDao;
        this.articleKeyWordDao = articleKeyWordDao;
        gson = new Gson();
    }

    @Scheduled(cron = "0 * * * * *")
    public void fetchArticles() {
        File[] articleFiles = getFileList();

        Date timeStamp = new Date();

        for (File articleFile : articleFiles) {
            String filePath = articleFile.getPath();

            try {
                BasicFileAttributes view
                        = Files.getFileAttributeView(Paths.get(filePath), BasicFileAttributeView.class)
                        .readAttributes();

                if (view.creationTime().toInstant().compareTo(timeStamp.toInstant().minusSeconds(60)) > 0) {
                    Article article = parseArticleFile(filePath);

                    Long articleId = articleDao.count() + 1;


                    String keywordArrayJsonString = executePythonKeyWordScript(article.body);
                    String emotionsJsonString = execPythonEmoScript(article.body);

                    ArrayList<String> keywords = gson.fromJson(keywordArrayJsonString, ArrayList.class);
                    ArrayList<String> emotions = gson.fromJson(emotionsJsonString, ArrayList.class);

                    String[] emtionsArray = new String[emotions.size()];
                    articleDao.insert(new fi.redgrenade.summarizer.db.tables.pojos.Article(
                            articleId,
                            article.body,
                            article.title,
                            null,
                            new Timestamp(article.timestamp.getTime()),
                            null,
                            article.category,
                            article.imageurl,
                            String.join(", ", emotions.toArray(emtionsArray))
                    ));

                    for (String keyword : keywords) {
                        Long keyWordId = null;
                        List<KeyWord> foundKeyWords = keyWordDao.fetchByWord(keyword);

                        if( foundKeyWords.size() == 0) {
                            keyWordId = keyWordDao.count() + 1;

                            keyWordDao.insert(new KeyWord(
                                    keyWordId,
                                    keyword
                            ));
                        } else {
                            keyWordId = foundKeyWords.get(0).getId();
                        }

                        articleKeyWordDao.insert(new ArticleKeyWord(articleId, keyWordId));
                    }


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

    private String executePythonKeyWordScript(String articleBody) throws IOException {
        String s = null;
        String result = "";

        try {
            String[] command = new String[]{"python", "nlu.py", articleBody, "5"};

            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            // System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                result += s;
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

    private String execPythonEmoScript(String articleBody) throws IOException {
        String s = null;
        String result = "";

        try {
            String[] command = new String[]{"python", "emo.py", articleBody};

            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            // System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                result += s;
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
