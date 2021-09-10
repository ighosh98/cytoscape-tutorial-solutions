import gprofiler.internal.HTTPRequests.HTTPRequests;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;

public class testingProject {
    private static Map<String, String> generateQuery(String query) {
        HashMap<String,String> parameters = new HashMap<>();
        parameters.put("organism","hsapiens");
        parameters.put("query",query);
        return parameters;
    }

    public static void main(String[] args){
        Set<String> selectedNodes = new HashSet<>(){{
            add("CASQ2");
            add("CASQ1");
            add("GSTO1");
            add("DMD");
            add("GSTM2");
        }};

        StringBuffer query = new StringBuffer("");

        Iterator<String> setIterator = selectedNodes.iterator();
        query.append("\"");
        while(setIterator.hasNext()){
            query.append(setIterator.next());
            query.append(" ");
        }
        query.append("\"");
        System.out.println(query);
        Map<String,String> parameters = generateQuery(query.toString());
        HTTPRequests requestEngine = new HTTPRequests();
        HttpResponse<String> response = null;
        try {
            response = requestEngine.makePostRequest("gost/profile/",parameters);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(response.body());
    }
}
