package ca.lakeeffect.pitscoutingapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TODO: Redo text sizes

    List<Counter> counters = new ArrayList<>();
    List<CheckBox> checkboxes = new ArrayList<>();
    List<RadioGroup> radiogroups = new ArrayList<>();
    List<Button> buttons = new ArrayList<>();
    List<SeekBar> seekbars = new ArrayList<>();

    Button save;

    TextView scoutNameText;
    TextView robotNumText; //robotnum and round

    int robotNum = 2708;
    String scoutName = "Woodie Flowers";

    static long start;

    InputPagerAdapter pagerAdapter;
    ViewPager viewPager;

    //    BluetoothSocket bluetoothsocket;
    ArrayList<String> pendingmessages = new ArrayList<>();
    boolean connected;

    Button moreOptions;

//    Thread bluetoothConnectionThread;

    ListenerThread listenerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs1 = getSharedPreferences("theme", MODE_PRIVATE);
        switch (prefs1.getInt("theme", 0)) {
            case 0:
                setTheme(R.style.AppTheme);
                break;
            case 1:
                setTheme(R.style.AppThemeLight);
                break;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alert();
        //add all buttons and counters etc.

        SharedPreferences prefs = getSharedPreferences("pendingmessages", MODE_PRIVATE);
        for (int i = 0; i < prefs.getInt("messageAmount", 0); i++) {
            if (prefs.getString("message" + i, null) == null) {
                SharedPreferences.Editor editor = prefs.edit();
                for (int s = i; s < prefs.getInt("messageAmount", 0) - 1; s++) {
                    editor.putString("message" + s, prefs.getString("message" + (s + 1), ""));
                }
                editor.putInt("messageAmount", prefs.getInt("messageAmount", 0) - 1);
                editor.commit();
            } else {
                pendingmessages.add(prefs.getString("message" + i, ""));
            }
        }

        moreOptions = (Button) findViewById(R.id.moreOptions);
        moreOptions.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(MainActivity.this, v, Gravity.CENTER_HORIZONTAL);
                menu.getMenuInflater().inflate(R.menu.more_options, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.reset) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Confirm")
                                    .setMessage("Continuing will reset current data.")
                                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            reset();

                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create()
                                    .show();
                        }
                        if (item.getItemId() == R.id.changeNum) {
                            alert();
                        }

                        if (item.getItemId() == R.id.changeTheme) {
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Confirm")
                                    .setMessage("Continuing will reset current data.")
                                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(MainActivity.this, StartActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .create()
                                    .show();
                        }
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                menu.show();
            }
        });

//        counters.add((Counter) findViewById(R.id.goalsCounter));

        //setup scrolling viewpager
        viewPager = (ViewPager) findViewById(R.id.scrollingview);
        pagerAdapter = new InputPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);


//        NumberPicker np = (NumberPicker) findViewm counters
//        np.setWrapSelectorWheel(false);ById(R.id.numberPicker);
//
//        np.setMinValue(0);
//        np.setMaxValue(20);    //maybe switch fro
//        np.setValue(0);

        //add onClickListeners

//        checkboxes.add((CheckBox) findViewById(R.id.scaleCheckBox));

//        submit = (Button) findViewById(R.id.submitButton);

//        timer = (TextView) findViewById(R.id.timer);
        robotNumText = (TextView) findViewById(R.id.robotNum);

        robotNumText.setText("Robot: " + robotNum);


//        submit.setOnClickListener(this);

        //start bluetooth pairing/connection
//        Thread thread = new Thread(new PairingThread(this, true));
//        thread.start();

        //start listening
//        Thread thread = new Thread(new ListenerThread(bluetoothsocket));
//        thread.start();


        start = System.nanoTime();
    }


    public void registerBluetoothListeners() {
        BroadcastReceiver bState = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("SDAsadsadsadsad", "iouweroiurweoiurewoirweuoiweru");
                String action = intent.getAction();
                switch (action) {
                    case BluetoothDevice.ACTION_ACL_DISCONNECTED:
                        connected = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView) ((RelativeLayout) findViewById(R.id.statusLayout)).findViewById(R.id.status)).setText("DISCONNECTED");
                                ((TextView) ((RelativeLayout) findViewById(R.id.statusLayout)).findViewById(R.id.status)).setTextColor(Color.argb(255, 255, 0, 0));
                            }
                        });
//                        if(bluetoothConnectionThread == null) setupBluetoothConnections();
//                        Thread thread1 = new Thread(bluetoothConnectionThread);
//                        thread1.start();
                        break;
//                    case BluetoothDevice.ACTION_ACL_CONNECTED:
//                        try {
//                            out = bluetoothsocket.getOutputStream();
//                            in = bluetoothsocket.getInputStream();
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ((TextView) findViewById(R.id.status)).setText("CONNECTED");
//                                    ((TextView) findViewById(R.id.status)).setTextColor(Color.argb(255,0,255,0));
//                                    Toast.makeText(MainActivity.this, "connected!",
//                                            Toast.LENGTH_LONG).show();
//                                }
//                            });
//                            while(!pendingmessages.isEmpty()){
//                                for(String message: pendingmessages){
//
//                                    byte[] bytes = new byte[1000000];
//                                    int amount = in.read(bytes);
//                                    if(amount>0)  bytes = Arrays.copyOfRange(bytes, 0, amount);//puts data into bytes and cuts bytes
//                                    else continue;
//                                    if(new String(bytes, Charset.forName("UTF-8")).equals("done")){
//                                        pendingmessages.remove(message);
//                                        break;
//                                    }
//                                }//TODO TEST IF THIS WORKS
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        break;
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(bState, filter);
    }

    StringBuilder data;
    StringBuilder labels;

    public String[] getData() {

        data = new StringBuilder();
        labels = new StringBuilder();

        //General Info
        data.append(robotNum + ",");
        labels.append("Robot,");

        labels.append("Date and Time Of Match,");
        DateFormat dateFormat = new SimpleDateFormat("dd HH:mm:ss");
        Date date = new Date();
        data.append(dateFormat.format(date) + ",");


        PercentRelativeLayout layout;

        //Notes page
        layout = (PercentRelativeLayout) pagerAdapter.notesPage.getView().findViewById(R.id.notesLayout);
        enterLayout(layout);

        //Robot page
        layout = (PercentRelativeLayout) pagerAdapter.robotPage.getView().findViewById(R.id.robotLayout);
        enterLayout(layout);

        //Tele Strategy page
        layout = (PercentRelativeLayout) pagerAdapter.teleStrategyPage.getView().findViewById(R.id.teleLayout);
        enterLayout(layout);

        labels.append("Scout,\n");
        data.append(scoutName+",\n");

        System.out.println(labels.toString());
        System.out.println(data.toString());
        String[] out = {labels.toString(), data.toString()};
        return out;
    }

    void enterLayout(ViewGroup top) {
        for (int i = 0; i < top.getChildCount(); i++) {
            View v = top.getChildAt(i);
            if (v.getId() > 0) {
                if (v instanceof EditText) {
                    data.append(((EditText) v).getText().toString().replace("^", "^^").replaceAll(",", "^c").replaceAll("\n", "^n").replaceAll(":", "^;") + ",");
                    labels.append(getResources().getResourceEntryName(v.getId()) + ",");
                }
                if (v instanceof CheckBox) {
                    data.append(((CheckBox) v).isChecked() + ",");
                    labels.append(getResources().getResourceEntryName(v.getId()) + ",");
                }
            }
            if (v instanceof ViewGroup) {
                enterLayout((ViewGroup) v);
            }
        }
    }


    public boolean saveData() {
        File sdCard = Environment.getExternalStorageDirectory();
        File file = new File(sdCard.getPath() + "/#PitScoutingData/data.csv");

        try {

            boolean newFile = false;
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
                newFile = true;
            }

            FileOutputStream f = new FileOutputStream(file, true);

            OutputStreamWriter out = new OutputStreamWriter(f);

            String[] data = getData();

            if(newFile) out.append(data[0].toString());
            out.append(data[1].toString());
            out.close();

            f.close();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Data Saved Successfully!",
                            Toast.LENGTH_LONG).show();
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "Data NOT Saved!!!",
                            Toast.LENGTH_LONG).show();
                }
            });
            return false;
        }
    }


//
//    public boolean saveData(){
//        File sdCard = Environment.getExternalStorageDirectory();
////        File dir = new File (sdCard.getPath() + "/ScoutingData/");
//
//        File file = new File(sdCard.getPath() + "/#ScoutingData/" + robotNum + ".csv");
//
//        try{
//
//            boolean newfile = false;
//            file.getParentFile().mkdirs();
//            if(!file.exists()) {
//                file.createNewFile();
//                newfile = true;
//            }
//
//            FileOutputStream f = new FileOutputStream(file, true);
//
//            OutputStreamWriter out = new OutputStreamWriter(f);
//
//            String[] data = getData();
//
//            if(newfile) out.append(data[1].toString());
//            out.append(data[0].toString());
//            out.close();
//
//            f.close();
//
////            Thread thread = new Thread(){
////                public void run(){
////                    while(true) {
////                        System.out.println("aaaa");
////                        byte[] bytes = new byte[1000];
////                        try {
////                            if(!connected){
////                                pendingmessages.add(robotNum + ":" + labels.toString() + ":"  + data.toString());
////                                SharedPreferences prefs = getSharedPreferences("pendingmessages", MODE_PRIVATE);
////                                SharedPreferences.Editor editor = prefs.edit();
////                                editor.putString("message"+prefs.getInt("messageAmount",0), robotNum + ":" + labels.toString() + ":"  + data.toString());
////                                editor.putInt("messageAmount", prefs.getInt("messageAmount",0)+1);
////                                editor.apply();
////                                return;
////                            }
////                            int amount = in.read(bytes);
////                            if (new String(bytes, Charset.forName("UTF-8")).equals("done")) {
////                                return;
////                            }
////                            if(!connected){
////                                pendingmessages.add(robotNum + ":" + labels.toString() + ":"  + data.toString());
////                                SharedPreferences prefs = getSharedPreferences("pendingmessages", MODE_PRIVATE);
////                                SharedPreferences.Editor editor = prefs.edit();
////                                editor.putString("message"+prefs.getInt("messageAmount",0), robotNum + ":" + labels.toString() + ":"  + data.toString());
////                                editor.putInt("messageAmount", prefs.getInt("messageAmount",0)+1);
////                                editor.apply();
////                                return;
////                            }
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                }
////            };
////
////            if(bluetoothsocket != null && bluetoothsocket.isConnected()){
////                System.out.println("aaaa");
////                this.out.write((robotNum + ":" + labels.toString() + ":" + data.toString()).getBytes(Charset.forName("UTF-8")));
////                thread.start();
////            }else{
////                pendingmessages.add(robotNum + ":" + labels.toString() + ":"  + data.toString());
////                SharedPreferences prefs = getSharedPreferences("pendingmessages", MODE_PRIVATE);
////                SharedPreferences.Editor editor = prefs.edit();
////                editor.putString("message"+prefs.getInt("messageAmount",0), robotNum + ":" + labels.toString() + ":"  + data.toString());
////                editor.putInt("messageAmount", prefs.getInt("messageAmount",0)+1);
////                editor.apply();
////            }
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

    public void waitForConformation(final StringBuilder labels, final StringBuilder data){
        Thread thread = new Thread(){
            public void run(){
                while(true) {
                    System.out.println("aaaa");
                    byte[] bytes = new byte[1000];
                    try {
                        if(!connected){
                            pendingmessages.add(robotNum + ":" + labels.toString() + ":"  + data.toString());
                            SharedPreferences prefs = getSharedPreferences("pendingmessages", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("message"+prefs.getInt("messageAmount",0), robotNum + ":" + labels.toString() + ":"  + data.toString());
                            editor.putInt("messageAmount", prefs.getInt("messageAmount",0)+1);
                            editor.apply();
                            return;
                        }
                        int amount = listenerThread.in.read(bytes);
                        if (new String(bytes, Charset.forName("UTF-8")).equals("done")) {
                            return;
                        }
                        if(!connected){
                            pendingmessages.add(robotNum + ":" + labels.toString() + ":"  + data.toString());
                            SharedPreferences prefs = getSharedPreferences("pendingmessages", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("message"+prefs.getInt("messageAmount",0), robotNum + ":" + labels.toString() + ":"  + data.toString());
                            editor.putInt("messageAmount", prefs.getInt("messageAmount",0)+1);
                            editor.apply();
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    public int getLocationInSharedMessages(String message){
        SharedPreferences prefs = getSharedPreferences("pendingmessages", MODE_PRIVATE);
        for(int i=0;i<prefs.getInt("messageAmount",0);i++) {
            if(prefs.getString("message" + i, "").equals(message)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    Toast.makeText(MainActivity.this, "The app has to save items to the external storage", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            moreOptions.callOnClick();
        }
        return;
    }

    public void reset(){
        //setup scrolling viewpager
        alert();

//        viewPager = (ViewPager) findViewById(R.id.scrollingview);
        viewPager.setAdapter(pagerAdapter);
//        viewPager.setOffscreenPageLimit(3);
//        viewPager.getAdapter().notifyDataSetChanged();


    }

    public void alert(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog)
                .setTitle("Enter Info")
                .setPositiveButton(android.R.string.yes,  null)
                .setCancelable(false)
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
             @Override
             public void onShow(final DialogInterface dialog) {
                 SharedPreferences prefs = getSharedPreferences("scoutName", MODE_PRIVATE);
                 ((EditText) ((AlertDialog) dialog).findViewById(R.id.editText3)).setText(prefs.getString("scoutName", ""));
                 ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                         LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog, null);
                         EditText robotNumin = (EditText) ((AlertDialog) dialog).findViewById(R.id.editText);
                         EditText scoutNamein = (EditText) ((AlertDialog) dialog).findViewById(R.id.editText3);
                         try {
                             robotNum = Integer.parseInt(robotNumin.getText().toString());
                             scoutName = scoutNamein.getText().toString();

                             SharedPreferences prefs = getSharedPreferences("scoutName", MODE_PRIVATE);
                             SharedPreferences.Editor editor = prefs.edit();
                             editor.putString("scoutName", scoutName);
                             editor.apply();

                             if(scoutName.equals("")){
                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         Toast.makeText(MainActivity.this, "Invalid Scout Name",
                                                 Toast.LENGTH_LONG).show();
                                     }
                                 });
                                 return;
                             }

                         } catch (NumberFormatException e) {
                             runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     Toast.makeText(MainActivity.this, "Invalid Data! Are any fields blank?",
                                             Toast.LENGTH_LONG).show();
                                 }
                             });
                             return;
                         }
                         robotNumText = (TextView) findViewById(R.id.robotNum);
                         robotNumText.setText("Robot: " + robotNum);
                         scoutNameText = (TextView) findViewById(R.id.scoutName);
                         scoutNameText.setText("Scout: "+scoutName);
                         pagerAdapter.photoPage.robotNum = robotNum;
                         pagerAdapter.photoPage.loadList();
                         System.out.println(pagerAdapter.photoPage.robotNum + "\t" + robotNum);
                         dialog.dismiss();
                     }
                 });

             }
         });
        dialog.show();
    }


}