package iou.software.owesmoneyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mads on 6/21/2015.
 */

public class SummaryActivity extends Activity {
    //Fields
    public final static String JSON = "JSON";
    private static int mTotalAmount = 0;
    private static int mAverageAmount = 0;

    private static TextView mTotalAmountView;
    private static TextView mAverageAmountView;
    private static View mSeperatorView;
    private ListView mListView;

    TransactionsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        //Getting the billing out
        String json = getIntent().getStringExtra(JSON);

        Billing billing = new Gson().fromJson(json, Billing.class);

        final List<Person> personList = billing.getPersons();

        //Starting up the ComplexAlgorithm class to process the data
        final ComplexAlgorithm complex = new ComplexAlgorithm();


        //Setting the text views to display the average and total ammounts
        mTotalAmount=complex.calculateTotal((ArrayList<Person>) personList);
        mAverageAmount=complex.calculateMean((ArrayList<Person>) personList);

        mTotalAmountView = (TextView) findViewById(R.id.total_money);
        mTotalAmountView.setText("" + mTotalAmount);

        mAverageAmountView = (TextView) findViewById(R.id.average_money);
        mAverageAmountView.setText("" + mAverageAmount);

        mSeperatorView = (View) findViewById(R.id.seperator);

        mListView = (ListView) findViewById(R.id.listView);

        //Running the algorithm
        complex.calculateTransactions((ArrayList<Person>) personList);

        //Setting up the adapter, passing the list of transactions
        mAdapter = new TransactionsAdapter(getApplicationContext(),complex.getTransactions1());
        mListView.setAdapter(mAdapter);





        /*Send sms Button, pressing it sends text messages to all the people
        who the algorithm determined owes money telling them how much they owe, to whom
        aswell as a link to mobilepay in the appstore/google play store.
        */
        final Button mSendButton = (Button) findViewById(R.id.notify_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Iterate over every person sending an sms to each one
                for (int i=0;i<complex.getTransactions1().size();i++) {
                    String phonenumber = complex.getTransactions1().get(i).getOwes().getPhoneNumber();
                    String message = complex.getTransactionStrings().get(i)+" (  " + complex.getTransactions1().get(i).getTakes().getPhoneNumber()+"  )";
                    String message2 = " MobilePay for Android: https://goo.gl/eWx9eo";
                    String message3 = " MobilePay for iPhone: https://goo.gl/pMCTVS";

                    sendSMS(phonenumber, message + message2 + message3);

                }
            }

        });
        //Back button, this button returns to the overview in MainActivity using the finish method.
        final Button mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Return to main
                finish();
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
        //TODO: Fjern system outs
        System.out.println("sendSMS has started " + phonenumber);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phonenumber, null, message, null, null);
        System.out.println("sendSMS is finished");

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
