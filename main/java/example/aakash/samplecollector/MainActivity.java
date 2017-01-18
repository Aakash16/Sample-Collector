package example.aakash.samplecollector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button ins, proj, whowe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        whowe = (Button) findViewById(R.id.who);
        ins = (Button) findViewById(R.id.instr);
        proj = (Button) findViewById(R.id.proj);

        whowe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, WhoWeR.class));

            }
        });

        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Instructions.class));

            }
        });

        proj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Projects.class));

            }
        });
    }
}
