package gprofiler.internal.HTTPRequests;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * For handling API requests to gProfiler
 */
public class HTTPRequests {
    private final String USER_AGENT = "Mozilla/5.0";
    private final String basicURL = "";
    public HttpResponse<String> makePostRequest(String endpoint , Map<String,String> parameters) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String url = this.basicURL+endpoint;
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap>(){}.getType();
        String jsonBody = gson.toJson(parameters,gsonType);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response;
    }
    public HttpResponse<String> makeGetRequests(String endpoint) throws IOException,InterruptedException {
        //fetches data using a specific api endpoint
        HttpClient client = HttpClient.newHttpClient();
        String url = this.basicURL+endpoint;
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept","application/json")
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response;
    }
};
