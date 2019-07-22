package com.example.exxxxxcaliberrrrrrrr;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Player> list;
    MyHelper myHelper;
    SQLiteDatabase db;

    public CustomAdapter(Activity context, ArrayList<Player> list) {
        this.context = context;
        this.list = list;
        myHelper = new MyHelper(context,"player.db");
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    //Nếu có id trả về Id không có trả về một giá trị bất kỳ
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;

    }

    //ViewHolder inner class
    class ViewHolder {
        public TextView itemName;
        public Button btnDelete;
        public Button btnUpadte;
    }
}
