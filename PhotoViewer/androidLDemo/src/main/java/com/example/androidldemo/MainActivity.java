
package com.example.androidldemo;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_1, new String[] { "title" },
                new int[] { android.R.id.text1 }));
    }

    protected List<Map<String, Object>> getData() {
        List<Map<String, Object>> myData = new ArrayList<Map<String, Object>>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("android.intent.category.androidLdemo");

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (null == list)
            return myData;

        int len = list.size();

        for (int i = 0; i < len; i++) {
            ResolveInfo info = list.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            addItem(myData, labelSeq.toString(), activityIntent(
                    info.activityInfo.applicationInfo.packageName,
                    info.activityInfo.name));
        }

        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
            private final Collator   collator = Collator.getInstance();

            public int compare(Map<String, Object> map1, Map<String, Object> map2) {
                return collator.compare(map1.get("title"), map2.get("title"));
            }
        };

        protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
            Map<String, Object> temp = new HashMap<String, Object>();
            temp.put("title", name);
            temp.put("intent", intent);
            data.add(temp);
        }

        protected Intent activityIntent(String pkg, String componentName) {
            Intent result = new Intent();
            result.setClassName(pkg, componentName);
            return result;
        }

         @Override
        @SuppressWarnings("unchecked")
        protected void onListItemClick(ListView l, View v, int position, long id) {
            Map<String, Object> map = (Map<String, Object>)l.getItemAtPosition(position);

            Intent intent = (Intent) map.get("intent");
            startActivity(intent);
        }
}
