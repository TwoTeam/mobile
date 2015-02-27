package teamtwo.event.com.events;

import android.app.ActionBar;
import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;



public class RegisterActivity extends ActionBarActivity {

    Context c; //ne vem kaj je to, ampak to rabiš


    //username,, name, surname, password, email

    String name, surname, username, password1,password2, email;
     EditText
      etName,
      etSurname,
      etUsername,
      etPass1,
      etPass2,
      etEmail;


     Button bSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        c=this;
        //zažene(definira)

        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPass1 = (EditText) findViewById(R.id.etPassword1);
        etPass2 = (EditText) findViewById(R.id.etPassword2);
        etEmail = (EditText) findViewById(R.id.etEmail);
        bSignup = (Button) findViewById(R.id.btSignup);



        //ko pritisne signup button
        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _("Register button hit!");

                username = etUsername.getText()+""; //" " da je pol string drugač je error
                name = etName.getText()+"";
                surname = etSurname.getText()+"";
                password1 = etPass1.getText()+"";
                password2 = etPass2.getText()+"";
                email = etEmail.getText()+"";

                _("username:"+username);
                _("name:"+name);
                _("surname:"+surname);
                _("password1:"+password1);
                _("password2:"+password2);
                _("email:"+email);


                if(password1.equals(password2)) {
                    if (username.length() == 0 || name.length() == 0 || surname.length() == 0 || password1.length() == 0 || password2.length() == 0 || email.length() == 0) //!!!!ŠE VEČ KOMBINACIJ
                    {
                        toast("Please fill in all the forms");
                        return;
                    }
                }
                else
                {
                    toast("Please check that the passwords are the same");
                }

                //networking
                Networking n = new Networking();
                n.execute("http://veligovsek.si/events/apis/register.php",Networking.NETWORK_STATE_REGISTER);
            }
        });

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
                postParameters.add(new BasicNameValuePair("username",username));
                postParameters.add(new BasicNameValuePair("name",name));
                postParameters.add(new BasicNameValuePair("surname",surname));
                postParameters.add(new BasicNameValuePair("password",password1));
                postParameters.add(new BasicNameValuePair("email",email));

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


            toast("Registration Successful!");
        }
        catch ( JSONException e)
        {
            _("WARNING PROBLEM DECODING JSON!"+e.getMessage());
        }


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

    private void _(String s)  //pokaže v logcatu za TESTINNG :P
    {
        Log.d("MyApp", "RegisterActivity" + "#######"+ s);
    }


}
