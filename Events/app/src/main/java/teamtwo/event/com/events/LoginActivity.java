package teamtwo.event.com.events;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.logging.LogRecord;


public class LoginActivity extends ActionBarActivity {

    Context c; //ne vem kaj je to, ampak to rabiš

    EditText etUsername,
             etPassword;

    String username, password;
    Button bLogin; //ta dela
    ImageButton ibButton; //še ne dela, vse isto sam da je image, naredi namesto button
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        c = this;


        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bLogin = (Button) findViewById(R.id.bLogin);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _("Login button hit!");

                username = etUsername.getText()+""; //" " da je pol string drugač je error
                password = etPassword.getText()+"";
                _("username:"+username);
                _("password:"+password);

                if (username.length()==0 || password.length()==0) //!!!!ŠE VEČ KOMBINACIJ
                {
                   toast("Please fill in username and password");
                    return;
                }

                //networking
                Networking n = new Networking();
                n.execute("http://veligovsek.si/events/apis/login.php",Networking.NETWORK_STATE_REGISTER);

            }
        });
    }
    private void toast(final String s)
    {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
                    }
                }
                 );
    }



    //ASYNC, rabiš drugač se crasha(dela na v drugih treadih in ne na mainu
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
        HttpPost request = new HttpPost(url);
        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();

        boolean valid = false;

           switch (state)
           {
               case Networking.NETWORK_STATE_REGISTER:
                   postParameters.add(new BasicNameValuePair("user",username));
                   postParameters.add(new BasicNameValuePair("password",password));
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
            _("result: "+stringBuffer);


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
        "response":true,
        "name":"a",
        "surname":"a",
        "id":"4"
        */

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

            String name = jo.getString("name"); //za druge isto narediš

            _("JSON CONTENT:");
            _("name:"+name);


            toast("Login Successful!");
        }
        catch ( JSONException e)
        {
            _("WARNING PROBLEM DECODING JSON!"+e.getMessage());
        }


    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    private void _(String s)  //pokaže v logcatu za TESTINNG :P
    {
        Log.d("MyApp", "LoginActivity" + "#######"+ s);
    }
}
