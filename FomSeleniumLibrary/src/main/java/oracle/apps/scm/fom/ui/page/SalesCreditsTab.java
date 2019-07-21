package oracle.apps.scm.fom.ui.page;

import oracle.apps.scm.fom.ui.BaseUIObject;
import oracle.apps.scm.fom.ui.Window;

public class SalesCreditsTab extends BaseUIObject {
    
	public enum Element {
		
		Title("//h1[contains(text(),'Sales Credits')]"),
		Dummy("Dummy");
		
		public final String xpath;
		
		Element(String xpath) {
			this.xpath = xpath;
		}
	}
	
	private Window mWindow;

	public SalesCreditsTab(Window window) {
		super(window.getDriver());
		mWindow = window;
	}
}
