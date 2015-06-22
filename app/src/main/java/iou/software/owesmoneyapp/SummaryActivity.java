package iou.software.owesmoneyapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import iou.software.owesmoneyapp.Person;


public class SummaryActivity extends Activity {

    private static int mTotalAmount = 0;
    private static int mAverageAmount = 0;

    private static TextView mTotalAmountView;
    private static TextView mAverageAmountView;
    private ListView mListView;

    PersonsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        mTotalAmountView = (TextView) findViewById(R.id.total_money);
        mTotalAmountView.setText(""+mTotalAmount);
        mAverageAmountView = (TextView) findViewById(R.id.average_money);
        mAverageAmountView.setText(""+mAverageAmount);
        mListView = (ListView) findViewById(R.id.listView);

        //List
        Person mads = new Person("Mads","45880974",190);
        Person jens = new Person("Jens","24660202",0);
        Person[] persons = {mads,jens};
        final List<Person> personList = new ArrayList<>();
        personList.addAll(Arrays.asList(persons));

        mAdapter = new PersonsAdapter(getApplicationContext(),personList);
        mListView.setAdapter(mAdapter);


        mListView.setFooterDividersEnabled(true);

        //Button
        final Button mSummarizeButton = (Button) findViewById(R.id.notify_button);
        mSummarizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Iterate over every person sending an sms to each one
                sendSMS(personList.get(0).getPhoneNumber(),personList.get(0).getPersonName());
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
        return true;

    }
    public void sendSMS (String phonenumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phonenumber, null, message, null, null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
