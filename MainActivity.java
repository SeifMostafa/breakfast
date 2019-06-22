package com.seif.breakfast;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seif.breakfast.model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends Activity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> meals = new ArrayList<String>(Arrays.asList("فطار", "غدا", "عشا"));
    ArrayList<Item> items = null;
    ArrayList<Item> default_items = new ArrayList<Item>(Arrays.asList(new Item("بطاطس",2), new Item("طعمية",2),new Item( "فول",1)));
    private int currentMealIndex = 0;
    private boolean meal_customized = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences  = getPreferences(MODE_PRIVATE);
       editor = sharedPreferences.edit();

        ItemsArrayAdapter itemsAdapter = new ItemsArrayAdapter(default_items, this);

        ListView listView = (ListView) findViewById(R.id.list_item);

        listView.setAdapter(itemsAdapter);

        FloatingActionButton floatingActionButton_saveIt = findViewById(R.id.fab_saveIt);
        FloatingActionButton floatingActionButton_loadIt = findViewById(R.id.fab_asUsual);
        floatingActionButton_loadIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String mealComponents =  sharedPreferences.getString(determineMeal(),null);
               if(mealComponents!=null){
                   Log.i("content",mealComponents);
                    String []item_strings = mealComponents.split("\\+");
                   items = new ArrayList<>();

                   for(String item_string:item_strings){
                       String[]item_content = item_string.split("\\*");
                       items.add(new Item(item_content[0],Integer.parseInt(item_content[1])));
                   }
                   itemsAdapter.updateReceiptsList(items);

               }else {
                   Toast.makeText(MainActivity.this,"مفيش حاجة محفوظة",Toast.LENGTH_LONG).show();
               }
            }
        });
        floatingActionButton_saveIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString(determineMeal(), itemsAdapter.getWholeThing());
                editor.commit();
                Toast.makeText(MainActivity.this, "حفظنا اللي جمعته", Toast.LENGTH_LONG).show();
            }
        });

        TextView textView_meal = findViewById(R.id.text_view_meal);
        textView_meal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP)

                    if (currentMealIndex == 2) {
                        currentMealIndex = -1;
                    }
                textView_meal.setText(meals.get(++currentMealIndex));

                meal_customized = true;

                return false;
            }
        });
    }

    private String determineMeal() {
        LocalDateTime now = LocalDateTime.now();
        if (!meal_customized) {
            if (now.getHour() < 12) {
                return meals.get(0);
            } else if (now.getHour() < 18)
                return meals.get(1);
            else return meals.get(2);
        }
        return meals.get(currentMealIndex);

    }
}
