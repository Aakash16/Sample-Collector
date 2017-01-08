package example.aakash.samplecollector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Dellpc on 09-Jan-17.
 */

public class NewProject extends Activity {

    String name, desc, date, location;
    int samples = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newproject);

        getInfo();

    }

    private void getInfo() {


        LayoutInflater li = LayoutInflater.from(NewProject.this);
        View promptsView = li.inflate(R.layout.getinfo, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                NewProject.this);

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
                                //  samples = Integer.parseInt(nosamp.getText().toString());
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
}
