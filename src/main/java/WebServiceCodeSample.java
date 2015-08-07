import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public final class WebServiceCodeSample {
    private static final String TECHNET_NAMESPACE_PREFIX = "technet";
    private static final String WEBSERVICE_SECURE_URL = "https://technet.rapaport.com/webservices/prices/rapaportprices.asmx";
    private static final String WEBSERVICE_INSECURE_URL = "http://technet.rapaport.com/webservices/prices/rapaportprices.asmx";

    private enum Shapes {
        ROUND("Round"), PEAR("Pear");

        private final String enumString;

        private Shapes(final String enumString) {
            this.enumString = enumString;
        }
    }

    ;

    public static void main(String[] args) throws Exception {
        final WebServiceCodeSample webServiceCodeSample = new WebServiceCodeSample();
        final String authenticationTicket = webServiceCodeSample.login(
                "username", "password");

        webServiceCodeSample.getPrice(authenticationTicket, "Round", 0.4F, "D",
                "VS1");
        webServiceCodeSample.getPriceSheet(authenticationTicket, Shapes.ROUND);
    }

    /**
     * Get the login token
     *
     * @param username
     * @param password
     * @return The authentication ticket
     * @throws SOAPException
     */
    private String login(final String username, final String password)
            throws SOAPException {
        final SOAPMessage soapMessage = getSoapMessage();
        final SOAPBody soapBody = soapMessage.getSOAPBody();
        final SOAPElement loginElement = soapBody.addChildElement("Login",
                TECHNET_NAMESPACE_PREFIX);

        loginElement.addChildElement("Username", TECHNET_NAMESPACE_PREFIX)
                .addTextNode(username);
        loginElement.addChildElement("Password", TECHNET_NAMESPACE_PREFIX)
                .addTextNode(password);

        soapMessage.saveChanges();

        final SOAPConnection soapConnection = getSoapConnection();
        final SOAPMessage soapMessageReply = soapConnection.call(soapMessage,
                WEBSERVICE_SECURE_URL);
        final String textContent = soapMessageReply.getSOAPHeader()
                .getFirstChild().getTextContent();

        soapConnection.close();

        return textContent;
    }

    /**
     * Returns the price
     *
     * @param authenticationTicket
     * @param shape
     * @param size
     * @param color
     * @param clarity
     * @throws SOAPException
     */
    private void getPrice(final String authenticationTicket,
                          final String shape, final float size, final String color,
                          final String clarity) throws SOAPException {
        final SOAPMessage soapMessage = getSoapMessage();

        addAuthenticationTicket(authenticationTicket, soapMessage);

        final SOAPBody soapBody = soapMessage.getSOAPBody();
        final SOAPElement getPriceElement = soapBody.addChildElement(
                "GetPrice", TECHNET_NAMESPACE_PREFIX);
        getPriceElement.addChildElement("shape", TECHNET_NAMESPACE_PREFIX)
                .addTextNode(shape);
        getPriceElement.addChildElement("size", TECHNET_NAMESPACE_PREFIX)
                .addTextNode(String.valueOf(size));
        getPriceElement.addChildElement("color", TECHNET_NAMESPACE_PREFIX)
                .addTextNode(color);
        getPriceElement.addChildElement("clarity", TECHNET_NAMESPACE_PREFIX)
                .addTextNode(clarity);

        soapMessage.saveChanges();

        final SOAPConnection soapConnection = getSoapConnection();

        final SOAPMessage soapMessageReply = soapConnection.call(soapMessage,
                WEBSERVICE_INSECURE_URL);

        final SOAPBody replyBody = soapMessageReply.getSOAPBody();
        final Node getPriceResponse = replyBody.getFirstChild();
        final Node getPriceResult = getPriceResponse.getFirstChild();

        final NodeList childNodes = getPriceResult.getChildNodes();
        final String replyShape = childNodes.item(0).getTextContent();
        final String lowSize = childNodes.item(1).getTextContent();

        // ... etc etc
        // You can create a bean that will encompass all elements

        soapConnection.close();
    }

    /**
     * Gets the price sheet
     *
     * @param authenticationTicket
     * @param shapes
     * @throws SOAPException
     * @throws TransformerException
     */
    private void getPriceSheet(final String authenticationTicket,
                               final Shapes shapes) throws SOAPException, TransformerException {
        final SOAPMessage soapMessage = getSoapMessage();

        addAuthenticationTicket(authenticationTicket, soapMessage);

        final SOAPBody soapBody = soapMessage.getSOAPBody();

        final SOAPElement getPriceSheetElement = soapBody.addChildElement(
                "GetPriceSheet", TECHNET_NAMESPACE_PREFIX);

        getPriceSheetElement.addChildElement("shape", TECHNET_NAMESPACE_PREFIX)
                .addTextNode(shapes.enumString);

        soapMessage.saveChanges();

        final SOAPConnection soapConnection = getSoapConnection();
        final SOAPMessage soapMessageReply = soapConnection.call(soapMessage,
                WEBSERVICE_INSECURE_URL);

        // this will print out the result
        // Create transformer

        final TransformerFactory tff = TransformerFactory.newInstance();
        final Transformer tf = tff.newTransformer();

        // Get reply content
        final Source sc = soapMessageReply.getSOAPPart().getContent();

        // Set output transformation
        final StreamResult result = new StreamResult(System.out);
        tf.transform(sc, result);
        System.out.println();

        // Close connection
        soapConnection.close();
    }

    /**
     * Create a SOAP Connection
     *
     * @return
     * @throws UnsupportedOperationException
     * @throws SOAPException
     */
    private SOAPConnection getSoapConnection()
            throws UnsupportedOperationException, SOAPException {
        final SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory
                .newInstance();
        final SOAPConnection soapConnection = soapConnectionFactory
                .createConnection();

        return soapConnection;
    }

    /**
     * Create the SOAP Message
     *
     * @return
     * @throws SOAPException
     */
    private SOAPMessage getSoapMessage() throws SOAPException {
        final MessageFactory messageFactory = MessageFactory.newInstance();
        final SOAPMessage soapMessage = messageFactory.createMessage();

        // Object for message parts
        final SOAPPart soapPart = soapMessage.getSOAPPart();
        final SOAPEnvelope envelope = soapPart.getEnvelope();

        envelope.addNamespaceDeclaration("xsd",
                "http://www.w3.org/2001/XMLSchema");
        envelope.addNamespaceDeclaration("xsi",
                "http://www.w3.org/2001/XMLSchema-instance");
        envelope.addNamespaceDeclaration("enc",
                "http://schemas.xmlsoap.org/soap/encoding/");
        envelope.addNamespaceDeclaration("env",
                "http://schemas.xmlsoap.org/soap/envelop/");

        // add the technet namespace as "technet"
        envelope.addNamespaceDeclaration(TECHNET_NAMESPACE_PREFIX,
                "http://technet.rapaport.com/");

        envelope.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");

        return soapMessage;
    }

    private void addAuthenticationTicket(final String authenticationTicket,
                                         final SOAPMessage soapMessage) throws SOAPException {
        final SOAPHeader header = soapMessage.getSOAPHeader();
        final SOAPElement authenticationTicketHeader = header.addChildElement(
                "AuthenticationTicketHeader", TECHNET_NAMESPACE_PREFIX);
        authenticationTicketHeader.addChildElement("Ticket",
                TECHNET_NAMESPACE_PREFIX).addTextNode(authenticationTicket);
    }
}