package com.example.tvusagerecord.io;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.text.ParseException;

public class StartTimeRecorder {

    public static final String TAG = StartTimeRecorder.class.getSimpleName();
    public static final String startFile = "first_time.csv";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");

    /**
     * Check if the external storage of the device is available for read and write
     * @return if external storage available
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d(TAG, "Yes, file is writable - first_time.csv");
            return true;
        }
        Log.d(TAG, "No, file is not writable - first_time.csv");
        return false;
    }

    /**
     * store the first start time when the file is empty
     */
    public void storeFirstTimeToFile() throws FileNotFoundException, IOException {
        //check if the first time stamp already exists
        //if exist, set boolean to yes
        boolean firstTimeSet = false;
        File file = new File(Environment.getExternalStorageDirectory(), startFile);
        FileInputStream stream = new FileInputStream(file);
        Scanner scan = new Scanner(stream, "UTF-8");
        if (scan.hasNextLine()) {
            firstTimeSet = true;
        }
        //if the first time stamp is not yet set
        if (firstTimeSet == false) {
            FileWriter csvWriter = new FileWriter(file, true);
            //append the current time in given date format to the file
            csvWriter.append(dateFormat.format(System.currentTimeMillis()));
            csvWriter.close();
        }
    }

    /**
     * get the first start time in string from file if it exists, if not, return null
     * @return
     * @throws FileNotFoundException
     */
    public String getFirstTimeStr() throws FileNotFoundException {
        File file = new File(Environment.getExternalStorageDirectory(), startFile);
        FileInputStream stream = new FileInputStream(file);
        Scanner scan = new Scanner(stream, "UTF-8");
        if (scan.hasNextLine()) {
            String line = scan.nextLine();
            return line;
        }
        return null;
    }

    /**
     * return long type of the first time stored in the file
     * @return
     * @throws FileNotFoundException
     * @throws ParseException
     */
    public long getFirstTimeLong() throws FileNotFoundException, ParseException {
        File file = new File(Environment.getExternalStorageDirectory(), startFile);
        FileInputStream stream = new FileInputStream(file);
        Scanner scan = new Scanner(stream, "UTF-8");
        if (scan.hasNextLine()) {
            String line = scan.nextLine();
            return dateFormat.parse(line).getTime();
        }
        return 0;
    }













}
