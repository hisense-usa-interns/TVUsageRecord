package com.example.tvusagerecord;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import android.util.Log;
import android.widget.Button;
import android.view.View;

public class UsbActivity extends AppCompatActivity {

    /** file name of app time stamp file */
    public static final String fileName = "app_timestamp.csv";
    /** file name of duration (periods) file */
    public static final String durationFileName = "duration.csv";
    /** file name of app rating file */
    public static final String ratingFileName = "rating.csv";
    /** file recording first boot time */
    public static final String startFile = "first_time.csv";
    /** tag of this class */
    public static final String TAG = "UsbActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb);

        Button exportBtn = (Button) findViewById(R.id.export);
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File[] files = getFilesFromExternalStorage();
                saveFilesToUSB(files);
            }
        });
    }



    /**
     * get all 4 files from external storage
     * @return
     */
    public File[] getFilesFromExternalStorage() {
        File timestamp = new File(Environment.getExternalStorageDirectory(), fileName);
        File duration = new File(Environment.getExternalStorageDirectory(), durationFileName);
        File rating = new File(Environment.getExternalStorageDirectory(), ratingFileName);
        File start = new File(Environment.getExternalStorageDirectory(), startFile);
        File[] files = new File[4];
        files[0] = timestamp;
        files[1] = duration;
        files[2] = rating;
        files[3] = start;
        return files;
    }

    public void saveFilesToUSB(File[] files) {
        //first get accurate path of media_rw
        File external = Environment.getExternalStorageDirectory();
        Log.d(TAG, "External storage absolute path: " + external.getAbsolutePath());

        File storage = new File("/storage");
        Log.d(TAG, "Storage path: " + storage.getAbsolutePath());
        File[] list = storage.listFiles();
        File usb = null;
        for (int i = 0; i < list.length; i++) {
            Log.d(TAG, "file name: " + list[i].getName());
            if (list[i].getName().contains("-")) {
                usb = list[i];
            }
        }

        //save files into usb folder

        File newTimeStamp = new File(usb, fileName);
        newTimeStamp = files[0];
        Log.d(TAG, "File saved: " + newTimeStamp.getName());
        Log.d(TAG, "File path: " + newTimeStamp.getAbsolutePath());

        File newDuration = new File(usb, durationFileName);
        newDuration = files[1];
        Log.d(TAG, "File saved: " + newDuration.getName());
        Log.d(TAG, "File path: " + newDuration.getAbsolutePath());

        File newRating = new File(usb, ratingFileName);
        newRating = files[2];
        Log.d(TAG, "File saved: " + newRating.getName());
        Log.d(TAG, "File path: " + newRating.getAbsolutePath());

        File newFirst = new File(usb, startFile);
        newFirst = files[3];
        Log.d(TAG, "File saved: " + newFirst.getName());
        Log.d(TAG, "File path: " + newFirst.getAbsolutePath());


    }


}
