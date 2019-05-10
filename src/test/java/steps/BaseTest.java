package steps;

import com.ea.Base.BaseUtil;
import com.ea.utils.EventListener;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import ru.stqa.selenium.factory.WebDriverPool;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Roman on 31/01/2019.
 */

public class BaseTest extends BaseUtil{


    private BaseUtil base;
    private WebDriver webDriver;


    public BaseTest(BaseUtil base) {
        this.base = base;
    }

    @Before
    public void InitializeTest(Scenario scenario) throws MalformedURLException {


        scenarioDef = base.features.createNode(scenario.getName());
        webDriver = WebDriverPool.DEFAULT.getDriver(new URL("http://localhost:4444/wd/hub"),"chrome");
        setupEventListener();

        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }


    @After
    public void TearDownTest(Scenario scenario) {
        if (scenario.isFailed()) {
            //Take screenshot logic goes here
            System.out.println(scenario.getName());
        }
        System.out.println("Closing the browser : MOCK");
        //WebDriverPool.DEFAULT.dismissAll();
    }

        private void setupEventListener(){
            base.Driver = new EventFiringWebDriver(webDriver);
            EventListener eventListener = new EventListener();
            base.Driver.register(eventListener);
            base.Driver.manage().window().maximize();
        }

}
