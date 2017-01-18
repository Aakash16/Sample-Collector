package example.aakash.samplecollector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Dellpc on 09-Jan-17.
 */

public class History extends Activity {

    String name, desc, date, location;
    int samples = 0;

    ProjectFormat proj_edit;
    boolean isedit;

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
        listView.setAdapter(listviewadapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                Log.d(TAG, "Item clicked: " + position);

                ProjectFormat proj = listviewadapter
                        .getItem(position);

                Intent intent = new Intent(History.this, HistoryItem.class);
                // intent.putExtra("")
                startActivity(intent);
            }
        });

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                listviewadapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.delete:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = listviewadapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                ProjectFormat selecteditem = listviewadapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                listviewadapter.remove(selecteditem);
                                db.deleteCodeFormat(selecteditem);
                            }
                        }

                        mode.finish();
                        return true;

                  /*  case edit:
                        SparseBooleanArray selected1 = listviewadapter
                                .getSelectedIds();

                        if (selected1.size() == 1) {

                            proj_edit = listviewadapter
                                    .getItem(selected1.keyAt(0));
                            isedit = true;
                            getInfo();

                            return true;
                        } else
                            Toast.makeText(getApplicationContext(), "Select only 1 item for edit", Toast.LENGTH_SHORT).show();

                        return false;

                 */
                    default:
                        return false;
                }
            }


            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.select_menu, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                listviewadapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });


        // Binds the Adapter to the ListView
        listView.setAdapter(listviewadapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

    }

    private void refreshList() {

        CustomListViewValuesArr.clear();
        setListData();
        listviewadapter.notifyDataSetChanged();
    }

    public void setListData() {


        List<ProjectFormat> contacts = db.getAllprojFormats();

        for (ProjectFormat cn : contacts) {
            String log = "Name: " + cn.getName() + ", Date: " + cn.getDate() + ", desc: " +
                    cn.getDescription() + ", samples: " + cn.getSamples() + ", Locatn: " + cn.getLocation();
            CustomListViewValuesArr.add(cn);

            listviewadapter.notifyDataSetChanged();
            Log.d("Record", log);
        }


    }

    private void getInfo() {


        LayoutInflater li = LayoutInflater.from(History.this);
        View promptsView = li.inflate(R.layout.getinfo, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                History.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText nme = (EditText) promptsView
                .findViewById(R.id.et_name);
        final EditText dat = (EditText) promptsView
                .findViewById(R.id.et_date);
        final EditText loc = (EditText) promptsView
                .findViewById(R.id.et_loc);
        final EditText nosamp = (EditText) promptsView
                .findViewById(R.id.et_num);
        final EditText descr = (EditText) promptsView
                .findViewById(R.id.et_decsr);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                name = nme.getText().toString();
                                desc = descr.getText().toString();
                                location = loc.getText().toString();
                                date = dat.getText().toString();

                                samples = Integer.parseInt(nosamp.getText().toString());
                                ProjectFormat p = new ProjectFormat(name, desc, date, location, samples);
                                db.addCodeFormat(p);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
