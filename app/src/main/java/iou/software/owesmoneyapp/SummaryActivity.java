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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SummaryActivity extends Activity {

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

        //List
        Person mads = new Person("Mads","45880974",190);
        Person jens = new Person("Jens","5554",0);
        final ArrayList<Person> personList = new ArrayList<>();
        personList.add(mads);
        personList.add(jens);

        final ComplexAlgorithm complex = new ComplexAlgorithm();

        mTotalAmount=complex.calculateTotal(personList);
        mAverageAmount=complex.calculateMean(personList);


        mTotalAmountView = (TextView) findViewById(R.id.total_money);
        mTotalAmountView.setText("" + mTotalAmount);

        mAverageAmountView = (TextView) findViewById(R.id.average_money);
        mAverageAmountView.setText("" + mAverageAmount);

        mSeperatorView = (View) findViewById(R.id.seperator);

        mListView = (ListView) findViewById(R.id.listView);

        complex.calculateTransactions(personList);

        mAdapter = new TransactionsAdapter(getApplicationContext(),complex.getTransactions1());
        mListView.setAdapter(mAdapter);





        //Summarize Button
        final Button mSendButton = (Button) findViewById(R.id.notify_button);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Iterate over every person sending an sms to each one
                for (int i=0;i<complex.getTransactions1().size();i++) {
                    String phonenumber = complex.getTransactions1().get(i).getOwes().getPhoneNumber();
                    String message =complex.getTransactionStrings().get(i)+"("+complex.getTransactions1().get(i).getTakes().getPhoneNumber()+")";
                    sendSMS(phonenumber, message);
                }
            }

        });
        //Back button
        final Button mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Return to main
                Intent addIntent = new Intent(SummaryActivity.this,MainActivity.class);
                startActivity(addIntent);
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
