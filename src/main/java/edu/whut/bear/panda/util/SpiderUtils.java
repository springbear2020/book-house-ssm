package edu.whut.bear.panda.util;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author Spring-_-Bear
 * @datetime 2022/5/3 21:58
 */
@Component
public class SpiderUtils {
    /**
     * Get the disk real path of the pixabay spider file named pixabay_spider.py
     *
     * @return Pixabay spider file real path or null
     */
    public String getPixabaySpiderRealPath() {
        String fileRealPath;
        Resource resource = new ClassPathResource("pixabay_spider.py");
        try {
            fileRealPath = resource.getURL().toString();
            return fileRealPath.substring(fileRealPath.indexOf('/') + 1);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Execute the terminal command like [python pixabay_spider.py params]
     *
     * @param fileFullPath The full path of the python
     * @param params       The parameters of the command
     * @return true - Command execute successfully
     */
    public boolean executeSpider(String fileFullPath, String params) {
        // Combining the parameters into a command
        String command = "python " + fileFullPath + " " + params;
        BufferedReader bufferedReader = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(command);

            // Print the execute result info by the terminal
            String output;
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((output = bufferedReader.readLine()) != null) {
                System.out.println(output);
            }
            // Check the command execute result(success or fail)
            if (process.waitFor() == 0 && process.exitValue() == 0) {
                // Command execute successfully
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
