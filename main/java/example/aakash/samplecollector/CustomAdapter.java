package example.aakash.samplecollector;

/**
 * Created by Dellpc on 17-Dec-16.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*********
 * Adapter class extends with BaseAdapter and implements with OnClickListener
 ************/
public class CustomAdapter extends BaseAdapter {

    /***********
     * Declare Used Variables
     *********/
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater = null;

    ProjectFormat tempValues = null;
    int i = 0;

    /*************
     * CustomAdapter Constructor
     *****************/
    public CustomAdapter(Activity a, ArrayList d) {

        /********** Take passed values **********/
        activity = a;
        data = d;


        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater) activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /********
     * What is the size of Passed Arraylist Size
     ************/
    public int getCount() {

        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {

        public TextView tname;
        public TextView tdate;
        public TextView tloc;

    }

    /******
     * Depends upon data size called for each row , Create each ListView row
     *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.listitem, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.tname = (TextView) vi.findViewById(R.id.tvname);
            holder.tdate = (TextView) vi.findViewById(R.id.tvdate);
            // holder.image=(ImageView)vi.findViewById(R.id.image);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
            holder.tname.setText("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            tempValues = null;
            tempValues = (ProjectFormat) data.get(position);

            /************  Set Model values in Holder elements ***********/

            holder.tname.setText(tempValues.getName());
            holder.tloc.setText(tempValues.getLocation());
            holder.tdate.setText(tempValues.getDate());


            /******** Set Item Click Listner for LayoutInflater for each row *******/

            //  vi.setOnClickListener(new OnItemClickListener( position ));
        }
        return vi;
    }


}