package teamtwo.event.com.events;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import teamtwo.event.com.events.Events.AppController;
import teamtwo.event.com.events.Events.CustomListAdapter;
import teamtwo.event.com.events.Events.Event;


public class FragmentB extends android.support.v4.app.Fragment  {


   // private SwipeRefreshLayout swipeContainer;
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Events json url
    private String url = "http://veligovsek.si/events/apis/facebook_events.php?city=Velenje"; //?city=Celje //static final
    private ProgressDialog pDialog;
    private List<Event> eventList = new ArrayList<Event>();
    private ListView listView;
    private CustomListAdapter adapter;
    private View myFragmentView;

/////

    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();

///////

    public static FragmentB newInstance(int page) {
        Bundle args = new Bundle();
        FragmentB fragment = new FragmentB();
        fragment.setArguments(args);
        return fragment;
    }


    String jname="";
public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

   // listView = (ListView) myFragmentView.findViewById(R.id.list);

    SharedPreferences preferences = this.getActivity().getSharedPreferences("ACC", Context.MODE_PRIVATE);
    jname =preferences.getString("name","");

    List<String> spinnerArray = new ArrayList<String>();
    spinnerArray.add("Ljubljana");
    spinnerArray.add("Velenje");
    spinnerArray.add("Celje");
    spinnerArray.add("Koper");
    spinnerArray.add("Dobrna");
    spinnerArray.add("Maribor");
    spinnerArray.add("Kranj");


    ArrayAdapter<String> adapterspineer = new ArrayAdapter<String>(
            getActivity(), android.R.layout.simple_spinner_item, spinnerArray);

    adapterspineer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    final Spinner sItems = (Spinner) myFragmentView.findViewById(R.id.sCity);
    sItems.setAdapter(adapterspineer);

    final String selected = sItems.getSelectedItem().toString();


    sItems.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {


                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                 //   url = "http://veligovsek.si/events/apis/facebook_events.php?city="+sItems.getSelectedItem().toString()+"&user="+jname;   //pošlje mesto naprej  GET zaenkrat

                    handler.post(refreshing); //da refresha page
                   // request();

                    url = "http://veligovsek.si/events/apis/facebook_events.php?city="+sItems.getSelectedItem().toString()+"&user="+jname;   //pošlje mesto naprej  GET zaenkrat


                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {


                }
            }
    );


    url = "http://veligovsek.si/events/apis/facebook_events.php?city="+selected+"&user="+jname;
    //Toast.makeText(getActivity(),selected, Toast.LENGTH_SHORT);
    swipeRefreshLayout = (SwipeRefreshLayout) myFragmentView.findViewById(R.id.swipe_container);
    // The refreshListner is called when the layout is pulled down.
    swipeRefreshLayout.setOnRefreshListener(
            new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    /**
                     * Get the new data from you data source.
                     * The swipeRefreshLayout needs to be notified when the data is returned
                     * in order for it to stop the animation.
                     **/

                    handler.post(refreshing);
                }
            });

    swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);


    listView.setOnScrollListener(new AbsListView.OnScrollListener() {


        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            boolean enable = false;

            /**
             * This enables us to force the layout to refresh only when the first item
             * of the list is visible.
             **/
            if (listView != null && listView.getChildCount() > 0) {
                // check if the first item of the list is visible
                boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                // check if the top of the first item is visible
                boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                // enabling or disabling the refresh layout
                enable = firstItemVisible && topOfFirstItemVisible;
            }
            swipeRefreshLayout.setEnabled(enable);
        }
    });


    /*swipeContainer = (SwipeRefreshLayout)myFragmentView.findViewById(R.id.swipeContainer);

    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {

            adapter = new CustomListAdapter(getActivity(), eventList);
            eventList.clear(); ///da se ne DUPLICIRA
            listView.setAdapter(adapter);


            request();


            swipeContainer.setRefreshing(false);

        }
    });*/


/*
    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);
*/


  listView = (ListView) myFragmentView.findViewById(R.id.list);
    if (adapter == null) {
        adapter = new CustomListAdapter(getActivity(), eventList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }


    request();
    handler.post(refreshing);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView selectedDescription = (TextView)view.findViewById(R.id.description);
            if(selectedDescription.getMaxHeight() == 500) {
                selectedDescription.setMaxHeight(100);
            }
            else {
                selectedDescription.setMaxHeight(500);
            }

        }
    });
}




    String idEvent;
    //naredi context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
           // AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        AdapterView.AdapterContextMenuInfo info =  (AdapterView.AdapterContextMenuInfo)menuInfo;

            int position = info.position;

        NetworkImageView selectedPicture = ((NetworkImageView)info.targetView.findViewById(R.id.picture));
        String selectedWord = ((TextView)info.targetView.findViewById(R.id.name)).getText().toString();
        String selectedCity = ((TextView)info.targetView.findViewById(R.id.city)).getText().toString();
        String selectedDate = ((TextView)info.targetView.findViewById(R.id.date)).getText().toString();
        String selectedDescription = ((TextView)info.targetView.findViewById(R.id.description)).getText().toString();

        idEvent = ((TextView)info.targetView.findViewById(R.id.id)).getText().toString();
            menu.setHeaderTitle(selectedWord);



          //  int position = info.position; //pozicija

            //String selectedTitle = ((TextView)(v.findViewById(R.id.name))).getText().toString();  //dobi naslov



            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }




    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();



     //   String[] menuItems = getResources().getStringArray(R.array.menu);
       // String menuItemName = menuItems[menuItemIndex];

        int menuItemIndex = item.getItemId();
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[menuItemIndex];



        // String selectedID = ((TextView)info.targetView.findViewById(R.id.id)).getText().toString();


        if(menuItemIndex == 0) { //FAV

            _(menuItemName);

            url = "http://veligovsek.si/events/apis/favourite.php?event="+idEvent+"&user="+jname;

            _(url);


        }
        else if(menuItemIndex == 1) { //HIDE
                _(menuItemName);


            url = "http://veligovsek.si/events/apis/hide.php?event="+idEvent+"&user="+jname;

            _(url);



        }


/*
        Toast.makeText(myFragmentView.getContext(),menuItemName,Toast.LENGTH_LONG);
*/

        //  url = "http://veligovsek.si/events/apis/favourite.php?event="+selectedID+"&user="+jname;



        final int position = info.position; //mam INDEX tiste pozicije
        // _(position);



        return true;
    }




/*

    pDialog = new ProgressDialog(getActivity());
    // Showing progress dialog before making http request
    pDialog.setMessage("Loading...");
    pDialog.show();
*/





/*


    JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                       // hidePDialog();

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
                               */
/* JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                movie.setGenre(genre);
*//*

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
               // hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(movieReq);


*/


 //   }



    private final Runnable refreshing = new Runnable(){
        public void run(){
            try {

/*                if(isRefreshing()){
                    // RE-Run after 1 Second
                    handler.postDelayed(this, 1000);
                }else*/

                    // Stop the animation once we are done fetching data.
                    swipeRefreshLayout.setRefreshing(false);

                    adapter = new CustomListAdapter(getActivity(), eventList);
                    eventList.clear(); ///da se ne DUPLICIRA
                    listView.setAdapter(adapter);


                    request();

                    /**
                     * You can add code to update your list with the new data.
                     **/

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void request()
    {

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

                                event.setTitle(obj.getString("title"));
                                event.setPicture_url(obj.getString("image"));
                                event.setCity(obj.getString("city"));
                                event.setDate(obj.getString("start"));
                                event.setID(obj.getString("id"));
                                event.setDescription(obj.getString("description"));
/*                                event.setTitle(obj.getString("name"));
                                event.setPicture_url(obj.getString("picture_url"));
                                event.setCity(obj.getString("city"));
                                event.setDate(obj.getString("date"));*/

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

    private void _(String s)  //pokaže v logcatu za TESTINNG :P
    {
        Log.d("MyApp", "LoginActivity" + "#######"+ s);
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