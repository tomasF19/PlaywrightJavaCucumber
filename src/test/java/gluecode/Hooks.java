package gluecode;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    @Before
    public void openBrowser(){
        if (playwright == null) {
            playwright = Playwright.create();
            browser = playwright.chromium().launch();
            page = browser.newPage();
            page.navigate("http://playwright.dev");
            System.out.println(page.title());
        }
    }

    @After
    public void closedBrowser() {
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
}
