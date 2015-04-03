package teamtwo.event.com.events;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import teamtwo.event.com.events.Events.AppController;
import teamtwo.event.com.events.Events.CustomListAdapter;
import teamtwo.event.com.events.Events.Event;


public class FragmentB extends android.support.v4.app.Fragment  {

    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Events json url
    private static final String url = "http://veligovsek.si/events/apis/events.php";
    private ProgressDialog pDialog;
    private List<Event> eventList = new ArrayList<Event>();
    private ListView listView;
    private CustomListAdapter adapter;
    private View myFragmentView;


public void onActivityCreated(Bundle savedInstanceState)
{
    super.onActivityCreated(savedInstanceState);

    listView = (ListView) myFragmentView.findViewById(R.id.list);
    if(adapter == null) {
        adapter = new CustomListAdapter(getActivity(), eventList);
        listView.setAdapter(adapter);
    }



    JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Event event = new Event();
                                event.setTitle(obj.getString("name"));
                                event.setPicture_url(obj.getString("picture_url"));
                                event.setCity(obj.getString("city"));
                                event.setDate(obj.getString("date"));

                                // Genre is json array
                               /* JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                movie.setGenre(genre);
*/
                                // adding movie to movies array
                                eventList.add(event);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        myFragmentView = inflater.inflate(R.layout.fragment_b, container, false);

        listView = (ListView) myFragmentView.findViewById(R.id.list);
    //   adapter = new CustomListAdapter(getActivity(), eventList);
     //   listView.setAdapter(adapter);

        return myFragmentView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
























/*

  private List<ListViewItem> mItems;
    private GetEvents getEvents = new GetEvents();

    private String picture1, name1, city1, date1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // initialize the items list
        mItems = new ArrayList<ListViewItem>();
        Resources resources = getResources();


        picture1 = getEvents.getArray(0);
        name1 = getEvents.getArray(1);
        city1 = getEvents.getArray(2);
        date1 = getEvents.getArray(3);


        int x=5;

        while(x > 0) {
            x--;
            mItems.add(new ListViewItem(picture1, name1, city1, date1));

   mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_launcher), getString(R.string.hello_world), getString(R.string.hello_world), getString(R.string.hello_world), getString(R.string.hello_world)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_launcher), getString(R.string.hello_world), getString(R.string.hello_world), getString(R.string.hello_world), getString(R.string.hello_world)));
        mItems.add(new ListViewItem(resources.getDrawable(R.drawable.ic_launcher), getString(R.string.hello_world), getString(R.string.hello_world), getString(R.string.hello_world), getString(R.string.hello_world)));

            // initialize and set the list adapter
        }
        setListAdapter(new ListViewAdapter(getActivity(), mItems));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        ListViewItem item = mItems.get(position);
        // do something
        Toast.makeText(getActivity(), item.name, Toast.LENGTH_SHORT).show();
    }

}


*/
}