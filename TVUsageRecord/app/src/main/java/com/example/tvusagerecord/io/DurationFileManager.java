package com.example.tvusagerecord.io;

import com.example.tvusagerecord.object.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.io.FileInputStream;

import android.os.Environment;
import android.util.Log;

/**
 * class implementing print methods of all statistics
 */
public class DurationFileManager {

    public static final String TAG = DurationFileManager.class.getSimpleName();

    /**
     * print the format of Duration to create a file, Used for creating a file
     * @param duration object
     * @param week 1-first week since start date, 2-second
     * @param fileName to print to
     */
    public void printToConstructFileByDuration(Duration duration, int week, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.println("DURATION SHEET,Total,Morning Hours,Noon Hours,Afternoon Hours,Evening Hours,Night Hours");
        //if duration is in the first week
        if (week == 1) {
            writer.println("Week 1," + duration.getTotal() + "," + duration.getMorning() + "," + duration.getNoon() + "," + duration.getAfternoon() + "," + duration.getEvening() + "," + duration.getNight());
        } else {
            writer.println("Week 1," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0);
        }
        //if duration is in the second week
        if (week == 2) {
            writer.println("Week 2," + duration.getTotal() + "," + duration.getMorning() + "," + duration.getNoon() + "," + duration.getAfternoon() + "," + duration.getEvening() + "," + duration.getNight());
        } else {
            writer.println("Week 2," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0);
        }
        writer.close();
    }


    /**
     * Check if the external storage of the device is available for read and write
     * @return if external storage available
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d(TAG, "Yes, file is writable - duration.csv");
            return true;
        }
        Log.d(TAG, "No, file is not writable - duration.csv");
        return false;
    }


    /**
     * create a new file, fill in week number and titles
     * @param fileName to print to
     */
    public void constructFile(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.println("DURATION SHEET,Total,Morning Hours,Noon Hours,Afternoon Hours,Evening Hours,Night Hours");
        writer.println("Week 1," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0);
        writer.println("Week 2," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0);
        writer.close();
    }


    /**
     * Overwrite the file with the whole table containing titles and all Duration statistics
     * @param fileName
     * @param table
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void overwriteFile(String fileName, String[][] table) throws FileNotFoundException, UnsupportedEncodingException {
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        for (int i = 0; i < table.length; i++) {
            writer.println(table[i][0] + "," + table[i][1] + "," + table[i][2] + "," + table[i][3] + "," + table[i][4] + "," + table[i][5] + "," + table[i][6]);
        }
        writer.close();
    }


    /**
     * read all content from the file to return a 2D string array
     * @param fileName
     * @return table containing all content from the file
     * @throws FileNotFoundException
     */
    public String[][] readDurationFile(String fileName) throws FileNotFoundException {
        File file = new File(Environment.getExternalStorageDirectory(), fileName);
        FileInputStream stream = new FileInputStream(file);
        Scanner scan = new Scanner(stream, "UTF-8");

        String[][] table = new String[4][7];
        int row = 0;
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] values = line.split(",");
            for (int i = 0; i < values.length; i++) {
                table[row][i] = values[i];
            }
            row++;
        }
        return table;
    }
}
