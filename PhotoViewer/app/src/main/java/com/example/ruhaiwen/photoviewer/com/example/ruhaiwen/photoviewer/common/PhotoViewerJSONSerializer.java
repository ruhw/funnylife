package com.example.ruhaiwen.photoviewer.com.example.ruhaiwen.photoviewer.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.ruhaiwen.photoviewer.model.Photo;

public class PhotoViewerJSONSerializer {
	
	private static final String TAG = "PhotoViewerJSONSerializer";
	private Context mContext;
	private String mFilename;
	
	public PhotoViewerJSONSerializer(Context c, String filename){
		mContext = c;
		mFilename = filename;
	}
	public void savePhotos(ArrayList<Photo> Photos) throws JSONException, IOException {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for (Photo p: Photos)
           array.put(p.toJSON());
        
        // Write the file to disk
        OutputStreamWriter writer = null;
        File file = new File(mContext.getExternalFilesDir(null), mFilename);
        try {
           FileOutputStream out = null;
           if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
              // Save to sd card if available.
              //out = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/com.example.ruhaiwen.photoviewer/" + mFilename);
        	   out = new FileOutputStream(file);
        	   Log.i(TAG, mContext.getExternalFilesDir(null).toString());
           }
           else{
              out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);             
           }
           writer = new OutputStreamWriter(out);
           writer.write(array.toString());
        } finally {
           if (writer != null)
              writer.close();
           
        }
     }
	
	/*public void savePhotosToExternal(ArrayList<Photo> photos)
			throws JSONException,IOException{
		if(Environment.isExternalStorageEmulated()){
			//File path = mContext.getExternalFilesDir(null);
			//File file = new File(path, mFilename);
			File file = new File(mContext.getExternalFilesDir(null), mFilename);
			//Build an array in JSON
			JSONArray array = new JSONArray();
			for(Photo c : photos) array.put(c.toJSON());
			//Wrime the file to disk
			Writer writer = null;
			
			try{
				OutputStream os = new FileOutputStream(file);
				writer = new OutputStreamWriter(os);
				writer.write(array.toString());
			}finally{
				if(writer != null){
					writer.close();
				}
				Log.i(TAG, mContext.getExternalFilesDir(null).toString());
			}
		}
	}*/
	
	public ArrayList<Photo> loadPhotos() throws IOException, JSONException {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        BufferedReader reader = null;
        File file = new File(mContext.getExternalFilesDir(null), mFilename);

        try {
           FileInputStream in = null;
           // Open and read the file into a StringBuilder.
           if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
              // Load from sd card if available.
              //in = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/PhotoViewer/" + mFilename);
        	   in = new FileInputStream(file);
           else
              in = mContext.openFileInput(mFilename);
           reader = new BufferedReader(new InputStreamReader(in));
           StringBuilder jsonString = new StringBuilder();
           
           String line = null;
           while ((line = reader.readLine()) != null)
              // Line breaks are omitted and irrelevant
              jsonString.append(line);
           
           // Parse the JSON using JSONTokener.
           JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
           
           // Build the array of photos from JSONObjects
           for (int i = 0; i <array.length(); ++i)
               photos.add(new Photo(array.getJSONObject(i)));
        } catch (FileNotFoundException e) {
           // Starting without a file, so ignore.
        } finally {
           if (reader != null)
              reader.close();
        }      
        return photos;
     }
}
