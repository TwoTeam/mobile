package teamtwo.event.com.events;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends android.support.v4.app.Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      final View view = inflater.inflate(R.layout.fragment_b,container,false);


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
        return  view;
    }

    private ArrayList<SearchResult> GetSearchResults(){
        ArrayList<SearchResult> results = new ArrayList<SearchResult>();

        SearchResult sr1 = new SearchResult();
        sr1.setName("Klub: ");
        sr1.setDate("5/10/2017");
        sr1.setCity("Velenje");
        sr1.setType("Klasika");
        results.add(sr1);

        SearchResult sr2 = new SearchResult();
        sr1.setName("Test");
        sr1.setDate("5/10/2017");
        sr1.setCity("Velenje");
        sr1.setType("Klasika");
        results.add(sr1);

        SearchResult sr3 = new SearchResult();
        sr1.setName("MAX");
        sr1.setDate("5/10/2017");
        sr1.setCity("Velenje");
        sr1.setType("Klasika");
        results.add(sr1);

        return results;
    }








}
