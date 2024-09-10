package ca.yorku.eecs.mack.mainlistviewscroll;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.usage.UsageEvents;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter {
    Context con;
    String[] data;
    String randomNum, Scroll, Group, timeStamp;
    double startTime, correctTime, errorRate;
    public boolean done;
    int counter, errorCounter, totalCounter;


    //get data from the main activity
    public CustomAdapter(Context context, String[] data, String num, int trialCounter, String Scroll, String Group, String timeStamp) {
        this.con = context;

        this.data = data;
        this.randomNum = num;
        this.startTime = System.currentTimeMillis();
        this.done = false;
        this.counter = trialCounter;
        this.errorCounter = 0;
        this.Scroll = Scroll;
        this.Group = Group;
        this.totalCounter = 0;
        this.errorRate = 0.0;
        this.timeStamp = timeStamp;

    }

    @Override
    public int getCount() {
        return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
        //this method will be called for every item of your listview
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listrow, parent, false);
            final TextView text = (TextView) convertView.findViewById(R.id.number); //recognize your view like this
            Button button = (Button) convertView.findViewById(R.id.toggle_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    totalCounter++;
                    if (text.getText() == randomNum) {
                        done = true;
                        Log.i("counter", "counter: " + counter);
                        Toast.makeText(con, "You picked the correct number", Toast.LENGTH_LONG).show();
                        correctTime = System.currentTimeMillis() - startTime;
                        correctTime = (Math.round((correctTime / 1000.0) * 100.0) / 100.0);
                        errorRate = (((errorCounter * 1.0) / (totalCounter * 1.0)) * 100.0);
                        errorRate = (Math.round((errorRate * 100.0)) / 100.0);
                        Toast.makeText(con, "time taken: " + correctTime + " seconds", Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(con);

                        String msg = "Congratulations!" + "\n" + "You found the correct number" + "\n" + "Time Taken: " + correctTime + " seconds" + "\nError Rate: " + errorRate + "%";

                        //              3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
                        final AlertDialog dialog = builder.create();
                        dialog.show();

                        //Write the data for each scroll in the text file
                        String topRow = "Trial Number, Group Number, Scroll Type, Time Taken, Error Rate\n";
                        String nextRows = counter + ", " + Group + ", " + Scroll + ", " + correctTime + ", " + errorRate + "\n";
                        File direct = new File(Environment.getExternalStorageDirectory() + "/TrialFolder");

                        if (!direct.exists()) {
                            if (direct.mkdir()) ; //directory is created;
                        }

                        File root = new File(direct.getAbsolutePath());
                        File FilePath = new File(root, Group + "_" + Scroll + "_" + timeStamp + ".txt");
                        if (!FilePath.exists()) {
                            FilePath = new File(root, Group + "_" + Scroll + "_" + timeStamp + ".txt");
                        }

                        try {
                            FileWriter fr = new FileWriter(FilePath, true);
                            if (FilePath.length() == 0) {
                                fr.append(topRow);
                            }
                            BufferedWriter br = new BufferedWriter(fr);
                            br.append(nextRows);

                            br.flush();
                            br.close();
                            fr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        builder.setMessage(msg)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.cancel();
                                    }
                                })
                                .setTitle("Trial Summary").show();
                        if (counter < 5) {
                            ((MainListViewActivity) con).randGen();
                        } else {
                            Intent in = new Intent(con, ScrollSetup.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            con.startActivity(in);
                        }

                    } else {
                        errorCounter++;
                    }
                }
            });
            text.setText(data[position]);
            return convertView;
        }
}