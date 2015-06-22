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
public class PersonsAdapter extends ArrayAdapter<Person> {

    private LayoutInflater inflater;

    public PersonsAdapter(Context context, List<Person> values){
        super(context,R.layout.row_item,R.id.rowTextView,values);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = this.getItem(position);
        CheckBox checkBox;
        TextView textView;
        if (convertView==null) {
            convertView = inflater.inflate(R.layout.row_item, null);
            textView = (TextView) convertView.findViewById(R.id.rowTextView);
            checkBox = (CheckBox) convertView.findViewById(R.id.rowCheckBox);
            convertView.setTag(new PersonViewHolder(textView,checkBox));

        } else {
            PersonViewHolder viewHolder = (PersonViewHolder) convertView.getTag();
            checkBox=viewHolder.getCheckBox();
            textView=viewHolder.getTextView();
        }
        checkBox.setTag(person);
        checkBox.setChecked(false);
        textView.setText(person.getPersonName());
        return convertView;
    }
}
