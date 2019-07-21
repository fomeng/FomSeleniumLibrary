package oracle.apps.scm.fom.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import oracle.apps.scm.fom.ui.BaseUIObject;
import oracle.apps.scm.fom.ui.Window;
import oracle.apps.scm.fom.ui.page.CreateOrderPage.Element;

public class ShipmentDetailsTab extends BaseUIObject {

	public enum Element {
		
		Title("//h1[contains(text(),'Shipment Details')]"),
		
		ShipToContactInput("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:shipToContactNameId::content']"),
		ShipToContactPointInput("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:shipToContactPointId::content']"),
		RequestTypeInput("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:soc1::content']"),
		RequestDateInput("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:id1::content']"),
		ShipMethodInput("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:shipMethodId::content']"),
		ShipLinesTogetherInput("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:soc15::content']"),
		
		LatestAcceptableDateInput("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:id2::content']"),
		EarliestAcceptableDateInput("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:id3::content']"),
		FOBInput("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:soc3::content']"),
		FreightTermsInput("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:soc4::content']"),
		AllowPartialShipmentInput("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:soc2::content']"),
		ShipmentPriorityInput("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:soc5::content']"),
		ShippingInstructionsInput("//textarea[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:it2::content']"),
		PackingInstructionsInput("//textarea[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:it3::content']"),
		
		WarehouseInput("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:warehouseNameId::content']"),
		SupplierInput("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:supplierNameId::content']"),
		SupplierSiteInput("//input[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:supplierSiteNameId::content']"),
		DemandClassInput("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:soc6::content']"),
		AllowItemSubstitutionInput("//select[@id='_FOpt1:_FOr1:0:_FOSritemNode_order_management_order_management:0:_FOTsr1:1:AP1:r5:0:soc7::content']"),
		Dummy("Dummy");
		
		public final String xpath;
		
		Element(String xpath) {
			this.xpath = xpath;
		}
	}
	
	private Window mWindow;

	public ShipmentDetailsTab(Window window) {
		super(window.getDriver());
		mWindow = window;
	}
	
	public void switchToGeneralTab() {
		clickByLinkText("General");
		
		waitUntilElementPresent(Element.ShipToContactInput.xpath);
	}
	
    public void switchToShippingTab() {
    	clickByLinkText("Shipping");
    	
    	waitUntilElementPresent(Element.LatestAcceptableDateInput.xpath);
	}
	
	public void switchToSupplyTab() {
		clickByLinkText("Supply");
		
		waitUntilElementPresent(Element.WarehouseInput.xpath);
	}
	
	public void setWarehouse(String warehouse) {
		WebElement element = getByXPath(Element.WarehouseInput.xpath);
		element.sendKeys(warehouse);
		
		// this is just to click outside the LOV input
		clickByXPath(Element.Title.xpath);
		
		// wait until supply input becomes disabled
		waitUntilNotClickable(Element.SupplierInput.xpath);
	}
	
	public void setSupplier(String supplier) {
		WebElement element = getByXPath(Element.SupplierInput.xpath);
		element.sendKeys(supplier);
		
		// this is just to click outside the LOV input
		clickByXPath(Element.Title.xpath);
		
		// wait until supply input becomes disabled
		waitUntilNotClickable(Element.WarehouseInput.xpath);
	}
	
	public void setSupplierSite(String supplierSite) {
		WebElement element = getByXPath(Element.SupplierSiteInput.xpath);
		element.sendKeys(supplierSite);
		
		// this is just to click outside the LOV input
		clickByXPath(Element.Title.xpath);
		
		// wait until supplier input is repainted
		waitUntilElementIsStale(element);
	}
	
	public void setDemandClass(String demandClass) {
		selectDrodownOptionByVisibleText(Element.DemandClassInput.xpath, demandClass);
	}
	
	public void setAllowItemSubstitution(String allowItemSubstitution) {	
		selectDrodownOptionByVisibleText(Element.AllowItemSubstitutionInput.xpath, allowItemSubstitution);
	}
	
	// Shipping tab fields
	
	public void setLatestAcceptableDate(String date) {
		setTextInput(Element.LatestAcceptableDateInput.xpath, date);
	}
	
	public void setEarliestAcceptableDate(String date) {
		setTextInput(Element.EarliestAcceptableDateInput.xpath, date);
	}
	
	public void setShippingInstructions(String text) {
		setTextInput(Element.ShippingInstructionsInput.xpath, text);
	}
	
	public void setPackingInstructions(String text) {
		setTextInput(Element.PackingInstructionsInput.xpath, text);
	}
	
	public void setFOB(String fob) {	
		selectDrodownOptionByVisibleText(Element.FOBInput.xpath, fob);
	}
	
	public void setFreightTerms(String freightTerms) {	
		selectDrodownOptionByVisibleText(Element.FreightTermsInput.xpath, freightTerms);
	}
	
	public void setAllowPartialShipment(String allowPartialShipment) {	
		selectDrodownOptionByVisibleText(Element.AllowPartialShipmentInput.xpath, allowPartialShipment);
	}
	
	public void setShipmentPriority(String shipmentPriority) {	
		selectDrodownOptionByVisibleText(Element.ShipmentPriorityInput.xpath, shipmentPriority);
	}
	
	
	// General tab fields
	public void setShipToContact(String shipToContactName) {
		WebElement element = getByXPath(Element.ShipToContactInput.xpath);
		WebElement contactPoint = getByXPath(Element.ShipToContactPointInput.xpath);
		
		replaceText(element, shipToContactName);

		// this is just to click outside the LOV input
		clickByXPath(Element.Title.xpath);
		
		// wait until supplier input is repainted
		waitUntilElementIsStale(contactPoint);
	}
	
	public void setShipToContactPoint(String shipToContactPoint) {
		WebElement contactPoint = getByXPath(Element.ShipToContactInput.xpath);
		contactPoint.sendKeys(shipToContactPoint);		
		
		// this is just to click outside the LOV input
		clickByXPath(Element.Title.xpath);
		
		// wait until supplier input is repainted
		waitUntilElementIsStale(contactPoint);
	}
	
	public void setRequestType(String type) {
		selectDrodownOptionByVisibleText(Element.RequestTypeInput.xpath, type);
	}
	
	public void setRequestDate(String date) {
		setTextInput(Element.RequestDateInput.xpath, date);
	}
	
	public void setShipMethod(String shipMethod) {
		WebElement shipMethodLOV = getByXPath(Element.ShipMethodInput.xpath);
		replaceText(shipMethodLOV, shipMethod);		
		
		// this is just to click outside the LOV input
		clickByXPath(Element.Title.xpath);

		wait(2000);
	}
	
	public void setShipLinesTogether(String shipLinesTogether) {
		selectDrodownOptionByVisibleText(Element.ShipLinesTogetherInput.xpath, shipLinesTogether);
	}
	
	protected void replaceText(WebElement element, String text) {
		// select existing text
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		
		element.sendKeys(text);
	}
}
