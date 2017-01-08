package example.aakash.samplecollector;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dellpc on 09-Jan-17.
 */

public class History extends Activity {

    DatabaseHandler db;
    ListViewAdapter listviewadapter;
    ListView listView;
    private ArrayList<ProjectFormat> CustomListViewValuesArr = new ArrayList<ProjectFormat>();
    public History CustomListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        listView = (ListView) findViewById(R.id.list_view);


        CustomListView = this;
        db = new DatabaseHandler(this);

        listviewadapter = new ListViewAdapter(this, R.layout.listitem,
                CustomListViewValuesArr);

        // Binds the Adapter to the ListView
        listView.setAdapter(listviewadapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

    }


    public void setListData() {


        List<ProjectFormat> contacts = db.getAllprojFormats();

        for (ProjectFormat cn : contacts) {
            //  String log = "Id: " + cn() + " ,Code: " + cn.getCode() + " ,Response: " +
            //          cn.getResponse();
            CustomListViewValuesArr.add(cn);

            // Log.d("Record: ", log);
        }


    }
}
