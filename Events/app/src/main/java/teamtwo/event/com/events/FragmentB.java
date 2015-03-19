package teamtwo.event.com.events;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends android.support.v4.app.Fragment{

    String jid;

    int mode = Activity.MODE_PRIVATE;
    SharedPreferences mySharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_b,container,false);


        SharedPreferences pref = this.getActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE);
        jid = pref.getString("id", "null");

        _(jid);


        ArrayList<SearchResult> searchResults = GetSearchResults();

        final ListView lv1 = (ListView) view.findViewById(R.id.lvEvents);
        lv1.setAdapter(new CustomAdapterEvents(this, searchResults));

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                SearchResult fullObject = (SearchResult)o;
                Toast.makeText(getActivity(), "You have chosen: " + " " + fullObject.getName(), Toast.LENGTH_LONG).show();
            }
        });

      /*  Networking n = new Networking();
        n.execute("http://veligovsek.si/events/apis/login.php", Networking.NETWORK_STATE_REGISTER);
 */       return  view;
    }



    private ArrayList<SearchResult> GetSearchResults(){
        ArrayList<SearchResult> results = new ArrayList<SearchResult>();

        SearchResult sr1 = new SearchResult();
        sr1.setName(jid);
        sr1.setDate("5/10/2017");
        sr1.setCity("Velenje");
        sr1.setType("Klasika");
        results.add(sr1);

        SearchResult sr2 = new SearchResult();
        sr1.setName(jid);
        sr1.setDate("5/10/2017");
        sr1.setCity("Velenje");
        sr1.setType("Klasika");
        results.add(sr2);

        SearchResult sr3 = new SearchResult();
        sr1.setName("Testing");
        sr1.setDate("5/10/2017");
        sr1.setCity("Velenje");
        sr1.setType("Klasika");
        results.add(sr3);

        return results;
    }
    /*
    private class Networking extends AsyncTask
    {

        public static final int NETWORK_STATE_REGISTER = 1;
        @Override
        protected String doInBackground(Object[] params) {

            _("doInBackground. ");
            // getJson("http://veligovsek.si/events/apis/register.php", NETWORK_STATE_REGISTER); //REGISTER WEB.PHP
            getJson((String)params[0],(Integer)params[1]); //dobi od n.execute.....
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
                postParameters.add(new BasicNameValuePair("user",));
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
        *//*
{ "result":
        [{"response":true,
        "name":"a",
        "surname":"a",
        "id":"4"}]
}
        *//*

        if(response.contains("error"))
        {
            _("errors are detected!!!");
            try {
                JSONObject jo = new JSONObject(response);
                String error = jo.getString("error");
                _("Error from server is::"+error);
                toast(error);
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

            jname = finalresult.getString("name"); //za druge isto narediš
            jid = finalresult.getString("id");

            _("JSON CONTENT:");
            _("name:"+jname);
            _("id:"+jid);

            //REMEMBER ME
            SharedPreferences.Editor editor = mySharedPreferences.edit();

            editor.putString("name",jname);
            editor.putString("id",jid);
            editor.commit();
        *//*    getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                    .edit()
                    .putString(PREF_USERNAME, jname)
                    .putString(PREF_ID, jid)
                    .commit();

*//*


            toast("Login Successful!");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            // String value = intent.getStringExtra("id") //če rabimo parej poslat
            LoginActivity.this.startActivity(intent);
            _("SAVING ID: "+jid);
            _("SAVING User: "+jname);

        }
        catch ( JSONException e)
        {
            _("WARNING PROBLEM DECODING JSON!"+e.getMessage());
            //   toast(response);
            toast("Username or Password is incorrect or doesn't exist!");

        }


    }


    protected boolean isOnline(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
            return true;
        }
        else{
            Toast.makeText(this,"Network isn't available", Toast.LENGTH_SHORT);
            return false;
        }
    }
 */
    private void _(String s)  //pokaže v logcatu za TESTINNG :P
    {
        Log.d("MyApp", "LoginActivity" + "#######" + s);
    }

    private void toast(final String s)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}
