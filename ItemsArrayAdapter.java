package com.seif.breakfast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seif.breakfast.model.Item;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ItemsArrayAdapter extends BaseAdapter {
   static Item item;
    static ArrayList<Item> items;
    LayoutInflater inflater;
    static Activity activity;

    public ItemsArrayAdapter(ArrayList<Item> m, Activity activity) {
        items = m;
        this.activity = activity;
        inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    public Context getContext() {
        return activity;
    }

    public void add(Item object) {
        synchronized (item) {
            items.add(object);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        synchronized (item) {
            items.clear();
        }
        notifyDataSetChanged();
    }

    public void setData(List<Item> data) {
        clear();
        for (Item item : data) {
            add(item);
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateReceiptsList(ArrayList<Item>newlist) {
        items = newlist;
       
        this.notifyDataSetChanged();
    }

    public static class ViewHolder
    {
        ImageButton imageButton_plus,imageButton_minus;
        TextView textView_counter_val,textView_item_name;
        Button btn_detail;
        int position;
        ItemsArrayAdapter itemsArrayAdapter;

        public ViewHolder(int position,ItemsArrayAdapter mitemsArrayAdapter) {
            this.position = position;
            itemsArrayAdapter = mitemsArrayAdapter;
        }

        public void prepare(View view)
        {

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            //imageView = (ImageView) view.findViewById(R.id.movie_imageview);
            imageButton_plus = (ImageButton)view.findViewById(R.id.image_btn_plus);
            imageButton_minus =  (ImageButton)view.findViewById(R.id.image_btn_minus);
            textView_item_name = (TextView)view.findViewById(R.id.text_view_category);
            textView_counter_val = (TextView)view.findViewById(R.id.text_view_category_counter_val);
            btn_detail = (Button)view.findViewById(R.id.btn_detail);
            textView_item_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText input = new EditText(activity);
                    new AlertDialog.Builder(activity)
                            .setTitle("اسمه ايه؟ ")
                            .setMessage(" خليك مختصر ..")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if(!input.getText().toString().isEmpty()){
                                        String itemName = input.getText().toString();
                                        if(itemName == null || itemName.isEmpty() || itemName.length()<2){
                                            Toast.makeText(activity,"اكتب حاجة عدله",Toast.LENGTH_SHORT).show();
                                        }
                                        items.get(position).setName(itemName);
                                        itemsArrayAdapter.updateReceiptsList(items);

                                    }

                                }
                            })

                            .setView(input)
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_input_get)
                            .show();
                }
            });
            imageButton_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cur = Integer.parseInt(textView_counter_val.getText().toString().trim());
                    textView_counter_val.setText("  " +String.valueOf(cur + 1 )+"  ");
                    items.get(position).setCounter(cur+1);
                }
            });
            imageButton_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int cur = Integer.parseInt(textView_counter_val.getText().toString().trim());
                   if(cur!=0){
                       textView_counter_val.setText("  " +String.valueOf(cur -1) +"  ");
                       items.get(position).setCounter(cur-1);
                   }
                }
            });
            btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText input = new EditText(activity);
                    new AlertDialog.Builder(activity)
                            .setTitle("ظبط ")
                            .setMessage("هتخليه ازاي؟")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if(!input.getText().toString().isEmpty()){
                                        itemsArrayAdapter.addNewCustomItem(input.getText().toString(),position);
                                    }

                                }
                            })
                            .setView(input)
                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_input_get)
                            .show();
                }
            });
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.layout_row,null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder(position,this);
            holder.prepare(vi);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (items.size() <= 0) {
         holder.imageButton_minus=null;
         holder.imageButton_plus=null;
         holder.textView_counter_val=null;
         holder.textView_item_name=null;
         holder.btn_detail = null;

        } else {
            /***** Get each Model object from Arraylist ********/
            this.item = null;
            item =  items.get(position);

            /************  Set Model values in Holder elements ***********/
            holder.textView_item_name.setText(item.getName());
            holder.textView_counter_val.setText("  "+String.valueOf(item.getCounter())+"  ");

        }
        return vi;


    }
    public void addNewCustomItem(String newItem,int position){
        items.add(position+1,new Item(newItem,1));
    }
    public String getWholeThing(){
        String wholeThing = "";
        for(int i= 0;i<getCount();i++){

            wholeThing +=items.get(i).getName()+"*"+items.get(i).getCounter()+"+";
        }
        return wholeThing;
    }
}