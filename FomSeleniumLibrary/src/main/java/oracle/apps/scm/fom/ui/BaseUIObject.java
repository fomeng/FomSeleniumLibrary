package oracle.apps.scm.fom.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import oracle.apps.scm.fom.ui.page.CreateOrderPage;
import oracle.apps.scm.fom.ui.page.ShipmentDetailsTab.Element;

public class BaseUIObject {    
	
	private static final long WAIT_TIMEOUT = 60;
	private WebDriver mDriver;

	public BaseUIObject(WebDriver driver) {
		mDriver = driver;
	}
	
	protected void clickByXPath(String xpath) {
		WebElement element = mDriver.findElement(By.xpath(xpath));
		element.click();
		
		waitForLoad();
	}
	
	protected void clickByLinkText(String text) {
		WebElement link = mDriver.findElement(By.linkText(text));
		link.click();		
	}
	
	protected WebElement getByXPath(String xpath) {
		return mDriver.findElement(By.xpath(xpath));
	}
	
	public WebDriver getDriver() {
		return mDriver;
	}
	
	protected void wait(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void waitForLoad() {
        ExpectedCondition<Boolean> pageLoadCondition = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };
                
        WebDriverWait wait = new WebDriverWait(mDriver, WAIT_TIMEOUT);
        wait.until(pageLoadCondition);
    }
	
	protected void waitForLoad(int minWaitInMillis) {
		wait(minWaitInMillis);
		waitForLoad();
    }
	
	protected Boolean waitUntilNull(String xpath, final String attribute) {
		WebDriverWait wait = new WebDriverWait(mDriver, WAIT_TIMEOUT);
		
		final By locator = By.xpath(xpath);
		//final WebDriver driver = mWindow.getDriver();
		
		ExpectedCondition<Boolean> nullTest = new ExpectedCondition<Boolean>() {
		      
		      public Boolean apply(WebDriver driver) {
		        WebElement element = driver.findElement(locator);
		        String currentValue = element.getAttribute(attribute);
		        if (currentValue == null||currentValue.isEmpty()) {
		          currentValue = element.getCssValue(attribute);
		        }
		        
		        return currentValue == null;
		      }
		};

		// wait for item picker to be enabled
		return wait.until(nullTest);
	}
	
	protected WebElement waitUntilClickable(String xpath) {
		WebDriverWait wait = new WebDriverWait(mDriver, WAIT_TIMEOUT);
		By elementRef = By.xpath(xpath);

		// wait for item picker to be enabled
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementRef));
		
		return element;
	}
	
	protected boolean waitUntilNotClickable(String xpath) {
		WebDriverWait wait = new WebDriverWait(mDriver, WAIT_TIMEOUT);
		By elementRef = By.xpath(xpath);

		// wait for item picker to be enabled
		return wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(elementRef)));
	}
	
	protected Boolean waitUntilAttributeEquals(String xpath, final String attribute, String value) {
		WebDriverWait wait = new WebDriverWait(mDriver, WAIT_TIMEOUT);
		By locator = By.xpath(xpath);
		return wait.until(ExpectedConditions.attributeToBe(locator, attribute, value));
	}
	
	protected boolean waitUntilTextIs(String xpath, String text) {
		WebDriverWait wait = new WebDriverWait(mDriver, WAIT_TIMEOUT);
		By locator = By.xpath(xpath);
		
		return wait.until(ExpectedConditions.textToBe(locator, text));		
	}
	
	protected boolean waitUntilTextContains(String xpath, String text) {
		WebDriverWait wait = new WebDriverWait(mDriver, WAIT_TIMEOUT);
		By locator = By.xpath(xpath);
		
		return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));		
	}
	
	
	protected WebElement waitUntilElementPresent(String xpath) {
		WebDriverWait wait = new WebDriverWait(mDriver, WAIT_TIMEOUT);
		By locator = By.xpath(xpath);
	
		// get the "Add Item" element
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	protected void waitUntilElementIsStale(WebElement element) {
		
		WebDriverWait wait = new WebDriverWait(mDriver, WAIT_TIMEOUT);		
		wait.until(ExpectedConditions.stalenessOf(element));	
	}
	
	protected void selectDrodownOptionByVisibleText(String ddXPath, String text) {
        WebElement dd = getByXPath(ddXPath);		
		
		Select select = new Select(dd);
		select.selectByVisibleText(text);	
		
		// TODO: Find a better way to wait
		wait(1500);
	}	
	
	protected void setTextInput(String xpath, String text) {
        WebElement element = getByXPath(xpath);
		
		element.sendKeys(text);
		element.sendKeys(Keys.TAB);	
		
		wait(1500);
	}
}
