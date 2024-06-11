package utils;


import com.microsoft.playwright.Page;
import java.nio.file.Paths;

public class WaitUtil {
    private Page page;

    public WaitUtil(Page page) {
        this.page = page;
    }


    public void waitForSeconds(int seconds) {
        try {
            page.navigate(Paths.get("src/main/resources/waiting.html").toUri().toString());
            page.evaluate("window.seconds = " + seconds + "; startCountdown(window.seconds);");
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
