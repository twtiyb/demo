import org.apache.soap.*;
import org.apache.soap.rpc.*;

import java.net.URL;
import java.util.Vector;

public class HelloClient {
    public static void main(String[] args) throws Exception {
        URL url = new URL("http://sws2.vjia.com/test/Service.asmx?wsdl'");
        String username = "Guest";
        if (args.length != 0)
            username = args[0];
        // Build the call.
        Call call = new Call();
        call.setTargetObjectURI("urn:helloservice");
        call.setMethodName("sayHello");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
        Vector params = new Vector();
        params.addElement(new Parameter("username", String.class, username,
                null));
        call.setParams(params);
        // make the call: note that the action URI is empty because the
        // XML-SOAP rpc router does not need this. This may change in the
        // future.
        Response resp = call.invoke(url, "");
        // Check the response.
        if (resp.generatedFault()) {
            Fault fault = resp.getFault();
            System.out.println("The call failed: ");
            System.out.println("   Fault Code    = " + fault.getFaultCode());
            System.out.println("   Fault String = " + fault.getFaultString());
        } else {
            Parameter result = resp.getReturnValue();
            System.out.println(result.getValue());
        }
    }


    public String Encode(String data, String Key, String Iv) {

        //?????????????
        byte[] byKey = Key.getBytes();
        byte[] byIV = Iv.getBytes();

        DESCryptoServiceProvider cryptoProvider = new DESCryptoServiceProvider();
        int i = cryptoProvider.KeySize;
        MemoryStream ms = new MemoryStream();
        CryptoStream cst = new CryptoStream(ms, cryptoProvider.CreateEncryptor(byKey, byIV), CryptoStreamMode.Write);

        StreamWriter sw = new StreamWriter(cst);
        sw.Write(data);
        sw.Flush();
        cst.FlushFinalBlock();
        sw.Flush();
        return Convert.ToBase64String(ms.GetBuffer(), 0, (int) ms.Length);
    }