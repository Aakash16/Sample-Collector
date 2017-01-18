package example.aakash.samplecollector;


import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<ProjectFormat> {
    // Declare Variables
    Context context;
    LayoutInflater inflater;
    List<ProjectFormat> projectlist;
    private SparseBooleanArray mSelectedItemsIds;

    public ListViewAdapter(Context context, int resourceId,
                           List<ProjectFormat> projlist) {
        super(context, resourceId, projlist);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.projectlist = projlist;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView Name;
        TextView Date;
        TextView Locatn;

    }

    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listitem, null);
            // Locate the TextViews in listview_item.xml
            holder.Name = (TextView) view.findViewById(R.id.tvname);
            holder.Date = (TextView) view.findViewById(R.id.tvdate);
            holder.Locatn = (TextView) view.findViewById(R.id.tvloc);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Capture position and set to the TextViews
        holder.Name.setText(projectlist.get(position).getName());
        holder.Date.setText(projectlist.get(position).getDate());
        holder.Locatn.setText(projectlist.get(position).getLocation());
        //    holder.resp.setText(codelist.get(position).getResponse());
        return view;
    }

    @Override
    public void remove(ProjectFormat object) {
        projectlist.remove(object);
        notifyDataSetChanged();
    }

    public List<ProjectFormat> projlist() {
        return projectlist;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}