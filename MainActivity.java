package com.seif.breakfast;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {

   ArrayList<String> items = new ArrayList<String>(Arrays.asList("بطاطس","طعمية","فول"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ItemsArrayAdapter itemsAdapter = new ItemsArrayAdapter(items,this);

        ListView listView = (ListView) findViewById(R.id.list_item);

        listView.setAdapter(itemsAdapter);
    }
}
