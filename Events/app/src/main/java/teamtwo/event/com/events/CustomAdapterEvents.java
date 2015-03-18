package teamtwo.event.com.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Primož Pesjak on 18.3.2015.
 */
public class CustomAdapterEvents  extends BaseAdapter {
    private static ArrayList<SearchResult> searchArrayList;

    private LayoutInflater mInflater;

    public CustomAdapterEvents(FragmentB context, ArrayList<SearchResult> results) {

        Context context1= context.getActivity();
        searchArrayList = results;
        mInflater = LayoutInflater.from(context1);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.events_row_view, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.tvName);
            holder.txtDate = (TextView) convertView.findViewById(R.id.tvDate);
            holder.txtCity = (TextView) convertView.findViewById(R.id.tvCity);
            holder.txtType = (TextView) convertView.findViewById(R.id.tvType);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(searchArrayList.get(position).getName());
        holder.txtDate.setText(searchArrayList.get(position).getDate());
        holder.txtCity.setText(searchArrayList.get(position).getCity());
        holder.txtType.setText(searchArrayList.get(position).getType());

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtDate;
        TextView txtCity;
        TextView txtType;
    }
}