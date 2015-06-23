package iou.software.owesmoneyapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AddBillingActivity extends Activity {

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
    final Gson gson = new Gson();
    SharedPreferences pManager;
    private String mTitle;

    PersonListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        //Preperations for gson array
        pManager = PreferenceManager.getDefaultSharedPreferences(AddBillingActivity.this);
        billings = gson.fromJson(pManager.getString("BILLING", "[]"), new TypeToken<List<Billing>>() {
        }.getType());


        //Set the views
        mTotalAmountView = (TextView) findViewById(R.id.total_money);
        mTotalAmountView.setText("" + mTotalAmount);
        mAverageAmountView = (TextView) findViewById(R.id.average_money);
        mAverageAmountView.setText("" + mAverageAmount);
        mBillingNameView = (TextView) findViewById(R.id.billing_name_overview);
        mTitle = getIntent().getStringExtra(TITLE);
        mBillingNameView.setText(mTitle);
        mListView = (ListView) findViewById(R.id.listView);

        // Create a new PersonListAdapter
        mAdapter = new PersonListAdapter(getApplicationContext());

        //Make it listen and update total and average, maybe delete super.onChanged
        mAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                updateTotalAndAverageAmounts();
            }
        });

        // Put divider between personItems and FooterView
        mListView.setFooterDividersEnabled(true);


        // Inflate footerView for overview_footer_view.xml_view.xml file

        TextView footerView = (TextView) getLayoutInflater().inflate(R.layout.overview_footer_view, null);

        // Add footerView to ListView

        mListView.addFooterView(footerView);

        // Attach Listener to FooterView
        footerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // - Implement OnClick().
                Intent addIntent = new Intent(AddBillingActivity.this, AddPersonActivity.class);
                startActivityForResult(addIntent, ADD_PERSON_REQUEST);
            }
        });

        // Attach the adapter to ListView
        mListView.setAdapter(mAdapter);

        // OnClickListener summarize button, opens summary-activity

        final Button mSummarizeButton = (Button) findViewById(R.id.summarize_button);
        mSummarizeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // checks if there is any persons added to the billing
                // if there is none, show a toast
                // if there is any added, go to the summary activity

                if (mAdapter.getPersons().isEmpty() || mAdapter.getCount() == 1) {
                    Toast.makeText(getApplicationContext(), R.string.no_persons_toast, Toast.LENGTH_LONG).show();
                } else {

                    Billing newBilling = new Billing(mTitle, false, mAdapter.getPersons());

                    // we package the billing as a JSON string
                    String json = new Gson().toJson(newBilling);

                    Intent data = new Intent();
                    Billing.packageIntent(data, json);

                    // sends the JSON string back to the main activity
                    setResult(RESULT_OK, data);

                    finish();
                }


            }
        });
    }

    // Makes data into people and add them to the adapters array
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_PERSON_REQUEST && resultCode == RESULT_OK) {

            Person person = new Person(data);

            mAdapter.add(person);

            updateTotalAndAverageAmounts();

        }
    }

    private void updateTotalAndAverageAmounts() {

        //Calculates total and mean values, to be removed
        mTotalAmount = 0;
        mAverageAmount = 0;

        if (mAdapter.getCount() > 0) {
            for (int i = 0; i < mAdapter.getCount(); i++) {
                mTotalAmount += mAdapter.getItem(i).getAmountPaid();
            }
            mAverageAmount = mTotalAmount / mAdapter.getCount();
        }

        //Update the views
        mTotalAmountView.setText("" + mTotalAmount);
        mAverageAmountView.setText("" + mAverageAmount);
    }

    @Override
    public void onResume() {

        super.onResume();
        updateTotalAndAverageAmounts();

    }

    //PersonListAdapter Class

    public class PersonListAdapter extends BaseAdapter {

        private final ArrayList<Person> persons = new ArrayList<>();
        private final Context mContext;

        public PersonListAdapter(Context context) {

            mContext = context;

        }


        public ArrayList<Person> getPersons() {
            return persons;
        }

        // Add a PersonItem to the adapter
        // Notify observers that the data set has changed

        public void add(Person person) {

            persons.add(person);
            notifyDataSetChanged();

        }

        public void remove(Person person) {

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
            //  Get the current Item
            final Person person = getItem(position);

            //  Inflate the View for this Item
            // from person_item.xml
            RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.
                    from(mContext).inflate(R.layout.person_item_overview, parent, false);
            // Fill in specific item data
            // Remember that the data that goes in this View
            // corresponds to the user interface elements defined
            // in the layout file

            // Display title in TextView
            final TextView nameView = (TextView) itemLayout.findViewById(R.id.person_name);
            nameView.setText(person.getPersonName());

            //  Display amount paid in TextView
            final TextView moneyView = (TextView) itemLayout.findViewById(R.id.person_money);
            moneyView.setText(Integer.toString(person.getAmountPaid()));

            // Display the edit button and set the on click listener
            final Button editButton = (Button) itemLayout.findViewById(R.id.edit_button);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Implement OnClick().
                    Toast.makeText(mContext, R.string.yet_to_be_added, Toast.LENGTH_SHORT).show();

                }
            });

            // Display the delete button and set the on click listener
            final Button deleteButton = (Button) itemLayout.findViewById(R.id.delete_button);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    remove(person);

                }
            });

            // Return the View you just created
            return itemLayout;

        }
    }
}


