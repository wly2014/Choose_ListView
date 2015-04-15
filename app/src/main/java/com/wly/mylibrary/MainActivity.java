package com.wly.mylibrary;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import com.wly.library_listview.Choose_Adapter;
import com.wly.library_listview.Choose_ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private Choose_ListView choose_listView;
    private Choose_Adapter adapter;
    private List<Map<String, Object>> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        choose_listView = (Choose_ListView) findViewById(R.id.choose_listView);
        list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("number", "wly");
        map1.put("name", "wly");
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map1);


        adapter = new Choose_Adapter(list, this);
        choose_listView.setAdapter(adapter);
        choose_listView.setOnDeleteListener(new OnDeleteListenerImpl());
    }

    private class OnDeleteListenerImpl implements Choose_ListView.OnDeleteListener {

        @Override
        public void onDelete(List<Map<String, Object>> list, HashMap<Integer, Boolean> isCheckMap) {
            Log.i("AllMember", "list.size:" + list.size() + ";isCheckMap.size:" + isCheckMap.size());
            List<Map<String, Object>> tempList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                String number = (String) map.get("number");
                boolean isChecked = isCheckMap.get(i);

                Log.i("", "list:" + number + ";is:" + isChecked);

                if (!isChecked) {
                    tempList.add(map);
                }

            }
            adapter.setList(tempList);
        }
    }
}
