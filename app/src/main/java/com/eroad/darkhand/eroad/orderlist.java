package com.eroad.darkhand.eroad;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class orderlist extends ArrayAdapter<Orders> {

    private Activity context;
    private List<Orders> orderlist;

    public orderlist(Activity context, List<Orders> orderlist) {

        super(context, R.layout.listview2rows, orderlist);
        this.context = context;
        this.orderlist = orderlist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listviewitem = inflater.inflate(R.layout.listview2rows, null, true);

        TextView textview1 = (TextView) listviewitem.findViewById(R.id.textView1);
        TextView textview2 = (TextView) listviewitem.findViewById(R.id.textView2);
        TextView textview3 = (TextView) listviewitem.findViewById(R.id.textView3);

        Orders orders = orderlist.get(position);

        textview1.setText(orders.getFname());
        textview2.setText(orders.getType());
        textview3.setText(orders.getQuantity());
        return listviewitem;


    }
}
