package teamtwo.event.com.events;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Primož Pesjak on 25.3.2015.
 */
public class GetEvents {
//returni array, da lahko potem se vse kliče

 //  public static String[] array = new String[4];

    private ArrayList<String> array;

    //ASYNC, rabiš drugač se crasha(dela na v drugih treadih in ne na mainu
    private class Networking extends AsyncTask
    {

        public static final int NETWORK_STATE_REGISTER = 1;
        @Override
        protected String doInBackground(Object[] params) {

            _("doInBackground. ");
            // getJson("http://veligovsek.si/events/apis/register.php", NETWORK_STATE_REGISTER); //REGISTER WEB.PHP
            getJson("http://veligovsek.si/events/apis/events.php",Networking.NETWORK_STATE_REGISTER); //dobi od n.execute.....
            return null;

        }
    }

    private void getJson(String url, int state)  //JSON
    {
        HttpClient httpClient=new DefaultHttpClient();
        _("doing POST to URL now...");
        //  spinner.setVisibility(View.VISIBLE);
        HttpPost request = new HttpPost(url);
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

        boolean valid = false;

        switch (state)
        {
            case Networking.NETWORK_STATE_REGISTER:
               // postParameters.add(new BasicNameValuePair("user",username));
              //  postParameters.add(new BasicNameValuePair("password",password));
                valid = true;
                break;
            default:
                _("////////WARNING UNKNOWN STATE!//////////");
        }

        if(valid)
        {
            _("Valid!");

            BufferedReader bufferedReader = null;
            StringBuffer stringBuffer= new StringBuffer("");
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParameters);
                request.setEntity(entity);
                HttpResponse response = httpClient.execute(request);

                bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line = "";
                String LineSeparator = System.getProperty("line.seperator");
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + LineSeparator);
                }
                bufferedReader.close();
            }
            catch (Exception e)
            {
                _("error doing networking!!!"+ e.getMessage());
                e.printStackTrace();

            }
            _("result: " + stringBuffer);

            /////

            decodeResultIntoJson(stringBuffer.toString());

            /////
        }
        else
        {
            _("Valid was not true! Will do nothing");
        }
    }

    private void decodeResultIntoJson(String response)
    {


        //napiš ka ti je vrnu json (46min)
        /*
{ "result":
        [{"response":true,
        "name":"a",
        "surname":"a",
        "id":"4"}]
}
        */

        if(response.contains("error"))
        {
            _("errors are detected!!!");
            try {
                JSONObject jo = new JSONObject(response);
                String error = jo.getString("error");
                _("Error from server is::"+error);
            }
            catch ( JSONException e)
            {
                _("AAAA WARNING PROBLEM DECODING JSON!"+e.getMessage());
            }
            return;
        }
        else{
            _("No error detected");
        }

        try {


            JSONObject jo = new JSONObject(response);

            JSONArray results = jo.getJSONArray("result");

            JSONObject finalresult =results.getJSONObject(0);

            array = new ArrayList<String>();


            array.add(finalresult.getString("picture"));
            array.add(finalresult.getString("name")); //za druge isto narediš
            array.add(finalresult.getString("date"));
            array.add(finalresult.getString("city"));


/*
            _("JSON CONTENT:");
            _("picture:"+array[0]);
            _("name:"+array[1]);
            _("date:"+array[2]);
            _("city:"+array[3]);
*/


            return;


        }
        catch ( JSONException e)
        {
            _("WARNING PROBLEM DECODING JSON!"+e.getMessage());
            //   toast(response);

        }


    }

/*    public ArrayList<String> getEv
    {
       return apo
   }}*/

    public String getEvent(int pos)
    {
        return array.get(pos);
    }
    private void _(String s)  //pokaže v logcatu za TESTINNG :P
    {
        Log.d("MyApp", "LoginActivity" + "#######" + s);
    }
}


