package runner;


import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)

@CucumberOptions(
        features="src/test/java/features",
        glue = {"gluecode"},
        publish = true,
        tags = "@Proba",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)


public class testrunner {
}
