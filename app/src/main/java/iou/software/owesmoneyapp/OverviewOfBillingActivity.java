package iou.software.owesmoneyapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class OverviewOfBillingActivity extends Activity {

    private static final String TAG = "ActivityOverview";
    public final static String TITLE = "title";

    private static final int ADD_PERSON_REQUEST = 1;

    private static final int MENU_DELETE = Menu.FIRST;
    private static final int MENU_BACK = Menu.FIRST + 1;

    private static int mTotalAmount = 0;
    private static int mAverageAmount = 0;

    private static TextView mTotalAmountView;
    private static TextView mAverageAmountView;
    private static TextView mBillingNameView;
    private ListView mListView;

    List<Billing> billings;
    Billing mBilling;
    final Gson gson = new Gson();
    SharedPreferences pManager;

    PersonListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //Preperations for gson array
        pManager = PreferenceManager.getDefaultSharedPreferences(OverviewOfBillingActivity.this);
        billings = gson.fromJson(pManager.getString("BILLING","[]"), new TypeToken<List<Billing>>() {}.getType());


        mTotalAmountView = (TextView) findViewById(R.id.total_money);
        mTotalAmountView.setText(""+mTotalAmount);
        mAverageAmountView = (TextView) findViewById(R.id.average_money);
        mAverageAmountView.setText(""+mAverageAmount);
        mBillingNameView = (TextView) findViewById(R.id.billing_name_overview);
        mBillingNameView.setText(getIntent().getStringExtra(TITLE));
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
                Intent addIntent = new Intent(OverviewOfBillingActivity.this,AddPersonActivity.class);
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
                Billing newBilling = new Billing(TITLE,true,mAdapter.getPersons());

                   //Saves the billing into the array.
                    billings.add(newBilling);
                    String s = gson.toJson(billings);
                    pManager.edit().putString("BILLING",s).apply();
                    Log.i(TAG, "Saved items");



                Intent addIntent = new Intent(OverviewOfBillingActivity.this,SummaryActivity.class);
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

            /*Person item = new Person("Peter", "444444", 190);
            for(int i = 0; i < 3 ; i++){
                mAdapter.add(item);
            }*/

            Person person = new Person(data);

            mAdapter.add(person);

            updateTotalAndAverageAmounts();

            Log.i(TAG, "Ending onActivityResult()");
            Log.i(TAG, ""+mAdapter.getCount());
        }
    }

    private void updateTotalAndAverageAmounts(){

        //Calculates total and mean values, to be removed
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
        mTotalAmountView.setText("" + mTotalAmount);
        mAverageAmountView.setText("" + mAverageAmount);
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





    }

    //PersonListAdapter

    public class PersonListAdapter extends BaseAdapter {

        private final ArrayList<Person> persons = new ArrayList<>();
        private final Context mContext;
        private static final int ADD_PERSON_REQUEST = 1;

        private static final String TAG = "ActivityOverview";

        public PersonListAdapter(Context context) {

            mContext = context;

        }


        public ArrayList<Person> getPersons(){
            return persons;
        }

        // Add a PersonItem to the adapter
        // Notify observers that the data set has changed

        public void add(Person person) {

            persons.add(person);
            notifyDataSetChanged();

        }

        // Clears the list adapter of all items.

        public void clear() {

            persons.clear();
            notifyDataSetChanged();

        }

        public void remove(Person person){

            persons.remove(person);
            notifyDataSetChanged();

        }

        // Returns the number of PersonItems

        @Override
        public int getCount() {

            return persons.size();

        }

        // Retrieve the number of PersonItems

        @Override
        public Person getItem(int pos) {

            return persons.get(pos);

        }

        // Get the ID for the Person
        // In this case it's just the position

        @Override
        public long getItemId(int pos) {

            return pos;

        }

        // Create a View for the PersonItem at specified position

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //  - Get the current Item
            final Person person = getItem(position);

            //  - Inflate the View for this Item
            // from person_item.xml
            RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.
                    from(mContext).inflate(R.layout.person_item_overview,parent,false);
            // Fill in specific item data
            // Remember that the data that goes in this View
            // corresponds to the user interface elements defined
            // in the layout file

            //  - Display title in TextView
            final TextView nameView = (TextView) itemLayout.findViewById(R.id.person_name);
            nameView.setText(person.getPersonName());

            //  - Display amount paid in TextView
            final TextView moneyView = (TextView) itemLayout.findViewById(R.id.person_money);
            moneyView.setText(Integer.toString(person.getAmountPaid()));

            //Display the edit button and set the on click listener
            final Button editButton = (Button) itemLayout.findViewById(R.id.edit_button);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // - Implement OnClick().
                    Intent addIntent = new Intent(OverviewOfBillingActivity.this,AddPersonActivity.class);
                    startActivityForResult(addIntent, ADD_PERSON_REQUEST);

                    Log.i(TAG, "onClick for the edit button");

                }
            });

            //Display the delete button and set the on click listener
            final Button deleteButton = (Button) itemLayout.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    remove(person);
                    Log.i(TAG, "onClick for the delete button");

                }
            });

            // Return the View you just created
            return itemLayout;

        }



    }


}


