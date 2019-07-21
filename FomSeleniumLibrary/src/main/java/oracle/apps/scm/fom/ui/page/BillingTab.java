package oracle.apps.scm.fom.ui.page;

import oracle.apps.scm.fom.ui.BaseUIObject;
import oracle.apps.scm.fom.ui.Window;

public class BillingTab extends BaseUIObject {
    
	public enum Element {
		
		Title("//h1[contains(text(),'Billing and Payment Details')]"),
		Dummy("Dummy");
		
		public final String xpath;
		
		Element(String xpath) {
			this.xpath = xpath;
		}
	}
	
	private Window mWindow;

	public BillingTab(Window window) {
		super(window.getDriver());
		mWindow = window;
	}
}
