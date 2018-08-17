package com.example.baruch.recsearch.api;

import android.util.Log;

import com.example.baruch.recsearch.AudioFile;
import com.example.baruch.recsearch.SearchResults;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.AddDocumentOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.Collection;
import com.ibm.watson.developer_cloud.discovery.v1.model.Configuration;
import com.ibm.watson.developer_cloud.discovery.v1.model.CreateCollectionOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.CreateEnvironmentOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.DeleteCollectionOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.DocumentAccepted;
import com.ibm.watson.developer_cloud.discovery.v1.model.DocumentStatus;
import com.ibm.watson.developer_cloud.discovery.v1.model.Environment;
import com.ibm.watson.developer_cloud.discovery.v1.model.GetCollectionOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.GetDocumentStatusOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.GetEnvironmentOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.ListConfigurationsOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.ListConfigurationsResponse;
import com.ibm.watson.developer_cloud.discovery.v1.model.ListEnvironmentsOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.ListEnvironmentsResponse;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryOptions;
import com.ibm.watson.developer_cloud.discovery.v1.model.QueryResponse;
import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechRecognitionResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Searcher {
    private static final String DEFAULT_CONFIG_NAME = "Default Configuration";

//    public JSONArray search(String query){
//        JSONArray res = new JSONArray();
//        discovery.query(
//                new QueryOptions().naturalLanguageQuery().
//        );
//
//        return res;
//    }


    Discovery discovery;

    void init() {

        discovery = new Discovery(
                "2018-08-01",
                "21b9e719-2b0c-4758-85e4-3b5b2b90cee1",
                "Mqsez5Qb3hUx"
        );

        discovery.setEndPoint("https://gateway.watsonplatform.net/discovery/api");


    }

    void index(String record, String file) {


        Discovery discovery = new Discovery("2017-11-07");
        discovery.setEndPoint("https://gateway.watsonplatform.net/discovery/api");
        discovery.setUsernameAndPassword("21b9e719-2b0c-4758-85e4-3b5b2b90cee1", "Mqsez5Qb3hUx");
//        String environmentId = null;
//        String configurationId = null;
//        String collectionId = null;
//        String documentId = null;
//
//        //See if an environment already exists
//        System.out.println("Check if environment exists");
//        ListEnvironmentsOptions listOptions = new ListEnvironmentsOptions.Builder().build();
//        ListEnvironmentsResponse listResponse = discovery.listEnvironments(listOptions).execute();
//        for (Environment environment : listResponse.getEnvironments()) {
//            //look for an existing environment that isn't read only
//            if (!environment.isReadOnly()) {
//                environmentId = environment.getEnvironmentId();
//                System.out.println("Found existing environment ID: " + environmentId);
//                break;
//            }
//        }
//
//        if (environmentId == null) {
//            System.out.println("No environment found, creating new one...");
//            //no environment found, create a new one (assuming we are a FREE plan)
//            String environmentName = "watson_developer_cloud_test_environment";
//            CreateEnvironmentOptions createOptions = new CreateEnvironmentOptions.Builder()
//                    .name(environmentName)
//                    .size(0L)  /* FREE */
//                    .build();
//            Environment createResponse = discovery.createEnvironment(createOptions).execute();
//            environmentId = createResponse.getEnvironmentId();
//            System.out.println("Created new environment ID: " + environmentId);
//
//            //wait for environment to be ready
//            System.out.println("Waiting for environment to be ready...");
//            boolean environmentReady = false;
//            while (!environmentReady) {
//                GetEnvironmentOptions getEnvironmentOptions = new GetEnvironmentOptions.Builder(environmentId).build();
//                Environment getEnvironmentResponse = discovery.getEnvironment(getEnvironmentOptions).execute();
//                environmentReady = getEnvironmentResponse.getStatus().equals(Environment.Status.ACTIVE);
//                try {
//                    if (!environmentReady) {
//                        Thread.sleep(500);
//                    }
//                } catch (InterruptedException e) {
//                    throw new RuntimeException("Interrupted", e);
//                }
//            }
//            System.out.println("Environment Ready!");
//        }
//
//        //find the default configuration
//        System.out.println("Finding the default configuration");
//        ListConfigurationsOptions listConfigsOptions = new ListConfigurationsOptions.Builder(environmentId).build();
//        ListConfigurationsResponse listConfigsResponse = discovery.listConfigurations(listConfigsOptions).execute();
//        for (Configuration configuration : listConfigsResponse.getConfigurations()) {
//            if (configuration.getName().equals(DEFAULT_CONFIG_NAME)) {
//                configurationId = configuration.getConfigurationId();
//                System.out.println("Found default configuration ID: " + configurationId);
//                break;
//            }
//        }
//
//        //create a new collection
//        System.out.println("Creating a new collection...");
//        String collectionName = "my_watson_developer_cloud_collection";
//        CreateCollectionOptions createCollectionOptions =
//                new CreateCollectionOptions.Builder(environmentId, collectionName)
//                        .configurationId(configurationId)
//                        .build();
//        Collection collection = discovery.createCollection(createCollectionOptions).execute();
//        collectionId = collection.getCollectionId();
//        System.out.println("Created a collection ID: " + collectionId);
//
//        //wait for the collection to be "available"
//        System.out.println("Waiting for collection to be ready...");
//        boolean collectionReady = false;
//        while (!collectionReady) {
//            GetCollectionOptions getCollectionOptions =
//                    new GetCollectionOptions.Builder(environmentId, collectionId).build();
//            Collection getCollectionResponse = discovery.getCollection(getCollectionOptions).execute();
//            collectionReady = getCollectionResponse.getStatus().equals(Collection.Status.ACTIVE);
//            try {
//                if (!collectionReady) {
//                    Thread.sleep(500);
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException("Interrupted", e);
//            }
//        }
//        System.out.println("Collection Ready!");
//
//        //add a document
//        System.out.println("Creating a new document...");
        String documentJson = record;
        InputStream documentStream = new ByteArrayInputStream(documentJson.getBytes());

        AddDocumentOptions.Builder createDocumentBuilder =
                new AddDocumentOptions.Builder("49aa886a-fb51-4002-a537-4b51fe65012a",
                        "44b9e87f-06e4-482b-96af-7f8598d262ba");
        createDocumentBuilder.file(documentStream).filename(file).fileContentType(HttpMediaType.APPLICATION_JSON);
        DocumentAccepted createDocumentResponse = discovery.addDocument(createDocumentBuilder.build()).execute();
//        String documentId = createDocumentResponse.getDocumentId();
//        System.out.println("Created a document ID: " + documentId);
//
//        //wait for document to be ready
//        System.out.println("Waiting for document to be ready...");
//        boolean documentReady = false;
//        while (!documentReady) {
//            GetDocumentStatusOptions getDocumentStatusOptions =
//                    new GetDocumentStatusOptions.Builder("49aa886a-fb51-4002-a537-4b51fe65012a",
//                            "44b9e87f-06e4-482b-96af-7f8598d262ba", documentId).build();
//            DocumentStatus getDocumentResponse = discovery.getDocumentStatus(getDocumentStatusOptions).execute();
//            documentReady = !getDocumentResponse.getStatus().equals(DocumentStatus.Status.PROCESSING);
//            try {
//                if (!documentReady) {
//                    Thread.sleep(500);
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException("Interrupted");
//            }
//        }
//        System.out.println("Document Ready!");
//
//        //query document
        System.out.println("Querying the collection...");
        QueryOptions.Builder queryBuilder = new QueryOptions.Builder("49aa886a-fb51-4002-a537-4b51fe65012a",
                "44b9e87f-06e4-482b-96af-7f8598d262ba");
        queryBuilder.query("several");
        QueryResponse queryResponse = discovery.query(queryBuilder.build()).execute();

        //print out the results
        System.out.println("Query Results:");
        System.out.println(queryResponse);

        //cleanup the collection created
//        System.out.println("Deleting the collection...");
//        DeleteCollectionOptions deleteOptions =
//                new DeleteCollectionOptions.Builder(environmentId, collectionId).build();
//        discovery.deleteCollection(deleteOptions).execute();
//        System.out.println("Collection deleted!");
//
//        System.out.println("Discovery example finished");
//
//
//        InputStream is = new ByteArrayInputStream( record.getBytes() );
//        ServiceCall<DocumentAccepted> documentAcceptedServiceCall =
//                discovery.addDocument(new AddDocumentOptions.Builder().fileContentType("josn").file(is).collectionId("test1").build());
//        DocumentAccepted documentAccepted = documentAcceptedServiceCall.execute();
//        System.out.print(documentAccepted);
    }

    public SearchResults perfolrmSearch(String searchTerm) {
        SearchResults searchResults = new SearchResults();
        List<AudioFile> audioFileList = new ArrayList<>();
        searchResults.resultList = audioFileList;
        try {

            QueryOptions.Builder queryBuilder = new QueryOptions.Builder("49aa886a-fb51-4002-a537-4b51fe65012a",
                    "44b9e87f-06e4-482b-96af-7f8598d262ba");
            queryBuilder.query("several");
            QueryResponse queryResponse = discovery.query(queryBuilder.build()).execute();
            JSONObject jsonObject = new JSONObject(queryResponse.toString());

            JSONArray josnArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < josnArray.length(); i++) {
                JSONObject obj = josnArray.getJSONObject(i);
                if (obj.has("file")) {
                    String file = obj.getString("file");
                    AudioFile audioFile = new AudioFile();
                    audioFile.name = file;
                    if (obj.has("transcript")) {
                        String transcript = obj.getString("transcript");
                        audioFile.text = transcript;
                    }
                    audioFileList.add(audioFile);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  searchResults;
    }

}
