package gluecode;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Hooks {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private BrowserContext context;

    public void openBrowser() {
        try{
            if (playwright == null) {
                playwright = Playwright.create();
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList(new String[]{"--start-maximized"}))
                );
                context = browser.newContext();
                page = context.newPage();
            startVideoRecording();
            //screenShoot();
            System.out.println(page.title());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to initialize the browser: " + e.getMessage());
        }

    }


    public void closedBrowser() {
        if (context != null) {
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
        renameVideoFile();  //descomentar si se realizo video, para renombrar video
    }

    public Page getPage(){
        return page;
    }

    public void startVideoRecording() {
        try {
            if (context != null) {
                // Cerrar el contexto anterior si existe para iniciar uno nuevo con grabación
                context.close();
                context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("reports/PlaywrightVideos/")).setViewportSize(null));
                page = context.newPage();
                System.out.println("Video recording started.");
            }
        }  catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to start video recording: " + e.getMessage());
        }
    }

    public void screenShoot(){
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String screenshotPath = "reports/screenshots/screenshot_" + timestamp + ".png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to take screenshot: " + e.getMessage());
        }
    }
    private void renameVideoFile() {
        try {
            // Ruta del directorio de videos
            Path videoDir = Paths.get("reports/PlaywrightVideos/");
            // Obtener el primer archivo de video en el directorio
            File[] files = videoDir.toFile().listFiles((dir, name) -> name.endsWith(".webm"));
            if (files != null && files.length > 0) {
                File videoFile = files[0];
                // Crear un formato de fecha y hora
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                // Ruta de destino con la nueva extensión y nombre basado en la fecha y hora
                Path newVideoFile = Paths.get(videoFile.getParent(), "video_" + timestamp + ".mp4");
                // Renombrar el archivo
                Files.move(videoFile.toPath(), newVideoFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Video renamed to: " + newVideoFile.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
