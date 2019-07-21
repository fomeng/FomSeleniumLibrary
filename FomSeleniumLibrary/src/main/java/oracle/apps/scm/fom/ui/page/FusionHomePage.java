package oracle.apps.scm.fom.ui.page;

import oracle.apps.scm.fom.ui.BaseUIObject;
import oracle.apps.scm.fom.ui.Page;
import oracle.apps.scm.fom.ui.Window;

public class FusionHomePage extends BaseUIObject implements Page {

	private Window mWindow;

	public FusionHomePage(Window window) {
		super(window.getDriver());
		mWindow = window;
	}
	
	private DashboardPage navigateToOrderManagementUsingIcons() {
		clickByXPath("//a[@id='groupNode_order_management_8']");
		
		return new DashboardPage(mWindow);		
	}
	
	public DashboardPage navigateToOrderManagement() {
		clickByXPath("//a[@id='pt1:_UISmmLink']");
		
		wait(2000);
		
		//clickByXPath("//a[@id='_FOpt1:nv_itemNode_order_management_order_management']");
		
		clickByXPath("//a[@class='UnifiedNavigatorItem af_commandLink' and text()='Order Management']");
		
		wait(2000);
		
		return new DashboardPage(mWindow);
	}
}
