package com.example.ruhaiwen.photoviewer.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ruhaiwen on 14-10-30.
 */
public class Photo {

        private String path;
        private String name;
        private String suffix; //后缀
        private String size;

        private static final String JSON_PATH = "path";
        private static final String JSON_NAME = "name";
        private static final String JSON_SUFFIX = "suffix";
        private static final String JSON_SIZE ="size";

        public Photo(String path, String name, String suffix, String size) {
            // TODO Auto-generated constructor stub
            this.path = path;
            this.name = name;
            this.suffix = suffix;
            this.size = size;
        }

        public Photo(JSONObject json) throws JSONException {
            path = json.getString(JSON_PATH);
            name = json.getString(JSON_NAME);
            suffix = json.getString(JSON_SUFFIX);
            size = json.getString(JSON_NAME);

        }

        public JSONObject toJSON() throws JSONException{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(JSON_PATH, path);
            jsonObject.put(JSON_NAME, name);
            jsonObject.put(JSON_SUFFIX,suffix);
            jsonObject.put(JSON_SIZE,size);
            return jsonObject;
        }


        public String getPath() {
            return path;
        }


        public String getName() {
            return name;
        }


        public String getSuffix() {
            return suffix;
        }



        public String getSize() {
            return size;
        }
}
