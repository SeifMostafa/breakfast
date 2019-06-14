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
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ItemsArrayAdapter extends BaseAdapter {
    String item;
    ArrayList<String> items;
    LayoutInflater inflater;
    static Activity activity;

    public ItemsArrayAdapter(ArrayList<String> m, Activity activity) {
        items = m;
        this.activity = activity;
        inflater = (LayoutInflater) this.activity.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    public Context getContext() {
        return activity;
    }

    public void add(String object) {
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

    public void setData(List<String> data) {
        clear();
        for (String item : data) {
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

    public void updateReceiptsList(ArrayList<String>newlist) {
        items = newlist;
        this.notifyDataSetChanged();
    }

    public static class ViewHolder
    {
        ImageButton imageButton_plus,imageButton_minus;
        TextView textView_counter_val,textView_item_name;
        Button btn_detail;

        public void prepare(View view)
        {
            //imageView = (ImageView) view.findViewById(R.id.movie_imageview);
            imageButton_plus = (ImageButton)view.findViewById(R.id.image_btn_plus);
            imageButton_minus =  (ImageButton)view.findViewById(R.id.image_btn_minus);
            textView_item_name = (TextView)view.findViewById(R.id.text_view_category);
            textView_counter_val = (TextView)view.findViewById(R.id.text_view_category_counter_val);
            btn_detail = (Button)view.findViewById(R.id.btn_detail);
            imageButton_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView_counter_val.setText("  " +String.valueOf(Integer.parseInt(textView_counter_val.getText().toString().trim()) + 1 )+"  ");
                }
            });
            imageButton_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int cur = Integer.parseInt(textView_counter_val.getText().toString().trim());
                   if(cur!=0){
                       textView_counter_val.setText("  " +String.valueOf(cur -1) +"  ");
                   }
                }
            });
            btn_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(activity)
                            .setTitle("ظبط ")
                            .setMessage("Are you sure you want to delete this entry?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
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

            holder = new ViewHolder();
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
            holder.textView_item_name.setText(item);

        }
        return vi;


    }

}