package iou.software.owesmoneyapp;


import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import android.util.Log;
import android.view.MenuItem;

public class OverviewOfBillingActivity extends Activity {

    private static final String TAG = "ActivityOverview";

    private static final int ADD_PERSON_REQUEST = 0;

    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_BACK = Menu.FIRST + 1;

    private static int mTotalAmount = 0;
    private static int mAverageAmount = 0;

    private static TextView mTotalAmountView;
    private static TextView mAverageAmountView;
    private ListView mListView;

    PersonListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        mTotalAmountView = (TextView) findViewById(R.id.total_money);
        mTotalAmountView.setText(""+mTotalAmount);
        mAverageAmountView = (TextView) findViewById(R.id.average_money);
        mAverageAmountView.setText(""+mAverageAmount);
        mListView = (ListView) findViewById(R.id.listView);

        // Create a new PersonListAdapter
        mAdapter = new PersonListAdapter(getApplicationContext());

        //Make it listen and update total and average, maybe delete super.onChanged
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged(){
                super.onChanged();
                updateTotalAndAverageAmounts();
            }
        });

        // Put divider between personItems and FooterView
        mListView.setFooterDividersEnabled(true);

        //Load
        if (mAdapter.getCount() == 0){
            loadItems();
        }

        //  - Inflate footerView for overview_footer_view.xml_view.xml file

        TextView footerView = (TextView) getLayoutInflater().inflate(R.layout.overview_footer_view,null);

        //  - Add footerView to ListView

        mListView.addFooterView(footerView);

        //  - Attach Listener to FooterView
        footerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // - Implement OnClick().
                Intent addIntent = new Intent(OverviewOfBillingActivity.this,AddPeopleActivity.class);
                startActivityForResult(addIntent, ADD_PERSON_REQUEST);
            }
        });

        //  - Attach the adapter to ListView
        mListView.setAdapter(mAdapter);

        // OnClickListener summarize button, opens summary-activity

        final Button mSummarizeButton = (Button) findViewById(R.id.summarize_button);
        mSummarizeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(OverviewOfBillingActivity.this,AddPeopleActivity.class);
                startActivity(addIntent);
            }
        });
    }

    //For now just adds a lot of people to test,
    // will eventually make data into people and add them
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == ADD_PERSON_REQUEST && resultCode == RESULT_OK){
            Log.i(TAG, "Entered onActivityResult()");

            Person item = new Person("Peter", "444444", 190);
            for(int i = 0; i < 3 ; i++){
                mAdapter.add(item);
            }

            updateTotalAndAverageAmounts();

            Log.i(TAG, "Ending onActivityResult()");
        }
    }

    private void updateTotalAndAverageAmounts(){

        //Calculates total and mean values, to be removeds
        mTotalAmount = 0;
        mAverageAmount = 0;

        if(mAdapter.getCount() > 0){
            for(int i = 0; i < mAdapter.getCount(); i++){
                mTotalAmount += mAdapter.getItem(i).getAmountPaid();
            }
            mAverageAmount = mTotalAmount/mAdapter.getCount();
        }

        Log.i("TAG", ""+mAdapter.getCount());

        //Update the views
        mTotalAmountView.setText(""+mTotalAmount);
        mAverageAmountView.setText(""+mAverageAmount);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Load saved PersonItems, if necessary

        if (mAdapter.getCount() == 0){
            loadItems();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Save PersonItems

        saveItems();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all");
        menu.add(Menu.NONE, MENU_BACK, Menu.NONE, "Go back");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_DELETE:
                Log.i(TAG, "Cleared");
                mAdapter.clear();
                return true;
            case MENU_BACK:
                Log.i(TAG, "Went Back");
                setResult(RESULT_CANCELED);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Load stored PersonItems
    private void loadItems() {
        Log.i(TAG, "Loaded items");

    }

    // Save PersonItems to file
    private void saveItems() {
        Log.i(TAG, "Saved items");
    }
}
