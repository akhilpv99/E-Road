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

public class PaymentsDone extends ArrayAdapter<PaidmoneyAdapter> {

    private Activity context;
    private List<PaidmoneyAdapter> paymentplist;

    public PaymentsDone(Activity context,List<PaidmoneyAdapter> paymentplist){
        super(context, R.layout.listviewpayments,paymentplist);
        this.context=context;
        this.paymentplist=paymentplist;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listviewpayments, null, true);

        TextView textviewfname = (TextView) listViewItem.findViewById(R.id.textView01);
        TextView textviewamount = (TextView) listViewItem.findViewById(R.id.textView02);

        PaidmoneyAdapter paidmoneyAdapter = paymentplist.get(position);

        textviewfname.setText(paidmoneyAdapter.getPayeename());
        textviewamount.setText(paidmoneyAdapter.getPaidamount());

        return listViewItem;
    }
}
