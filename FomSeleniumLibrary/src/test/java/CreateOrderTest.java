import org.openqa.selenium.WebDriver;

import oracle.apps.scm.fom.BaseTest;
import oracle.apps.scm.fom.ui.Window;
import oracle.apps.scm.fom.ui.page.CreateOrderPage;
import oracle.apps.scm.fom.ui.page.DashboardPage;
import oracle.apps.scm.fom.ui.page.FusionHomePage;
import oracle.apps.scm.fom.ui.page.ShipmentDetailsTab;

public class CreateOrderTest extends BaseTest {

	private static final String BASE_URL = "https://fuscdrmsmc203-fa-ext.us.oracle.com";

	WebDriver mDriver;

	public CreateOrderTest() {
		super(BASE_URL);
	}

	private void run() throws InterruptedException {

		Window window = newWindow("Default");

		FusionHomePage homePage = window.login();			
		DashboardPage dashboard = homePage.navigateToOrderManagement();		
		
		CreateOrderPage cop = dashboard.createOrder();
		
		cop.ensureBusinessUnitIs("Vision Operations");
		cop.setCustomer("Computer Service and Rentals");
		
		// add lines
		cop.setItemNumber("AS54888");
		cop.addLine();
		cop.setItemNumber("AS54555");
		cop.addLine();
		
		ShipmentDetailsTab tab = cop.navigateToShipmentDetailsTab();
		tab.switchToShippingTab();
		tab.setLatestAcceptableDate("7/25/19 9:15 PM");
		tab.setEarliestAcceptableDate("7/17/19 9:15 PM");
		tab.setFOB("Destination");
		tab.setAllowPartialShipment("Yes");
		tab.setFreightTerms("Prepaid freight");
		tab.setShipmentPriority("High");
		tab.setShippingInstructions("Ship by air only");
		tab.setPackingInstructions("Wrap in bubblewrap");		
		
		tab.switchToSupplyTab();
		
		//tab.setWarehouse("V1 - Vision Operations");
		
		tab.setSupplier("PERBAR CONSTRUCTION INC");		
		tab.setDemandClass("NORT US");
		tab.setAllowItemSubstitution("Yes");
		
		tab.switchToGeneralTab();
		tab.setShipToContact("Arnold Smithfield");		
		tab.setShipMethod("Airborne Parcel Express");
		
		cop.navigateToBillingTab();
		
		cop.navigateToSalesCreditsTab();
		
		cop.navigateToLinesTab();
		
		cop.saveOrder();
		cop.submitOrder();
		
		Thread.sleep(10000);

		window.close();
	}

	public static void main(String[] args) throws InterruptedException {
		CreateOrderTest test = new CreateOrderTest();
		test.run();
	}

}
