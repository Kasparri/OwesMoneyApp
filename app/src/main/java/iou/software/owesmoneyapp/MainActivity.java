package iou.software.owesmoneyapp;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kasper on 19/06/15.
 */
public class MainActivity extends ListActivity {

    public final static String TITLE = "title";
    public final static String JSON = "JSON";

    private AddBillingListAdapter mAdapter;
    private static final int ADD_BILLING_ITEM_REQUEST = 0;
    private List<Billing> billings;
    private Context mContext;

    final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // preperations for gson array
        SharedPreferences pManager = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        billings = gson.fromJson(pManager.getString("BILLING","[]"), new TypeToken<List<Person>>() {}.getType());


        mContext = getApplicationContext();

        // Create a new AddBillingListAdapter for this ListActivity's ListView
        mAdapter = new AddBillingListAdapter(mContext);


        // Inflate footerView for activity_main.xml file

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.footer_view, getListView(), false);
        TextView footerView = (TextView) row.findViewById(R.id.add_new_billing);


        // Add footerView to ListView

        getListView().addFooterView(footerView);

        // Attach Listener to FooterView
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // makes a popup
                AlertDialog.Builder popup = new AlertDialog.Builder(MainActivity.this);


                // sets its title and message
                popup.setTitle(R.string.popup_title);
                popup.setMessage(R.string.popup_message);


                // makes the EditText field that is to be added to the popup window
                final EditText titleName = new EditText(mContext);
                titleName.setTextColor(Color.BLACK);
                titleName.setHint(R.string.popup_textEdit_hint);
                titleName.setSingleLine(true);

                // adds it
                popup.setView(titleName);


                // sets the Confirm button

                popup.setPositiveButton(R.string.popup_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // sets a default name, if no name is entered

                        if (titleName.getText().toString().isEmpty()){
                            titleName.setText(R.string.popup_default_name);
                        }

                        // starts Overview of Billing activity

                        Intent intent = new Intent(MainActivity.this, OverviewOfBillingActivity.class);
                        intent.putExtra(TITLE, titleName.getText().toString());
                        startActivityForResult(intent, ADD_BILLING_ITEM_REQUEST);
                    }
                });


                // sets the cancel button

                popup.setNegativeButton(R.string.popup_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // canceled, do nothing

                    }
                });


                // shows the popup
                popup.show();



            }
        });

        // Attach the adapter to this ListActivity's ListView
        setListAdapter(mAdapter);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_BILLING_ITEM_REQUEST && resultCode == RESULT_OK){

            // getting the JSON string from the intent
            String json = data.getStringExtra(JSON);

            // retrieving the billing from the JSON string
            Billing billing = new Gson().fromJson(json,Billing.class);

            // adding the billing to the view
            mAdapter.add(billing);

            // making a new intent that redirects the user to the summary activity
            Intent toSummary = new Intent(MainActivity.this, SummaryActivity.class);

            // adds the json string to the intent
            toSummary.putExtra(JSON,json);

            // starts the summary activity with the 'toSummary' intent
            startActivity(toSummary);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar billing_item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

    }

    private void updateView(){
        mAdapter.notifyDataSetChanged();
    }


    // internal ListAdapter class

    public class AddBillingListAdapter extends BaseAdapter {

        private final List<Billing> mBillings = new ArrayList<>();
        private final Context mContext;


        public AddBillingListAdapter(Context context) {
            mContext = context;
        }


        // Add a Billing to the adapter
        // Notify observers that the data set has changed

        public void add(Billing billing) {
            mBillings.add(billing);
            notifyDataSetChanged();
        }


        // Clears the list adapter of all billings.

        public void clear() {
            mBillings.clear();
            notifyDataSetChanged();
        }

        public void remove(Billing billing) {

            mBillings.remove(billing);
            notifyDataSetChanged();

        }

        // Returns the number of billings

        @Override
        public int getCount() {
            return mBillings.size();
        }


        // Retrieve the Billing of a certain number

        @Override
        public Object getItem(int pos) {
            return mBillings.get(pos);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        // Create a View for the Billing at specified position

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Billing billing = mBillings.get(position);

            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.billing_item, parent, false);

            LinearLayout itemLayout = (LinearLayout) convertView;

            // Fill in specific Billing data
            // Remember that the data that goes in this View
            // corresponds to the user interface elements defined
            // in the layout file

            final TextView titleView = (TextView) itemLayout.findViewById(R.id.billing_name);
            titleView.setText(billing.getTitle());
            titleView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SummaryActivity.class);

                    String json = new Gson().toJson(billing);

                    intent.putExtra(JSON, json);

                    startActivity(intent);
                }

            });

            final Button deleteButton = (Button) itemLayout.findViewById(R.id.delete_billing);
            deleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    Toast.makeText(mContext,billing.getTitle() + " was removed", Toast.LENGTH_SHORT).show();
                    mAdapter.remove(billing);

                }
            });


            final CheckBox billingStatus = (CheckBox) itemLayout.findViewById(R.id.billing_checkbox);
            billingStatus.setChecked(billing.getStatus());


            // Must also set up an OnCheckedChangeListener,
            // which is called when the user toggles the billing-status checkbox

            billingStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {

                    billingStatus.setChecked(isChecked);

                }
            });

            // Return the View you just created
            return itemLayout;

        }

    }
}
