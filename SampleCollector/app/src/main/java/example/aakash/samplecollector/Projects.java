package example.aakash.samplecollector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Dellpc on 09-Jan-17.
 */

public class Projects extends Activity {

    Button newp, histr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projects);

        histr = (Button) findViewById(R.id.hist);
        newp = (Button) findViewById(R.id.newproj);

        histr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Projects.this, History.class));

            }
        });

        newp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Projects.this, NewProject.class));

            }
        });
    }
}
