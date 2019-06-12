package com.example.tvusagerecord.manager;

import com.example.tvusagerecord.io.DurationFileManager;
import com.example.tvusagerecord.io.AppTimeStampFileManager;
import com.example.tvusagerecord.object.AppTimeStamp;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Manager of usage recording app
 */
public class Manager {

    /** file manager of duration */
    DurationFileManager durationManager;
    /** file manager of app time stamp */
    AppTimeStampFileManager timestampManager;

    /**
     * manager constructor
     */
    public Manager() {
        this.durationManager = new DurationFileManager();
        this.timestampManager = new AppTimeStampFileManager();
    }


    /**
     * update the value in Duration file
     * @param fileName path/file name to the csv. file
     * @param week number of week, 1 for week1, 2 for week2
     * @param period eg. "total", "morning", "noon", "afternoon", "evening", "night"
     * @param value integer to replace the initial value from the file
     */
    public void updateDurationFile(String fileName, int week, String period, int value) throws FileNotFoundException, UnsupportedEncodingException {
        String[][] table = durationManager.readDurationFile(fileName);
        int col = 0;
        if (period.equals("total"))
            col = 1;
        if (period.equals("morning"))
            col = 2;
        if (period.equals("noon"))
            col = 3;
        if (period.equals("afternoon"))
            col = 4;
        if (period.equals("evening"))
            col = 5;
        if (period.equals("night"))
            col = 6;
        table[week][col] = Integer.toString(value);
        durationManager.overwriteFile(fileName, table);
    }


    /**
     * use file manager to update app time stamp file
     * @param fileName
     * @param app
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void updateAppTimeStampFile(String fileName, AppTimeStamp app) throws IOException {
        timestampManager.updateFile(fileName, app);
    }


    /**
     * get the last item from the file
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public String getNewestPkgName(String fileName) throws FileNotFoundException {
        return timestampManager.getLastItemName(fileName);
    }
}
