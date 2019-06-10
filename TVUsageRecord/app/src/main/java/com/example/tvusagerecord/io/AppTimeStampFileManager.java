package com.example.tvusagerecord.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import com.example.tvusagerecord.object.AppTimeStamp;

/**
 * class of file manager of reading, updating, printing the csv. file of app time stamps
 */
public class AppTimeStampFileManager {

    /**
     * Read content from the file, as a list containing of each line as a string
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public ArrayList<String> readFile(String fileName) throws FileNotFoundException {
        Scanner scan = new Scanner(new FileInputStream(fileName), "UTF-8");
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
        Scanner scan = new Scanner(new FileInputStream(fileName), "UTF-8");
        ArrayList<String> strList = new ArrayList<>();
        while (scan.hasNextLine()) {
            String nextLine = scan.nextLine();
            String[] values = nextLine.split(",");
            String name = values[1];
            strList.add(name);
        }
        String lastItem = strList.get(strList.size() - 1);
        return lastItem;
    }

    /**
     * update the file: add a new AppTimeStamp item to it
     * @param fileName
     * @param app
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public void updateFile(String fileName, AppTimeStamp app) throws FileNotFoundException, UnsupportedEncodingException {
        //get the list of str from file and update the list
        ArrayList<String> list = readFile(fileName);
        String item = app.getTimeStamp() + "," + app.getAppName();
        list.add(item);
        //store back to file
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        for (int i = 0; i < list.size(); i++) {
            writer.println(list.get(i));
        }
        writer.close();
    }

}
