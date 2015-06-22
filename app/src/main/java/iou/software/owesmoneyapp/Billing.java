package iou.software.owesmoneyapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Created by Kasper on 19/06/15.
 */
public class Billing {


    public final static String TITLE = "title";
    public final static String STATUS = "status";

    private String title;
    private boolean status;


    public Billing(Intent intent) {
        title = intent.getStringExtra(TITLE);
        status = intent.getBooleanExtra(Billing.STATUS, false);
    }

    public String getTitle() {
        return title;
    }

    public boolean getStatus() {
        return status;
    }


    public static void packageIntent(Intent intent, String title, boolean status) {

        intent.putExtra(TITLE, title);
        intent.putExtra(STATUS, status);

    }


}
