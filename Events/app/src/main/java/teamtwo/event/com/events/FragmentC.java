/*
package teamtwo.event.com.events;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentC extends android.support.v4.app.Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static FragmentC newInstance(int page) {
        Bundle args = new Bundle();
      //  args.putInt(ARG_PAGE, page);
        FragmentC fragment = new FragmentC();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);
    //    TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        // tvTitle.setText("Fragment " + mPage);
        return view;
    }

  */
/*  private String title;
    private int page;
    // newInstance constructor for creating fragment with arguments
    public static FragmentC newInstance(int page, String title) {
        FragmentC fragmentFirst = new FragmentC();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);*//*
*/
/*
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");*//*
*/
/*
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_c, container, false);
        return view;
    }
*//*

}
*/
