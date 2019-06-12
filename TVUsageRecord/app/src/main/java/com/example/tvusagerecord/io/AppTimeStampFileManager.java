package com.example.tvusagerecord.io;

import android.os.Environment;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import com.example.tvusagerecord.object.AppTimeStamp;
import java.io.File;

/**
 * class of file manager of reading, updating, printing the csv. file of app time stamps
 */
public class AppTimeStampFileManager {

    /**
     * Check if the external storage of the device is available for read and write
     * @return if external storage available
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d(AppTimeStampFileManager.class.getSimpleName(), "yes, file is writable");
            return true;
        }
        Log.d(AppTimeStampFileManager.class.getSimpleName(), "no, file is not writable");
        return false;
    }

    /**
     * Read content from the file, as a list containing of each line as a string
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public ArrayList<String> readFile(String fileName) throws FileNotFoundException {

        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        FileInputStream stream = new FileInputStream(file);
        Scanner scan = new Scanner(stream, "UTF-8");

        ArrayList<String> strList = new ArrayList<>();
        while (scan.hasNextLine()) {
            strList.add(scan.nextLine());
        }
        return strList;
    }

    /**
     * return the pkg name of the last (newest) item from the file
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public String getLastItemName(String fileName) throws FileNotFoundException {
        Log.d(AppTimeStampFileManager.class.getSimpleName(), "last item started");
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        FileInputStream stream = new FileInputStream(file);
        Scanner scan = new Scanner(stream, "UTF-8");

        ArrayList<String> strList = new ArrayList<>();
        while (scan.hasNextLine()) {
            String nextLine = scan.nextLine();
            String[] values = nextLine.split(",");
            String name = values[1];
            strList.add(name);
        }
        String lastItem = strList.get(strList.size() - 1);
        Log.d(AppTimeStampFileManager.class.getSimpleName(), "last item ended");
        return lastItem;
    }

    /**
     * update the file: add a new AppTimeStamp item to it
     * @param fileName
     * @param app
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void updateFile(String fileName, AppTimeStamp app) throws IOException {
        boolean append = true;
        Log.d(AppTimeStampFileManager.class.getSimpleName(), "update started");
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        FileWriter csvWriter = new FileWriter(file, append);
        FileInputStream stream = new FileInputStream(file);
        Scanner scan = new Scanner(stream, "UTF-8");

        String item = app.getTimeStamp() + "," + app.getAppName();
        if(scan.hasNext()) {
            csvWriter.append("\n");
        }
        csvWriter.append(item);
        csvWriter.close();
        Log.d(AppTimeStampFileManager.class.getSimpleName(), "update ended");
    }

}
