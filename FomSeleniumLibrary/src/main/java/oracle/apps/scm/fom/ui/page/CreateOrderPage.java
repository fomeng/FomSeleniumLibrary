package oracle.apps.scm.fom.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import oracle.apps.scm.fom.ui.BaseUIObject;
import oracle.apps.scm.fom.ui.Page;
import oracle.apps.scm.fom.ui.Window;

public class CreateOrderPage extends BaseUIObject implements Page {

	public enum Element {
		PageTitle("//td[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:SPph::_afrTtxt']/div/h1"),
		CustomerLOV("//input[@type='text'][@name='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:partyNameId']"),
		CustomerPONumber(""),
		ItemPickerInput("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:itemNumberId:lovTxtId::content']"),
		CreateLineButton(""),
		BusinessUnitList("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:soc3::content']"),
		AddLineButton("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:addLine']/a"),
		SaveButton("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:save']/table//td[1]/a"),
		SubmitButton("//div[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:submit']//a"),
		LinesTab("//div[@title='Lines']/a"),
		ShipmentDetailsTab("//div[@title='Shipment Details']/a"),
		BillingTab("//div[@title='Billing and Payment Details']/a"),
		SalesCreditsTab("//div[@title='Manage Sales Credits']/a"),
		Dummy("Dummy");
		
		public final String xpath;
		
		Element(String xpath) {
			this.xpath = xpath;
		}
	}
	
	private Window mWindow;

	public CreateOrderPage(Window window) {
		super(window.getDriver());
		mWindow = window;
	}
	
	public void ensureBusinessUnitIs(String buName) {
		WebElement bu = getByXPath(Element.BusinessUnitList.xpath);
		
		Select select = new Select(bu);
		WebElement option = select.getFirstSelectedOption();
		String defaultItem = option.getText();
		System.out.println("Selected option: " + defaultItem );
		
		if( !buName.equals(defaultItem)) {
			select.selectByVisibleText(buName);
			
			WebDriverWait wait = new WebDriverWait(mWindow.getDriver(), 20);
			By itemPickerRef = By.xpath(CreateOrderPage.Element.ItemPickerInput.xpath);

			// wait for item picker to be enabled
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(itemPickerRef));
			
			System.out.println(element);
			
		}
		
		wait(1000);
	}

	public void setCustomer(String customerName) {
		WebElement customer = getByXPath(Element.CustomerLOV.xpath);
		customer.sendKeys(customerName);
		
		clickByXPath(CreateOrderPage.Element.PageTitle.xpath);
		
		waitUntilTextIs(CreateOrderPage.Element.PageTitle.xpath, "Create Order: " + customerName);
	}
	
	public void setItemNumber(String itemNumber) {
		WebElement itemPicker = getByXPath(Element.ItemPickerInput.xpath);
		itemPicker.sendKeys(itemNumber);
		itemPicker.sendKeys(Keys.ENTER);	
		
		waitUntilAttributeEquals(Element.AddLineButton.xpath, "aria-disabled", "");
	}
	
	public void addLine() {
		
		WebElement table = getByXPath("//table[@summary='Order Lines']");		
		clickByXPath(Element.AddLineButton.xpath);
		
		// wait until table is repainted
		waitUntilElementIsStale(table);
	}
	
	public void saveOrder() {
		clickByXPath(Element.SaveButton.xpath);
		
		waitUntilTextContains(CreateOrderPage.Element.PageTitle.xpath, "Draft");
	}
	
	public void submitOrder() {
		clickByXPath(Element.SubmitButton.xpath);
	}
	
	public void cancel() {
		
	}
	
	public LinesTab navigateToLinesTab() {
    	clickByXPath(Element.LinesTab.xpath);
    	
    	waitUntilElementPresent(LinesTab.Element.Title.xpath);   
    	
    	return new LinesTab(mWindow);
    }
	
    public ShipmentDetailsTab navigateToShipmentDetailsTab() {
    	clickByXPath(Element.ShipmentDetailsTab.xpath);
    	
    	waitUntilElementPresent(ShipmentDetailsTab.Element.Title.xpath);
    	
    	return new ShipmentDetailsTab(mWindow);
    }
    
    public BillingTab navigateToBillingTab() {
    	clickByXPath(Element.BillingTab.xpath);
    	
    	waitUntilElementPresent(BillingTab.Element.Title.xpath);
    	
    	return new BillingTab(mWindow);
    }
    
    public SalesCreditsTab navigateToSalesCreditsTab() {
    	clickByXPath(Element.SalesCreditsTab.xpath);
    	
    	waitUntilElementPresent(SalesCreditsTab.Element.Title.xpath);    	
    	
    	return new SalesCreditsTab(mWindow);
    }
}
