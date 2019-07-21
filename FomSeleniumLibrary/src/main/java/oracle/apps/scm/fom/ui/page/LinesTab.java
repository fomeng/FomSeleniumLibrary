package oracle.apps.scm.fom.ui.page;

import oracle.apps.scm.fom.ui.BaseUIObject;
import oracle.apps.scm.fom.ui.Window;

public class LinesTab extends BaseUIObject {
    
    public enum Element {
		
    	Title("//h2[contains(text(),'Order Lines')]"),
		Dummy("Dummy");
		
		public final String xpath;
		
		Element(String xpath) {
			this.xpath = xpath;
		}
	}
	
	private Window mWindow;

	public LinesTab(Window window) {
		super(window.getDriver());
		mWindow = window;
	}
}
