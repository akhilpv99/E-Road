package com.eroad.darkhand.eroad;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OnlineDpartners extends ArrayAdapter<RegisterAdapter> {

    private Activity context;
    private List<RegisterAdapter> onlinedplist;

    public OnlineDpartners(Activity context,List<RegisterAdapter> onlinedplist){
        super(context, R.layout.listviewdpartner,onlinedplist);
        this.context=context;
        this.onlinedplist=onlinedplist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.dpartnerlistview, null, true);

        TextView textviewdpname = (TextView) listViewItem.findViewById(R.id.textView4);
        TextView textviewdpemail = (TextView) listViewItem.findViewById(R.id.textView5);

        RegisterAdapter registerAdapter = onlinedplist.get(position);

        textviewdpname.setText(registerAdapter.getName());
        textviewdpemail.setText(registerAdapter.getEmail());

        return listViewItem;
    }
}
