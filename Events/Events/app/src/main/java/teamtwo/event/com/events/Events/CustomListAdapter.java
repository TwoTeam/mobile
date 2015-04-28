package teamtwo.event.com.events.Events;

/**
 * Created by Primož Pesjak on 27.3.2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import teamtwo.event.com.events.Events.Event;
import teamtwo.event.com.events.R;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Event> eventItems;
    private ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Event> eventItems) {
        this.activity = activity;
        this.eventItems = eventItems;
    }

    @Override
    public int getCount() {
        return eventItems.size();
    }

    @Override
    public Object getItem(int location) {
        return eventItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView picture_url = (NetworkImageView) convertView
                .findViewById(R.id.picture);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView city = (TextView) convertView.findViewById(R.id.city);
       // TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView date = (TextView) convertView.findViewById(R.id.date);

        TextView id = (TextView) convertView.findViewById(R.id.id);

        TextView description = (TextView) convertView.findViewById(R.id.description);

        //description.setHeight(50);


        //description.setMaxHeight(500);


        // getting movie data for the row
        Event m = eventItems.get(position);

        // thumbnail image
        picture_url.setImageUrl(m.getPicture_url(), imageLoader);

        // title
        name.setText(m.getTitle());

        // rating
        city.setText("City: " + String.valueOf(m.getCity()));

        // genre
        /*String genreStr = "";
        for (String str : m.getGenre()) {
            genreStr += str + ", ";
        }
        genreStr = genreStr.length() > 0 ? genreStr.substring(0,
                genreStr.length() - 2) : genreStr;
        genre.setText(genreStr);*/

        // release year
        date.setText(String.valueOf(m.getDate()));

        id.setText(String.valueOf(m.getID()));

        description.setText(String.valueOf(m.getDescription()));

        if (position%2 == 0) {
            convertView.setBackgroundResource(R.drawable.abc_list_pressed_holo_light);
        } else {
            convertView.setBackgroundResource(R.drawable.abc_ab_share_pack_holo_light);
        }



        return convertView;
    }

}