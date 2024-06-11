package gluecode;



import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import utils.WaitUtil;

public class Test {

    private Hooks hooks;
    private WaitUtil waitUtil;

    @Before
    public void setUp()
    {
        hooks = new Hooks();
        hooks.openBrowser();
        this.waitUtil = new WaitUtil(hooks.getPage());
    }

    @After
    public void tearDown(){
        hooks.closedBrowser();
    }

    @Then("Espera {int} segundos")
    public void esperarSegundos(int seconds){
        waitUtil.waitForSeconds(seconds);
    }

    @Then("^esto es then$")
    public void esto_es_then() throws Throwable {
    }

    @Given("esto es given")
    public void esto_es_given() throws Throwable {
           hooks.screenShoot();
    }

    @When("^esto es when$")
    public void esto_es_when() throws Throwable {
        System.out.println("prueba");
    }

    @When("Ingresa a la pagina")
    public void visitPage(){
        hooks.getPage().navigate("https://www.google.com/");
        System.out.println(hooks.getPage().title());
    }

}
