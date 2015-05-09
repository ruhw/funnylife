package com.example.ruhaiwen.photoviewer.com.example.ruhaiwen.photoviewer.common;

import android.content.Context;
import android.os.Environment;


import com.example.ruhaiwen.photoviewer.R;
import com.example.ruhaiwen.photoviewer.model.Photo;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ruhaiwen on 14-10-30.
 */
public class FileUtils {


    public static List<Photo> mPhotos =new ArrayList<Photo>();

    public static List<Photo> searchPhotos(Context context,int floor) {
        File file = Environment.getExternalStorageDirectory();

        FilenameFilter[] mFilters = getFilenameFilter(context);

        mPhotos = FileUtils.searchDirectory(file, mFilters, floor);
        return mPhotos;
    }

    public static FilenameFilter[] getFilenameFilter(Context context){
        String[] imageTypes = context.getResources().getStringArray(R.array.image);
        FilenameFilter[] filter = new FilenameFilter[imageTypes.length];

        int i = 0;
        for (final String type : imageTypes) {
            filter[i] = new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith("." + type);
                }
            };
            i++;
        }
        return filter;
    }

    public static List<Photo> searchDirectory(File directory, FilenameFilter[] filter,
                                              int floor) {


        File[] entries = directory.listFiles();

        if (entries != null) {

            for (File entry : entries) {
                if (entry.isDirectory() && ((floor--) > 1)) { //floor表示遍历层次
                    searchDirectory(entry, filter, floor);
                } else {
                    for (FilenameFilter filefilter : filter) {
                        if (filter == null || filefilter
                                .accept(directory, entry.getName())) {
                              addToPhotos(entry);

                        }
                    }
                }
            }
        }
        return mPhotos;
    }

    public static void addToPhotos(File file){
        String path = file.getPath();
        String name = file.getName();

        int idx = name.lastIndexOf(".");
        if (idx <= 0) {
            return;
        }
        String suffix =name.substring(idx+1, name.length());
        String size = FormatFileSize(file.length());

        Photo photo = new Photo(path,name,suffix,size);
        mPhotos.add(photo);

    }
    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String FormatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
