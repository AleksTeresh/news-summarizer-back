package fi.redgrenade.summarizer.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by arsenii on 26/11/2017.
 */
@RestController
public class SummaryController {
    @RequestMapping(method = RequestMethod.GET, value = "/summary")
    public String getSummary(
            @RequestParam(value = "content", defaultValue = "", required = false) String content
    ) {
        // Use executeScript

        return content;
    }

    private String executeSctript(String content) {
        String s = null;
        String result = "";

        try {
            String[] command = new String[]{"python", "summary.py", content};

            Process p = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                result += s;
            }
            System.out.println(result);

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
