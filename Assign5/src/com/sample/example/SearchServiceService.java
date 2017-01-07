
package com.sample.example;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;



@WebServiceClient(name = "SearchServiceService", targetNamespace = "http://example.sample.com/", wsdlLocation = "http://localhost:8080/WSSearchService/SearchService?wsdl")
public class SearchServiceService
    extends Service
{

    private final static URL SEARCHSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException SEARCHSERVICESERVICE_EXCEPTION;
    private final static QName SEARCHSERVICESERVICE_QNAME = new QName("http://example.sample.com/", "SearchServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/WSSearchService/SearchService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SEARCHSERVICESERVICE_WSDL_LOCATION = url;
        SEARCHSERVICESERVICE_EXCEPTION = e;
    }

    public SearchServiceService() {
        super(__getWsdlLocation(), SEARCHSERVICESERVICE_QNAME);
    }

    public SearchServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SEARCHSERVICESERVICE_QNAME, features);
    }

    public SearchServiceService(URL wsdlLocation) {
        super(wsdlLocation, SEARCHSERVICESERVICE_QNAME);
    }

    public SearchServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SEARCHSERVICESERVICE_QNAME, features);
    }

    public SearchServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SearchServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SearchService
     */
    @WebEndpoint(name = "SearchServicePort")
    public SearchService getSearchServicePort() {
        return super.getPort(new QName("http://example.sample.com/", "SearchServicePort"), SearchService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SearchService
     */
    @WebEndpoint(name = "SearchServicePort")
    public SearchService getSearchServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://example.sample.com/", "SearchServicePort"), SearchService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SEARCHSERVICESERVICE_EXCEPTION!= null) {
            throw SEARCHSERVICESERVICE_EXCEPTION;
        }
        return SEARCHSERVICESERVICE_WSDL_LOCATION;
    }

}
