
package example.aakash.samplecollector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/*
 * Created by Dellpc on 09-Jan-17.
 */

public class NewProject extends Activity {

    String name, desc, date, location;
    int samples = 2;
    DatabaseHandler db;
    private TextView textViewShowTime1, textViewShowTime2, textViewShowTime3;
    private CountDownTimer countDownTimer1, countDownTimer2, countDownTimer3;
    private long totalTimeCountInMilliseconds1, totalTimeCountInMilliseconds2, totalTimeCountInMilliseconds3;

    private long timeBlinkInMilliseconds = 5 * 1000;
    private boolean blink1, blink2, blink3;

    TextView nm, loc, dat, tvrd1, tvrd2, tvrd3;
    Button meas;
    boolean isPause;
    ArrayList<Float> readList1, readList2, readList3;

    float reading;
    LinearLayout l1, l2, l3;
    int[] lvis = {View.GONE, View.GONE, View.GONE};

    File myFile, myFile2, myFile3;
    FileOutputStream fOut, fOut2, fOut3;
    OutputStreamWriter myOutWriter, myOutWriter2, myOutWriter3;

    Handler handler,handler2;
    Runnable runn1, runn2, runn3;
    int counter1 = 0, counter2 = 0, counter3 = 0;
    int offsetMin = 5;
    //int[] minlist = {0, 2, 2, 2, 2, 2, 5, 5, 10, 10, 10, 10, 30, 30, 30, 30, 30, 30};
    int[] minlist = {14, 14, 14};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newproject);
        db = new DatabaseHandler(this);
        meas = (Button) findViewById(R.id.bmeas);
        textViewShowTime1 = (TextView) findViewById(R.id.tvTimeCount1);
        textViewShowTime2 = (TextView) findViewById(R.id.tvTimeCount2);
        textViewShowTime3 = (TextView) findViewById(R.id.tvTimeCount3);

        nm = (TextView) findViewById(R.id.tv_name);
        dat = (TextView) findViewById(R.id.tv_date);
        loc = (TextView) findViewById(R.id.tv_locn);
        tvrd1 = (TextView) findViewById(R.id.tv_1);
        tvrd2 = (TextView) findViewById(R.id.tv_2);
        tvrd3 = (TextView) findViewById(R.id.tv_3);


        handler = new Handler();
        runn1 = new Runnable() {
            @Override
            public void run() {

                if (counter2 == 0 && samples > 1) {
                    Toast.makeText(getApplicationContext(), "Timer 2 started", Toast.LENGTH_SHORT).show();
                    textViewShowTime2.setVisibility(View.VISIBLE);
                    handler2.postDelayed(runn2, offsetMin * 1000);
                }
                setTimer(minlist[counter1]);
                startTimer1();

            }
        };
        handler2 = new Handler();
        runn2 = new Runnable() {
            @Override
            public void run() {

                if (counter3 == 0 && samples == 3) {
                    Toast.makeText(getApplicationContext(), "Timer 3 started", Toast.LENGTH_SHORT).show();
                    textViewShowTime3.setVisibility(View.VISIBLE);
                    handler.postDelayed(runn3, offsetMin * 1000);
                }

                setTimer2(minlist[counter2]);
                startTimer2();

            }
        };

        runn3 = new Runnable() {
            @Override
            public void run() {

                setTimer3(minlist[counter3]);
                startTimer3();

            }
        };


        meas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isPause) {
                    if (Build.VERSION.SDK_INT < 23) {
                        textViewShowTime1.setTextAppearance(getApplicationContext(),
                                R.style.normalText);
                    } else {
                        textViewShowTime1.setTextAppearance(
                                R.style.normalText);
                    }


                    handler.post(runn1);
                    textViewShowTime1.setVisibility(View.VISIBLE);
                    LinearLayout ll = (LinearLayout) findViewById(R.id.lay1);
                    ll.setVisibility(View.VISIBLE);


                } else {
                    // countDownTimer.

                }
                meas.setClickable(false);
            }
        });
        createFile();

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
                .setCancelable(true)
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

                                if (samples == 1) {
                                    readList1 = new ArrayList<Float>();
                                    lvis[0] = View.VISIBLE;
                                    LinearLayout l1 = (LinearLayout) findViewById(R.id.layv1);
                                    l1.setVisibility(View.VISIBLE);
                                } else if (samples == 2) {
                                    readList1 = new ArrayList<Float>();
                                    readList2 = new ArrayList<Float>();
                                    lvis[0] = View.VISIBLE;
                                    lvis[1] = View.VISIBLE;

                                    LinearLayout l1 = (LinearLayout) findViewById(R.id.layv1);
                                    l1.setVisibility(View.VISIBLE);
                                    View v = (View) findViewById(R.id.vv1);
                                    v.setVisibility(View.VISIBLE);
                                    LinearLayout l2 = (LinearLayout) findViewById(R.id.layv2);
                                    l2.setVisibility(View.VISIBLE);
                                } else if (samples == 3) {
                                    readList1 = new ArrayList<Float>();
                                    readList2 = new ArrayList<Float>();
                                    readList3 = new ArrayList<Float>();

                                    View v = (View) findViewById(R.id.vv1);
                                    v.setVisibility(View.VISIBLE);
                                    View v2 = (View) findViewById(R.id.vv2);
                                    v2.setVisibility(View.VISIBLE);
                                    LinearLayout l1 = (LinearLayout) findViewById(R.id.layv1);
                                    l1.setVisibility(View.VISIBLE);
                                    LinearLayout l2 = (LinearLayout) findViewById(R.id.layv2);
                                    l2.setVisibility(View.VISIBLE);
                                    LinearLayout l3 = (LinearLayout) findViewById(R.id.layv3);
                                    l3.setVisibility(View.VISIBLE);

                                }
                                nm.setText(name);
                                dat.setText(date);
                                loc.setText(location);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    private void getMeasure(final int seq) {


        LayoutInflater li = LayoutInflater.from(NewProject.this);
        View promptsView = li.inflate(R.layout.reading, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                NewProject.this);
        alertDialogBuilder.setView(promptsView);

        TextView tv = (TextView) promptsView.findViewById(R.id.textViewS);
        if (seq == 1) {
            tv.setText("Reading Value 1: ");
        }
        if (seq == 2) {
            tv.setText("Reading Value 2: ");
        }
        if (seq == 3) {
            tv.setText("Reading Value 3: ");
        }
        final EditText val = (EditText) promptsView.findViewById(R.id.et_val);
        val.requestFocus();
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                String st = val.getText().toString();
                                if (st != null && !st.equals(""))
                                    reading = Float.parseFloat(st);

                                if (seq == 1) {

                                    if (readList1 == null) {
                                        readList1 = new ArrayList<Float>();
                                    }
                                    readList1.add(reading);
                                    Log.d("Read", " val1: " + reading);
                                    tvrd1.setTypeface(Typeface.DEFAULT);
                                    tvrd1.append("" + reading + "\n");
                                    save(reading, 1);


                                }
                                if (seq == 2) {

                                    if (readList2 == null) {
                                        readList2 = new ArrayList<Float>();
                                    }
                                    readList2.add(reading);
                                    Log.d("Read", " val2: " + reading);
                                    tvrd2.setTypeface(Typeface.DEFAULT);
                                    tvrd2.append("" + reading + "\n");
                                    save(reading, 2);

                                }

                                if (seq == 3) {

                                    if (readList3 == null) {
                                        readList3 = new ArrayList<Float>();
                                    }
                                    readList3.add(reading);
                                    Log.d("Read", " val3: " + reading);
                                    tvrd3.setTypeface(Typeface.DEFAULT);
                                    tvrd3.append("" + reading + "\n");
                                    save(reading, 3);

                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        //    alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();


    }

    private void save(float val, int seq) {

        try {

            switch (seq) {
                case 1:
                    if (myOutWriter != null) {
                        myOutWriter.append(val + "\n");
                        Log.d("File", " writting 1: " + val + " count val: " + counter1);
                    }
                    if (counter1 == minlist.length - 1)
                        closeFile1();

                    break;
                case 2:
                    if (myOutWriter2 != null) {
                        myOutWriter2.append(val + "\n");
                        Log.d("File", " writting 2: " + val);
                    }
                    if (counter2 == minlist.length - 1)
                        closeFile2();
                    break;
                case 3:
                    if (myOutWriter3 != null) {
                        myOutWriter3.append(val + "\n");
                        Log.d("File", " writting 3: " + val);
                    }
                    if (counter3 == minlist.length - 1)
                        closeFile3();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createFile() {
        try {

            File fold = new File("/sdcard/Sample Collector/");
            if (!fold.exists()) {
                Log.d("Main", "created folder: " + fold.mkdir());

            }
            myFile = new File(fold.getAbsoluteFile() + "/" + "Measurer_1" + ".csv");
            Log.d("File", "created file 1: " + myFile.createNewFile());
            fOut = new FileOutputStream(myFile);
            myOutWriter =
                    new OutputStreamWriter(fOut);


            Log.d("File", "file created");
            Toast.makeText(getBaseContext(), "Measurer 1 recording in file", Toast.LENGTH_SHORT).show();

            if (samples == 2) {
                myFile2 = new File(fold.getAbsoluteFile() + "/" + "Measurer_2" + ".csv");
                Log.d("File", "created file 1: " + myFile2.createNewFile());
                fOut2 = new FileOutputStream(myFile2);
                myOutWriter2 =
                        new OutputStreamWriter(fOut2);


                Log.d("File", "file created 2");
                Toast.makeText(getBaseContext(), "Measurer 2 recording in file", Toast.LENGTH_SHORT).show();

            }

            if (samples == 3) {
                myFile2 = new File(fold.getAbsoluteFile() + "/" + "Measurer_2" + ".csv");
                Log.d("File", "created file 2: " + myFile2.createNewFile());
                fOut2 = new FileOutputStream(myFile2);
                myOutWriter2 =
                        new OutputStreamWriter(fOut2);


                Log.d("File", "file created 2");
                Toast.makeText(getBaseContext(), "Measurer 2 recording in file", Toast.LENGTH_SHORT).show();


                myFile3 = new File(fold.getAbsoluteFile() + "/" + "Measurer_3" + ".csv");
                Log.d("File", "created file 3: " + myFile3.createNewFile());
                fOut3 = new FileOutputStream(myFile3);
                myOutWriter3 =
                        new OutputStreamWriter(fOut3);


                Log.d("File", "file created 3");
                Toast.makeText(getBaseContext(), "Measurer 3 recording in file", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private void closeFile1() {

        if (myOutWriter != null && fOut != null) {
            try {
                myOutWriter.close();
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(getBaseContext(),
                    "Measurer 1 records successfully written",
                    Toast.LENGTH_SHORT).show();

            Log.d("Main", "Rec stopped 1");

        }


    }

    private void closeFile2() {

        if (myOutWriter2 != null && fOut2 != null) {
            try {
                myOutWriter2.close();
                fOut2.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(getBaseContext(),
                    "Measurer 2 records successfully written",
                    Toast.LENGTH_SHORT).show();

            Log.d("Main", "Rec stopped 2");

        }

    }

    private void closeFile3() {

        if (myOutWriter3 != null && fOut3 != null) {
            try {
                myOutWriter3.close();
                fOut3.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(getBaseContext(),
                    "Measurer 3 records successfully written",
                    Toast.LENGTH_SHORT).show();
            Log.d("Main", "Rec stopped 3");

        }
    }

    private void setTimer(int time) {

        totalTimeCountInMilliseconds1 = time * 1000;
     //   Log.d("Timer", "milis setup : " + totalTimeCountInMilliseconds1);

    }

    private void setTimer2(int time) {

        totalTimeCountInMilliseconds2 = time * 1000;

     //   Log.d("Timer", "milis setup : " + totalTimeCountInMilliseconds2);

    }

    private void setTimer3(int time) {

        totalTimeCountInMilliseconds3 = time * 1000;
     //   Log.d("Timer", "milis setup : " + totalTimeCountInMilliseconds3);

    }


    private void startTimer1() {

      //  Log.d("Timer", " Started timer 1");
        countDownTimer1 = new CountDownTimer(totalTimeCountInMilliseconds1, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds


            @Override
            public void onTick(long leftTimeInMilliseconds) {

                //   Log.d("Timer", "ticking");
                long seconds = leftTimeInMilliseconds / 1000;

                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    textViewShowTime1.setTextAppearance(getApplicationContext(),
                            R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style

                    if (blink1) {
                        textViewShowTime1.setVisibility(View.VISIBLE);

                        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 100);
                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime1.setVisibility(View.INVISIBLE);
                        Vibrator v = (Vibrator) getApplication().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(500);
                    }

                    blink1 = !blink1; // toggle the value of blink
                }

                textViewShowTime1.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                Log.d("Timer", "timer1 finish: " + counter1);

                textViewShowTime1.setVisibility(View.VISIBLE);

                if (counter1 == minlist.length) {
                    textViewShowTime1.setText("Measurer 1 Finished!");
                    textViewShowTime1.setTextColor(getResources().getColor(R.color.DarkGreen));

                }
                if (counter1 < minlist.length) {
                    counter1++;
                    handler.post(runn1);
                    textViewShowTime1.setTextColor(Color.BLACK);

                  //  getMeasure(1);

                }
            }

        }.start();

    }

    private void startTimer2() {

      //  Log.d("Timer", " Started timer 2");
        countDownTimer2 = new CountDownTimer(totalTimeCountInMilliseconds2, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds


            @Override
            public void onTick(long leftTimeInMilliseconds) {

                //   Log.d("Timer", "ticking");
                long seconds = leftTimeInMilliseconds / 1000;

                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    textViewShowTime2.setTextAppearance(getApplicationContext(),
                            R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style

                    if (blink2) {
                        textViewShowTime2.setVisibility(View.VISIBLE);

                        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 100);
                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime2.setVisibility(View.INVISIBLE);
                        Vibrator v = (Vibrator) getApplication().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(500);
                    }

                    blink2 = !blink2; // toggle the value of blink
                }

                textViewShowTime2.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                Log.d("Timer", "timer2 finish: " + counter2);

                textViewShowTime2.setVisibility(View.VISIBLE);

                if (counter2 == minlist.length) {
                    textViewShowTime2.setText("Measurer 2 Finished!");
                    textViewShowTime2.setTextColor(getResources().getColor(R.color.DarkGreen));

                }
                if (counter2 < minlist.length) {
                    counter2++;
                    handler2.post(runn2);
                    textViewShowTime2.setTextColor(Color.BLACK);

                   // getMeasure(2);

                }
            }

        }.start();

    }

    private void startTimer3() {

      //  Log.d("Timer", " Started timer 3");
        countDownTimer3 = new CountDownTimer(totalTimeCountInMilliseconds3, 1000) {
            // 500 means, onTick function will be called at every 500
            // milliseconds


            @Override
            public void onTick(long leftTimeInMilliseconds) {

                //   Log.d("Timer", "ticking");
                long seconds = leftTimeInMilliseconds / 1000;

                if (leftTimeInMilliseconds < timeBlinkInMilliseconds) {
                    textViewShowTime3.setTextAppearance(getApplicationContext(),
                            R.style.blinkText);
                    // change the style of the textview .. giving a red
                    // alert style

                    if (blink3) {
                        textViewShowTime3.setVisibility(View.VISIBLE);

                        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 100);
                        // if blink is true, textview will be visible
                    } else {
                        textViewShowTime3.setVisibility(View.INVISIBLE);
                        Vibrator v = (Vibrator) getApplication().getSystemService(Context.VIBRATOR_SERVICE);
                        // Vibrate for 500 milliseconds
                        v.vibrate(500);
                    }

                    blink3 = !blink3; // toggle the value of blink
                }

                textViewShowTime3.setText(String.format("%02d", seconds / 60)
                        + ":" + String.format("%02d", seconds % 60));

            }

            @Override
            public void onFinish() {
                // this function will be called when the timecount is finished
                Log.d("Timer", "timer3 finish: " + counter3);

                textViewShowTime3.setVisibility(View.VISIBLE);

                if (counter3 == minlist.length) {
                    textViewShowTime3.setText("Measurer 3 Finished!");
                    textViewShowTime3.setTextColor(getResources().getColor(R.color.DarkGreen));
                }
                if (counter3 < minlist.length) {
                    handler.post(runn3);
                    textViewShowTime3.setTextColor(Color.BLACK);
                }
                getMeasure(3);
                counter3++;
            }

        }.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer3 != null) {
            countDownTimer3.cancel();
        }
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
        }
        if (countDownTimer1 != null) {
            countDownTimer1.cancel();
        }
        db.close();
    }
}
