import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;

 

public class SSLTest {
	
	private final static String WSS_SECURITY_XSD_STR = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
	private final static String WSS_SECURITY_UTIL_XSD_STR = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
	private final static String WS_USERNAME_TOKEN = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
	    
	private static class TrustAllHosts implements HostnameVerifier {
	    public boolean verify(String hostname, SSLSession session) {
	        return true;
	    }
	}
	
	public SOAPMessage sendSoapRequest(String endpointUrl, SOAPMessage request) {
	    try {
	        final boolean isHttps = endpointUrl.toLowerCase().startsWith("https");
	        HttpsURLConnection httpsConnection = null;
	        // Open HTTPS connection
	        if (isHttps) {
	            // Create SSL context and trust all certificates
	            SSLContext sslContext = SSLContext.getInstance("SSL");
	            TrustManager[] trustAll
	                    = new TrustManager[] {new TrustAllCertificates()};
	            sslContext.init(null, trustAll, new java.security.SecureRandom());
	            // Set trust all certificates context to HttpsURLConnection
	            HttpsURLConnection
	                    .setDefaultSSLSocketFactory(sslContext.getSocketFactory());
	            // Open HTTPS connection
	            URL url = new URL(endpointUrl);
	            httpsConnection = (HttpsURLConnection) url.openConnection();
	            // Trust all hosts
	            httpsConnection.setHostnameVerifier(new TrustAllHosts());
	            // Connect
	            httpsConnection.connect();
	        }
	        // Send HTTP SOAP request and get response
	        SOAPConnection soapConnection
	                = SOAPConnectionFactory.newInstance().createConnection();
	        SOAPMessage response = soapConnection.call(request, endpointUrl);
	        // Close connection
	        soapConnection.close();
	        // Close HTTPS connection
	        if (isHttps) {
	            httpsConnection.disconnect();
	        }
	        return response;
	    } catch (SOAPException se) {
	    	
	    }
	    catch (IOException ioe) {
	    	
	    }
	    catch(NoSuchAlgorithmException nsa) {
	    }
	    catch(KeyManagementException ex) {
	    }

	    return null;
	}
	
	private static class TrustAllCertificates implements X509TrustManager {
	    public void checkClientTrusted(X509Certificate[] certs, String authType) {
	    }
	 
	    public void checkServerTrusted(X509Certificate[] certs, String authType) {
	    }
	 
	    public X509Certificate[] getAcceptedIssuers() {
	        return null;
	    }
	}
	
	public static void main(String args[]) {
		
		String soapEndpointUrl = "https://fuscdrmsmc203-fa-ext.us.oracle.com:443/fscmService/OrderImportService";
        String soapAction = null;
        
        System.out.println(System.getProperties());

        callOrderImportService(soapEndpointUrl, soapAction);
    }

    private static void createOISoapEnvelope(SOAPMessage soapMessage, String xmlStr) throws SOAPException, ParserConfigurationException, SAXException, IOException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();        
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
        soapBody.addDocument(doc);
    }
    
    
    private static void callOrderImportService(String soapEndpointUrl, String soapAction) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createOrderImportSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }
    
    private static SOAPMessage createOrderImportSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        
        String xmlStr = "<ns1:createOrders xmlns:ns1=\"http://xmlns.oracle.com/apps/scm/fom/importOrders/orderImportService/types/\">\r\n" + 
        		"         <ns1:request xmlns:ns2=\"http://xmlns.oracle.com/apps/scm/fom/importOrders/orderImportService/\">\r\n" + 
        		"            <ns2:BatchName/>\r\n" + 
        		"            <ns2:Order>\r\n" + 
        		"               <ns2:SourceTransactionIdentifier>ASP_0329_004</ns2:SourceTransactionIdentifier>\r\n" + 
        		"               <ns2:SourceTransactionSystem>LEG</ns2:SourceTransactionSystem>\r\n" + 
        		"               <ns2:SourceTransactionNumber>ASP_0329_004</ns2:SourceTransactionNumber>\r\n" + 
        		"               <ns2:BuyingPartyId>1006</ns2:BuyingPartyId>\r\n" + 
        		"               <ns2:BuyingPartyContactId>1560</ns2:BuyingPartyContactId>\r\n" + 
        		"               <ns2:TransactionalCurrencyCode>USD</ns2:TransactionalCurrencyCode>\r\n" + 
        		"               <ns2:TransactionOn>2016-12-10T06:08:52.0340</ns2:TransactionOn>\r\n" + 
        		"               <ns2:RequestingBusinessUnitIdentifier>204</ns2:RequestingBusinessUnitIdentifier>\r\n" + 
        		"               <ns2:RequestingLegalUnitIdentifier>204</ns2:RequestingLegalUnitIdentifier>\r\n" + 
        		"               <ns2:FreezePriceFlag>true</ns2:FreezePriceFlag>\r\n" + 
        		"               <ns2:FreezeShippingChargeFlag>true</ns2:FreezeShippingChargeFlag>\r\n" + 
        		"               <ns2:FreezeTaxFlag>true</ns2:FreezeTaxFlag>\r\n" + 
        		"               <ns2:Line>\r\n" + 
        		"                  <ns2:SourceTransactionLineIdentifier>101</ns2:SourceTransactionLineIdentifier>\r\n" + 
        		"                  <ns2:SourceTransactionScheduleIdentifier>101</ns2:SourceTransactionScheduleIdentifier>\r\n" + 
        		"                  <ns2:SourceTransactionLineNumber>1</ns2:SourceTransactionLineNumber>\r\n" + 
        		"                  <ns2:SourceTransactionScheduleNumber>1</ns2:SourceTransactionScheduleNumber>\r\n" + 
        		"                  <ns2:ProductNumber>AS54888</ns2:ProductNumber>\r\n" + 
        		"                  <ns2:OrderedQuantity unitCode=\"Ea\">1</ns2:OrderedQuantity>\r\n" + 
        		"                  <ns2:OrderedUOMCode>Ea</ns2:OrderedUOMCode>\r\n" + 
        		"                  <ns2:RequestedFulfillmentOrganizationIdentifier>207</ns2:RequestedFulfillmentOrganizationIdentifier>\r\n" + 
        		"                  <ns2:RequestingBusinessUnitIdentifier>204</ns2:RequestingBusinessUnitIdentifier>\r\n" + 
        		"                  <ns2:RequestedShipDate>2016-12-10T06:08:52.0340</ns2:RequestedShipDate>\r\n" + 
        		"                  <ns2:PaymentTermsCode>1</ns2:PaymentTermsCode>\r\n" + 
        		"                  <ns2:TransactionCategoryCode>ORDER</ns2:TransactionCategoryCode>\r\n" + 
        		"                  <ns2:InventoryOrganizationIdentifier>204</ns2:InventoryOrganizationIdentifier>\r\n" + 
        		"                  <ns2:UnitListPrice currencyCode=\"USD\">105.5</ns2:UnitListPrice>\r\n" + 
        		"                  <ns2:ShipToPartyIdentifier>1006</ns2:ShipToPartyIdentifier>\r\n" + 
        		"                  <ns2:ShipToPartySiteIdentifier>1036</ns2:ShipToPartySiteIdentifier>\r\n" + 
        		"                  <ns2:ShipToPartyContactIdentifier>1560</ns2:ShipToPartyContactIdentifier>\r\n" + 
        		"                  <ns2:BillToCustomerIdentifier>1006</ns2:BillToCustomerIdentifier>\r\n" + 
        		"                  <ns2:BillToCustomerName>1006</ns2:BillToCustomerName>\r\n" + 
        		"                  <ns2:BillToAccountSiteUseIdentifier>1025</ns2:BillToAccountSiteUseIdentifier>\r\n" + 
        		"                  <ns2:BillToAccountContactIdentifier>4820</ns2:BillToAccountContactIdentifier>\r\n" + 
        		"                  <ns2:PartialShipAllowedFlag>FALSE</ns2:PartialShipAllowedFlag>\r\n" + 
        		"                  <ns2:OrderCharge>\r\n" + 
        		"                     <ns2:ChargeDefinitionCode>QP_SALE_PRICE</ns2:ChargeDefinitionCode>\r\n" + 
        		"                     <ns2:ChargeSubtypeCode>ORA_PRICE</ns2:ChargeSubtypeCode>\r\n" + 
        		"                     <ns2:PriceTypeCode>ONE_TIME</ns2:PriceTypeCode>\r\n" + 
        		"                     <ns2:PricedQuantity>1</ns2:PricedQuantity>\r\n" + 
        		"                     <ns2:PricedQuantityUOMCode>Ea</ns2:PricedQuantityUOMCode>\r\n" + 
        		"                     <ns2:PrimaryFlag>true</ns2:PrimaryFlag>\r\n" + 
        		"                     <ns2:ApplyTo>PRICE</ns2:ApplyTo>\r\n" + 
        		"                     <ns2:RollupFlag>false</ns2:RollupFlag>\r\n" + 
        		"                     <ns2:SourceChargeIdentifier>SC1</ns2:SourceChargeIdentifier>\r\n" + 
        		"                     <ns2:ChargeCurrencyCode>USD</ns2:ChargeCurrencyCode>\r\n" + 
        		"                     <ns2:ChargeTypeCode>ORA_SALE</ns2:ChargeTypeCode>\r\n" + 
        		"                     <ns2:ChargeCurrencyCode/>\r\n" + 
        		"                     <ns2:SequenceNumber>1</ns2:SequenceNumber>\r\n" + 
        		"                     <ns2:PricePeriodicityCode/>\r\n" + 
        		"                     <ns2:GsaUnitPrice/>\r\n" + 
        		"                     <ns2:OrderChargeComponent>\r\n" + 
        		"                        <ns2:ChargeCurrencyCode/>\r\n" + 
        		"                        <ns2:HeaderCurrencyCode/>\r\n" + 
        		"                        <ns2:HeaderCurrencyExtendedAmount>100</ns2:HeaderCurrencyExtendedAmount>\r\n" + 
        		"                        <ns2:PriceElementCode>QP_NET_PRICE</ns2:PriceElementCode>\r\n" + 
        		"                        <ns2:SequenceNumber>2</ns2:SequenceNumber>\r\n" + 
        		"                        <ns2:PriceElementUsageCode>NET_PRICE</ns2:PriceElementUsageCode>\r\n" + 
        		"                        <ns2:HeaderCurrencyUnitPrice>100</ns2:HeaderCurrencyUnitPrice>\r\n" + 
        		"                        <ns2:RollupFlag>false</ns2:RollupFlag>\r\n" + 
        		"                        <ns2:SourceParentChargeComponentId/>\r\n" + 
        		"                        <ns2:ChargeCurrencyUnitPrice>10</ns2:ChargeCurrencyUnitPrice>\r\n" + 
        		"                        <ns2:SourceChargeIdentifier>SC1</ns2:SourceChargeIdentifier>\r\n" + 
        		"                        <ns2:SourceChargeComponentIdentifier>SCC1</ns2:SourceChargeComponentIdentifier>\r\n" + 
        		"                     </ns2:OrderChargeComponent>\r\n" + 
        		"                     <ns2:OrderChargeComponent>\r\n" + 
        		"                        <ns2:ChargeCurrencyCode/>\r\n" + 
        		"                        <ns2:HeaderCurrencyCode/>\r\n" + 
        		"                        <ns2:HeaderCurrencyExtendedAmount>1100</ns2:HeaderCurrencyExtendedAmount>\r\n" + 
        		"                        <ns2:PriceElementCode>QP_LIST_PRICE</ns2:PriceElementCode>\r\n" + 
        		"                        <ns2:SequenceNumber>1</ns2:SequenceNumber>\r\n" + 
        		"                        <ns2:PriceElementUsageCode>LIST_PRICE</ns2:PriceElementUsageCode>\r\n" + 
        		"                        <ns2:HeaderCurrencyUnitPrice>100</ns2:HeaderCurrencyUnitPrice>\r\n" + 
        		"                        <ns2:RollupFlag>false</ns2:RollupFlag>\r\n" + 
        		"                        <ns2:SourceParentChargeComponentId/>\r\n" + 
        		"                        <ns2:ChargeCurrencyUnitPrice>10</ns2:ChargeCurrencyUnitPrice>\r\n" + 
        		"                        <ns2:SourceChargeIdentifier>SC1</ns2:SourceChargeIdentifier>\r\n" + 
        		"                        <ns2:SourceChargeComponentIdentifier>SCC2</ns2:SourceChargeComponentIdentifier>\r\n" + 
        		"                        <ns2:ChargeCurrencyExtendedAmount>1100</ns2:ChargeCurrencyExtendedAmount>\r\n" + 
        		"                     </ns2:OrderChargeComponent>\r\n" + 
        		"                  </ns2:OrderCharge>\r\n" + 
        		"               </ns2:Line>\r\n" + 
        		"               <ns2:OrderPreferences/>\r\n" + 
        		"            </ns2:Order>\r\n" + 
        		"         </ns1:request>\r\n" + 
        		"      </ns1:createOrders>";

        createOISoapEnvelope(soapMessage, xmlStr);
        
        addSecurityHeader(soapMessage);

        //MimeHeaders headers = soapMessage.getMimeHeaders();
        //headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }
    
    private static void addSecurityHeader(SOAPMessage message) throws SOAPException {
    	
    	String username = "scmoperations";
        String password = "Welcome1";

        SOAPHeader header = message.getSOAPHeader();

        QName securityQ = new QName(WSS_SECURITY_XSD_STR, "Security", "wsse");
        SOAPHeaderElement security = header.addHeaderElement(securityQ);
        SOAPElement wsu =
            security.addNamespaceDeclaration("wsu",  WSS_SECURITY_UTIL_XSD_STR);

        security.setMustUnderstand(true);

        SOAPElement timestamp = security.addChildElement("Timestamp", "wsu");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:dd.SSS'Z'");

        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        SOAPElement created = timestamp.addChildElement("Created", "wsu");
        Date createdTime = new Date();
        created.addTextNode(formatter.format(createdTime));

        SOAPElement expires = timestamp.addChildElement("Expires", "wsu");
        //Add oneday
        Calendar c = Calendar.getInstance();
        c.setTime(createdTime);
        c.add(Calendar.DATE, 1);
        expires.addTextNode(formatter.format(c.getTime()));

        SOAPElement userNameToken = security.addChildElement("UsernameToken", "wsse");
        QName idAttr1 = new QName(null, "Id", "wsse");

        userNameToken.addAttribute(idAttr1, "UsernameToken-"+System.currentTimeMillis());

        SOAPElement userNameElem = userNameToken.addChildElement("Username", "wsse");
        userNameElem.addTextNode(username);

        SOAPElement passwordElem = userNameToken.addChildElement("Password", "wsse");
        QName passwordType = new QName("Type");
        passwordElem.addAttribute(passwordType,  WS_USERNAME_TOKEN);
        passwordElem.addTextNode(password);
    }	
}
