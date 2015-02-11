package teamtwo.event.com.events;

import android.app.ActionBar;
import android.nfc.Tag;
import android.os.AsyncTask;
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

    protected EditText rname;
    protected EditText rsurname;
    protected EditText rusername;
    protected EditText rpass1;
    protected EditText rpass2;
    protected EditText remail;
    protected Button rsignup;


    String requestURL = "http://veligovsek.si/events/apis/login.php";
    String data = "email=mitja.miklav@gmail.com&password=m";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //zažene(definira)

        rname = (EditText) findViewById(R.id.Name);
        rsurname = (EditText) findViewById(R.id.Surname);
        rusername = (EditText) findViewById(R.id.Username);
        rpass1 = (EditText) findViewById(R.id.Pass1);
        rpass2 = (EditText) findViewById(R.id.Pass2);
        remail = (EditText) findViewById(R.id.Email);
        rsignup = (Button) findViewById(R.id.SignUp);

        //ko pritisne signup button

        rsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = rname.getText().toString().trim();   //trim zbriše če je za imenom dal space
                String surname = rsurname.getText().toString().trim();
                String username = rusername.getText().toString().trim();
                String pass1 = rpass1.getText().toString().trim();
                String pass2 = rpass2.getText().toString().trim();
                String email = remail.getText().toString().trim();
                    List<NameValuePair> arguments = new ArrayList<>(5);
                    arguments.add(new BasicNameValuePair("name", name));
                    arguments.add(new BasicNameValuePair("surname", surname));
                    arguments.add(new BasicNameValuePair("username", username));
                    arguments.add(new BasicNameValuePair("password", pass1));
                    arguments.add(new BasicNameValuePair("email", email));


                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost("http://veligovsek.si/events/apis/register.php");

                    //
                    // Create some NameValuePair for HttpPost parameters
                    //

                    try {
                        post.setEntity(new UrlEncodedFormEntity(arguments));
                        HttpResponse response = client.execute(post);

                        //
                        // Print out the response message
                        //

                        Toast.makeText(RegisterActivity.this, "Signup Succesful", Toast.LENGTH_LONG).show();
                        System.out.println(EntityUtils.toString(response.getEntity()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }




              //      Toast.makeText(RegisterActivity.this, "Something went Wrong, Probably the passwords", Toast.LENGTH_LONG).show();
                    //passa nista enaka

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
}
