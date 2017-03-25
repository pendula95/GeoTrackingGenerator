package centili;

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

    public HttpClientGEO(String url, int numberOfRequestsPerMinute, List<String> countryInfo) {
        this.url = url;
        this.numberOfRequestsPerMinute = 60000l / numberOfRequestsPerMinute;
        this.countryInfo = countryInfo;

        httpClient = HttpClients.createDefault();
        postRequest = new HttpPost(url);
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    public void run() {
        while (true) {
            try {
                String json = gson.toJson(generateRandomTransfer());
                StringEntity requestEntity = new StringEntity(
                        json,
                        ContentType.APPLICATION_JSON);
                postRequest.setEntity(requestEntity);
                HttpResponse rawResponse = httpClient.execute(postRequest);
                System.out.println("Request sent = " + postRequest + " data = " + json);
                System.out.println("Response = " + rawResponse + "\n");
                rawResponse.getEntity().getContent().close();

                sleep(numberOfRequestsPerMinute);

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private Transfer generateRandomTransfer() {
        Transfer transfer = new Transfer(countryInfo);
        return transfer;
    }


}
