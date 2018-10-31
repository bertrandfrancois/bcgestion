package com.factory;

import org.springframework.stereotype.Component;

@Component
public class UrlFactory {

    public String newEstimateDocumentLine(long clientId, long documentId) {
        return "/clients/" + clientId + "/estimates/" + documentId + "/addLine";
    }

    public String editEstimateDocumentLine(long clientId, long documentId, long documentLineId) {
        return "/clients/" + clientId + "/estimates/" + documentId + "/editLine/" + documentLineId;
    }

    public String newServiceInvoiceDocumentLine(long clientId, long documentId) {
        return "/clients/" + clientId + "/services/" + documentId + "/addLine";
    }

    public String editServiceInvoiceDocumentLine(long clientId, long documentId, long documentLineId) {
        return "/clients/" + clientId + "/services/" + documentId + "/editLine/" + documentLineId;
    }

    public String newProject(long clientId) {
        return "/clients/"+ clientId + "/projects/create";
    }

    public String editProject(long clientId, long projectId) {
        return "/clients/"+ clientId + "/projects/" + projectId +"/edit";
    }
}
