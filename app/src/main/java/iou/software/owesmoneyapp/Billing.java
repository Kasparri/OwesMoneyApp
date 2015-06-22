package iou.software.owesmoneyapp;

import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasper on 19/06/15.
 */
public class Billing {


    public final static String TITLE = "title";
    public final static String STATUS = "status";
    public final static String JSON = "JSON";

    private String title;
    private boolean status;
    private List<Person> persons;


    public Billing(Intent intent) {
        title = intent.getStringExtra(TITLE);
        status = intent.getBooleanExtra(Billing.STATUS, false);
    }

    public Billing(String title, boolean status, ArrayList<Person> persons) {
        this.title = title;
        this.status = status;
        this.persons = persons;

    }

    public String getTitle() {
        return title;
    }

    public List<Person> getPersons(){
        return persons;
    }

    public boolean getStatus() {
        return status;
    }


    public static void packageIntent(Intent intent, String title, boolean status) {

        intent.putExtra(TITLE, title);
        intent.putExtra(STATUS, status);

    }


    public static void packageIntent(Intent intent, String json) {

        intent.putExtra(JSON,json);

    }


}
