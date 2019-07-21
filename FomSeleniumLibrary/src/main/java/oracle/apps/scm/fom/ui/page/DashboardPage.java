package oracle.apps.scm.fom.ui.page;

import oracle.apps.scm.fom.ui.BaseUIObject;
import oracle.apps.scm.fom.ui.Page;
import oracle.apps.scm.fom.ui.Window;

public class DashboardPage extends BaseUIObject implements Page  {
	
    public enum Element {
		
		CreateOrderButton("//table[@class='af_toolbar_items']/tbody/tr/td/div[contains(@id, 'createbtn')]");
		
		public final String xpath;
		
		Element(String xpath) {
			this.xpath = xpath;
		}
	}

	private Window mWindow;

	public DashboardPage(Window window) {
		super(window.getDriver());
		mWindow = window;
	}
	
	public CreateOrderPage createOrder() {
		
		clickByXPath(Element.CreateOrderButton.xpath);		
	
		waitUntilElementPresent(CreateOrderPage.Element.BusinessUnitList.xpath);
		
		return new CreateOrderPage(mWindow);
	}
}
