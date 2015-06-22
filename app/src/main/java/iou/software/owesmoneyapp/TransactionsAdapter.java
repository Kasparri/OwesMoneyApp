package iou.software.owesmoneyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import java.util.List;

/**
 * Created by Mads on 6/21/2015.
 */
public class TransactionsAdapter extends ArrayAdapter<Transactions> {

    private LayoutInflater inflater;

    public TransactionsAdapter(Context context, List<Transactions> values){
        super(context,R.layout.row_item,R.id.rowTextView,values);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Transactions transaction = this.getItem(position);
        final CheckBox checkBox;
        TextView textView;
        if (convertView==null) {
            convertView = inflater.inflate(R.layout.row_item, null);
            textView = (TextView) convertView.findViewById(R.id.rowTextView);
            checkBox = (CheckBox) convertView.findViewById(R.id.rowCheckBox);
            convertView.setTag(new TransactionsViewHolder(textView,checkBox));

        } else {
            TransactionsViewHolder viewHolder = (TransactionsViewHolder) convertView.getTag();
            checkBox=viewHolder.getCheckBox();
            textView=viewHolder.getTextView();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.setChecked(!checkBox.isChecked());
            }
        });
        checkBox.setTag(transaction);
        checkBox.setChecked(false);
        textView.setText(transaction.getOwes().getPersonName()+" owes "+transaction.getTransaction()+" to "+transaction.getTakes().getPersonName());
        return convertView;
    }
}
