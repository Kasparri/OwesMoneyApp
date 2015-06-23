package iou.software.owesmoneyapp;

import android.widget.CheckBox;
import android.widget.TextView;


/**
 * Created by Mads on 6/21/2015.
 */

public class TransactionsViewHolder {
    private CheckBox checkBox;
    private TextView textView;

    public TransactionsViewHolder() {
    }

    public TransactionsViewHolder(TextView textView, CheckBox checkBox) {
        this.checkBox = checkBox;
        this.textView = textView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
