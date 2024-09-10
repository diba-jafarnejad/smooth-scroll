package ca.yorku.eecs.mack.mainlistviewscroll;

import android.app.ListActivity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * Demo_ListView_1 - demonstrates presenting an array of words in a <code>ListView</code> <p>
 *
 * Related links:
 *
 * <blockquote> API Guides: <p>
 *
 * <ul>
 *
 * <li><a href="http://developer.android.com/guide/topics/ui/layout/listview.html">List View</a>
 *
 * <li><a href="http://developer.android.com/guide/topics/ui/declaring-layout.html#AdapterViews">Building Layouts with
 * an Adapter</a>
 *
 * </ul> <p>
 *
 * API References: <p>
 *
 * <ul>
 *
 * <li><a href="http://developer.android.com/reference/android/widget/ListView.html"> <code>ListView</code></a>
 *
 * <li><a href="http://developer.android.com/reference/android/app/ListActivity.html">
 * <code>ListActivity</code></a>
 *
 * <li><a href="http://developer.android.com/reference/android/widget/ListAdapter.html"> <code>ListAdapter</code></a>
 * (interface)
 *
 * <li><a href="http://developer.android.com/reference/android/widget/Adapter.html"> <code>Adapter</code></a>
 * (interface)
 *
 * <li><a href="http://developer.android.com/reference/android/widget/BaseAdapter.html"> <code>BaseAdapter</code></a>
 * (implements ListAdapter)
 *
 * </ul> <p>
 *
 * </blockquote>
 *
 * This is the first in a series of three demos using <code>ListView</code>:
 * <p>
 *
 * <blockquote>
 * <table border="1" cellspacing="0" cellpadding="6">
 * <tr bgcolor="#cccccc" width="100">
 * <th align="center" >Demo
 * <th align="center" >Content (view class)
 * <th align="center" >Source of Content
 *
 * <tr>
 * <td>Demo_ListView_1
 * <td>Strings (<code>TextView</code>)
 * <td>Resources Array
 *
 * <tr>
 * <td>Demo_ListView_2
 * <td>Images (<code>ImageView</code>)
 * <td>Device's internal memory card
 *
 * <tr>
 * <td>Demo_ListView_3
 * <td>Images (<code>ImageView</code>)
 * <td>Internet web site
 *
 * </table>
 * </blockquote>
 * <p>
 *
 * The first demo is extremely simple: Each
 * item in the list is a <code>String</code> &ndash; a word taken from an array of 9022 words. <p>
 *
 * Here's a screen snap of the app with a partial view into the scrollable list of words: <p>
 *
 * <center><a href="DemoListView1-1.jpg"><img src="DemoListView1-1.jpg" width="300" alt="image"></a></center> <p>
 *
 * In the code, note that the main activity extends <code>ListActivity</code> (not <code>Activity</code>). As noted in
 * the API
 * Reference, a <code>ListActivity</code> is <p>
 *
 * <blockquote><i> an activity that displays a list of items by binding to a data source such as an array or
 * <code>Cursor</code>, and exposes event handlers when the user selects an item. <code>ListActivity</code> hosts a
 * <code>ListView</code> object that can be bound to different data sources, typically either an array or a
 * <code>Cursor</code> holding query results. </i> </blockquote> <p>
 *
 * The data source for this demo is an array. <p>
 *
 * The main activity includes just one method, <code>onCreate</code>. Four primary tasks are handled in
 * <code>onCreate</code>. First, we set the content view (UI) for the app to the XML layout that contains the
 * <code>ListView</code>: <p>
 *
 * <pre>
 *      setContentView(R.layout.list_view_layout);
 * </pre>
 *
 * In <code>list_view_layout.xml</code>, the <code>ListView</code> is declared: <p>
 *
 * <pre>
 *      &lt;ListView
 *         android:id="@android:id/list"
 *         android:layout_width="match_parent"
 *         android:layout_height="0dp"
 *         android:layout_weight="1"
 *         android:background="#888888"
 *         android:fastScrollEnabled="true" /&gt;
 * </pre>
 *
 * Note that providing a screen layout with a <code>ListView</code> object with the id "<code>@android:id/list</code>"
 * is required when using <code>ListActivity</code>. <p>
 *
 * The second task in <code>onCreate</code> is to get a reference to the array of words (which is included in the demo
 * as a resource):<p>
 *
 * <pre>
 *      String[] words = getResources().getStringArray(R.array.words);
 * </pre>
 *
 * Third, an adapter is created that is bound to the array of words: <p>
 *
 * <pre>
 *      NumberAdapter wa = new NumberAdapter(words);
 * </pre>
 *
 * The adapter class is <code>NumberAdapter</code> which extends <code>BaseAdapter</code> (see below). <p>
 *
 * Finally, a reference to the ListView is obtained and the <code>ListView</code> is then given the new adapter which
 * provides the content for the <code>ListView</code>: <p>
 *
 * <pre>
 *      ListView lv = (ListView)findViewById(android.R.id.list);
 *      lv.setAdapter(wa);
 * </pre>
 *
 * With this, the app is created and the UI appears with a <code>ListView</code> object populated with <code>List</code>
 * objects.  Each <code>List</code> object is an instance of <code>TextView</code> containing a string of text &ndash;
 * a word taken from the array of words.  As many words appear as will fit on the device's display.  The user many
 * scroll
 * up and down through the list in the usual way, using touch gestures. Scrolling is also possible by moving the
 * scrollbar's handle.  One added feature is "indexed scrolling", discussed below.<p>
 *
 * Our adapter is an instance of <code>NumberAdapter</code>, a custom class that extends <code>BaseAdapter</code> (which
 * extends <code>Adapter</code>). We override and implement four of the methods: <code>getCount</code>,
 * <code>getItem</code>, <code>getItemId</code>, and <code>getView</code>. <p>
 *
 * The work of actually getting the words and placing them in the list occurs in <code>getView</code>.  As the list is
 * scrolled up and down, words are retrieved as necessary, based on the "position" in the array of new words scrolling
 * into view.  The words are placed in the <code>TextView</code> objects which form the views populating the
 * <code>ListView</code>.
 * <p>
 *
 * Bear in mind that even though there are 9022 words in the array, <code>View</code> objects &ndash; actually
 * <code>TextView</code> objects &ndash; are instantiated in <code>getView</code> only as necessary to fill the device's
 * display.  As scrolling takes place, most of the words that appear are placed in <code>TextView</code> objects that
 * already exist.  Existing <code>TextView</code> objects are simply repositioned and updated to show the next word to
 * be displayed. <p>
 *
 * <b>Indexed Scrolling</b><p>
 *
 * Scrolling is improved using indexed scrolling:
 * <p>
 *
 * <center><a href="DemoListView1-2.jpg"><img src="DemoListView1-2.jpg" width="300" alt="image"></a></center> <p>
 *
 * The user may touch and drag the scrolling elevator to move quickly over large distances in the underlying
 * data.  The pop-out letter beside the scrollbar handle provides a visual cue of the current
 * location
 * of scrolling.
 * <p>
 *
 * To enable indexed scrolling, the ScrollView must be configured to perform fast indexing. This can be done
 * two ways: (i) programmatically, by invoking the <code>setFastScrollEnabled</code> method on the ListView, or (ii) by
 * adding the
 * <code>fastScrollEnabled</code> attribute to the <code>ScrollView</code> element in the XML layout file.  The
 * latter approach is used here, as seen in the XML code above.
 * <p>
 *
 * As well, the adapter must be configured to implement the <code>SectionIndexer</code> interface.  This entails
 * implementing three interface methods and dividing the ScrollView contents into sections.  Full details are in
 * <code>NumberAdapter.java</code>.
 * <p>
 *
 * @author (c) Scott MacKenzie, 2016-2018
 */

@SuppressWarnings("unused")
public class MainListViewActivity extends ListActivity {
    final static String MYDEBUG = "MYDEBUG"; // for Log.i messages
    ListView lv;
    final int FRICTION_SCALE_FACTOR = 150000;
    String scrollMethod, GroupNum, timeStamp;
    TextView randomNumber;
    int trialCounter = 0;
    boolean upHeld, downHeld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // make the ListVew the UI for the application


        //get scroll method form setting
        Intent intentRecieved = getIntent();
        Bundle b = intentRecieved.getExtras();
        scrollMethod = b.getString("userScrollMethod");
        GroupNum = b.getString("groupMethod");
        timeStamp = b.getString("timeStamp");

        if (scrollMethod.contains("Button")) {
            setContentView(R.layout.button_scroll_view);
        } else {
            setContentView(R.layout.list_view_layout);
        }

        // get the array of words to present in the ListView
//        String[] words = getResources().getStringArray(R.array.words);

        // create a word adapter bound to the array of words
//        NumberAdapter wa = new NumberAdapter(words);
//        while (trialCounter < 5) {
        //Access the layout of the MainActivity
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        // get a reference to the ListView
        lv = (ListView) layout.findViewById(android.R.id.list);
        lv.setFastScrollEnabled(false);

        randGen();
        // Attach a listener
        if (scrollMethod.contains("Direct")) {
            lv.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_UP:
                            if (scrollMethod.equals("Direct Scroll Without Physics")) {
                                lv.setFriction(ViewConfiguration.getScrollFriction() * FRICTION_SCALE_FACTOR);
                                System.out.println("Set the friction to 15000 " + motionEvent.getActionIndex());
                            } else if (scrollMethod.equals("Direct Scroll With Physics")) {
                                System.out.println("Set the friction to 0 " + motionEvent.getActionIndex());
                            }
                    }
                    return false;
                }
            });
        } else if (scrollMethod.contains("Button")) {
            // make the ButtonListVew the UI for the application

//            // get a reference to the ListView
//            lv = (ListView) layout.findViewById(android.R.id.list);
//            lv.setFastScrollEnabled(false);

            //get the textview for random number

//            randGen();

            upHeld = false;
            downHeld = false;

            Button up = (Button) findViewById(R.id.upButton);
            Button down = (Button) findViewById(R.id.downButton);
            up.bringToFront();
            down.bringToFront();


            up.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        upHeld = true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        upHeld = false;
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        upHeld = true;
                    }
                    if (upHeld) {
                        lv.smoothScrollToPosition(lv.getFirstVisiblePosition() - 1);
                    }
                    upHeld = true;
                    return true;
                }
            });

            down.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        downHeld = true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        downHeld = false;
                    } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        downHeld = true;
                    }
                    if (downHeld) {
                        lv.smoothScrollToPosition(lv.getLastVisiblePosition() + 1);
                    }
                    downHeld = true;
                    return true;
                }
            });
        }
//        }

        Log.i(MYDEBUG, "Trial counter" + trialCounter);

//        if (trialCounter == 5) {
//            Intent i = new Intent(getApplicationContext(), ScrollSetup.class);
//            startActivity(i);
//        }

        // start experiment activity

        // give the adapter to the ListView
//        lv.setAdapter(wa);

    }

    public void randGen() {
        //get the textview for random number
        randomNumber = (TextView) findViewById(R.id.randomNumber);
//        while (trialCounter < 5) {
        String[] arr;
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = String.valueOf(Math.abs(rd.nextInt() % 500)); // storing random integers in an array
//            for (int j = 0; j < i; j++) {
//                if (arr[i] == arr[j]) {
//                    arr[j] = String.valueOf(Math.abs((rd.nextInt() % 500))); //What's this! Another random number!
//                }
//            }
////            System.out.println(arr[i]); // printing each array element
//        }
        ArrayList<String> a = new ArrayList<>(100);
        for (int i = 0; i <= 300; i++) { //to generate from 0-10 inclusive.
            //For 0-9 inclusive, remove the = on the <=
            a.add(String.valueOf(i));
        }
        Collections.shuffle(a);
        arr = (a.subList(0, 30)).toArray(new String[30]);

        int rnd = new Random().nextInt(arr.length);
        String val = arr[rnd];
        randomNumber.setText(val);

        trialCounter++;
        CustomAdapter ad = new CustomAdapter(this, arr, val, trialCounter, scrollMethod, GroupNum, timeStamp);
        lv.setAdapter(ad);
        Bundle newB = new Bundle();
        newB.putString("random", val);
    }


}
