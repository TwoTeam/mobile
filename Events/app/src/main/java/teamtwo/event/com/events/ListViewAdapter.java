/*
package teamtwo.event.com.events;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

*/
/**
 * Created by Primož Pesjak on 25.3.2015.
 *//*

public class ListViewAdapter extends ArrayAdapter<ListViewItem> {

 */
/*   public ListViewAdapter(Context context, List<ListViewItem> items) {
        super(context, R.layout.listview_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
         //   viewHolder.tvType = (TextView) convertView.findViewById(R.id.tvType);
            viewHolder.tvCity = (TextView) convertView.findViewById(R.id.tvCity);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        ListViewItem item = getItem(position);
      // viewHolder.ivIcon.setImageDrawable(item.icon);
        viewHolder.tvName.setText(item.name);
      //  viewHolder.tvType.setText(item.type);
       viewHolder.tvCity.setText(item.city);
       viewHolder.tvDate.setText(item.date);

        return convertView;
    }

    *//*
*/
/**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * @see http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     *//*
*/
/*
    private static class ViewHolder {
        ImageView ivIcon;
        TextView tvName,tvCity,tvDate;
    }
}


*//*

///////////////*/
