package oracle.apps.scm.fom.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import oracle.apps.scm.fom.ui.page.FusionHomePage;

public class Window extends BaseUIObject {
	
	private String mName;
	private int mIndex;
	
	private WebDriver mDriver;
	
	public Window(String name, int index, WebDriver driver) {
		super(driver);
		
		mName = name;
		mIndex = index;
		mDriver = driver;
	}
	
	
	public FusionHomePage login() {
		
		WebElement userId = mDriver.findElement(By.xpath("//input[@type='text'][@name='userid']"));		
		userId.sendKeys("<userName>");	// replace user name and password with actual values	
	
		WebElement pwd = mDriver.findElement(By.xpath("//input[@type='password'][@name='password']"));		
		pwd.sendKeys("<password>");
		
		WebElement signInButton = mDriver.findElement(By.xpath("//button[contains(text(),'Sign In')]"));		
		signInButton.click();
		
		wait(1000);
		
		clickByXPath("//a[@id='pt1:_UIShome']");
		
		return new FusionHomePage(this);
	}
	
	public void close() {
		if( isDefaultWindow() ) {
			// this is the only tab open. We need to close the driver
			// TODO: assumption here is that all other tabs are closed. Address this
			mDriver.close();
		}
		else {
			closeTab();
		}
	}
	
	private boolean isDefaultWindow() {
		return mIndex == 0;
	}
	
	private void closeTab() {
		
	}

}
