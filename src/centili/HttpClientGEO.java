package centili;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

/**
 * Created by lbulic on 3/25/17.
 */
public class HttpClientGEO extends Thread {

    private HttpClient httpClient;
    private HttpPost postRequest;
    private String url;
    private long numberOfRequestsPerMinute;
    private Gson gson;
    private List<String> countryInfo;
    private List<String> serviceInfo;
    private List<String> operatorsInfo;
    private boolean expanded;

    public HttpClientGEO(String url, int numberOfRequestsPerMinute, List<String> countryInfo) {
        this.url = url;
        this.numberOfRequestsPerMinute = 60000l / numberOfRequestsPerMinute;
        this.countryInfo = countryInfo;
        this.expanded = false;
    }

    public HttpClientGEO(String url, int numberOfRequestsPerMinute, List<String> countryInfo, List<String> operatorsInfo, List<String> serviceInfo) {
        this.url = url;
        this.numberOfRequestsPerMinute = 60000l / numberOfRequestsPerMinute;
        this.countryInfo = countryInfo;
        this.serviceInfo = serviceInfo;
        this.operatorsInfo = operatorsInfo;
        this.expanded = true;
    }


    public void run() {

        httpClient = HttpClients.createDefault();
        postRequest = new HttpPost(url);
        gson = new GsonBuilder().create();
        long increment = 0;

        while (true) {
            try {
                String json = gson.toJson(generateRandomTransfer());
                StringEntity requestEntity = new StringEntity(
                        json,
                        ContentType.APPLICATION_JSON);
                postRequest.setEntity(requestEntity);
                HttpResponse rawResponse = httpClient.execute(postRequest);
                //System.out.println("Request sent = " + postRequest + " data = " + json);
                //System.out.println("Response = " + rawResponse + "\n");
                System.out.println(++increment);
                if(rawResponse.getEntity() != null) {
                    rawResponse.getEntity().getContent().close();
                }
                sleep(numberOfRequestsPerMinute);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private Transfer generateRandomTransfer() {
        Transfer transfer;
        if(!expanded) {
            transfer = new Transfer(countryInfo);
        }else{
            transfer = new Transfer(countryInfo, serviceInfo, operatorsInfo);
        }
        return transfer;
    }


}
