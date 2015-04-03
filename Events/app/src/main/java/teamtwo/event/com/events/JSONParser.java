package teamtwo.event.com.events;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Primož Pesjak on 20.3.2015.
 */
public class JSONParser {

    static InputStream inputStream = null;
    static JSONArray jsonArray = null;;
    static  String json = "";

    public JSONParser()
    {

    }
    public JSONArray getJSONfromUrl(String url)
    {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);

        try{
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line=reader.readLine()) != null)
                {
                    builder.append(line);
                }
            }
            else {
                Log.e("==>", "Failed to download file");
            }
        }catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        try{
            jsonArray = new JSONArray(builder.toString());
        }catch (JSONException e)
        {
            Log.e("JSON Parser", "Error parsing data "+ e.toString());
        }
        return  jsonArray;
    }
}
