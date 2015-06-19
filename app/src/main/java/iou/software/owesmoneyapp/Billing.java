package iou.software.owesmoneyapp;

import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by Kasper on 19/06/15.
 */
public class Billing {

    private static String title;
    private CheckBox checkBox;
    private Button editButton;
    private Button deleteButton;

    public static String getTitle() {
        return title;

    }

    public boolean getStatus() {
        return checkBox.isChecked();
    }






}
