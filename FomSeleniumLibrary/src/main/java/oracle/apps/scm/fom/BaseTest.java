package oracle.apps.scm.fom;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import oracle.apps.scm.fom.ui.Window;

public class BaseTest {
	
	private int mWindowCount = 0;
	private static final String REL_HOME_PAGE_URL = "/homePage/faces/AtkHomePageWelcome";
	
    private WebDriver mDriver;
	
    private Map<String, Window> mWindows = new HashMap<String, Window>();

	private String mBaseURL;
	
    public BaseTest(String baseURL) {
    	mBaseURL = baseURL;
    	mDriver = getDriver();
    }
    
	
	public Window newWindow(String name) {
		Window window = new Window(name, mWindowCount++, mDriver);
		mDriver.get(mBaseURL + REL_HOME_PAGE_URL);
		
		mWindows.put(name, window);
		
		mDriver.manage().window().maximize();
		
		return window;
	}
	
	private WebDriver getDriver() {		
		//System.setProperty("webdriver.gecko.driver", "drivers/geckodriver/geckodriver.exe");
		//WebDriver driver = new FirefoxDriver();
		
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		return driver;
	}
	
	protected void wait(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

}
