package fi.redgrenade.summarizer.schedulers;

import fi.redgrenade.summarizer.dao.ExArticleKeyWordDao;
import fi.redgrenade.summarizer.dao.ExKeyWordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
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

    @Autowired
    public ArticleToKeyConverter(ExKeyWordDao keyWordDao, ExArticleKeyWordDao articleKeyWordDao) {
        File[] articleFiles = getFileList();
        Date timeStamp = new Date();
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
                    String fileContents = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
                    System.out.println(fileContents);
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
}
