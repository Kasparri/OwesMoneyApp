package iou.software.owesmoneyapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

    private AddBillingListAdapter mAdapter;
    private static final int ADD_BILLING_ITEM_REQUEST = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a new AddBillingListAdapter for this ListActivity's ListView
        mAdapter = new AddBillingListAdapter(getApplicationContext());


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

                Intent intent = new Intent(MainActivity.this, OverviewOfBillingActivity.class);
                startActivityForResult(intent, ADD_BILLING_ITEM_REQUEST);

            }
        });

        // Attach the adapter to this ListActivity's ListView
        setListAdapter(mAdapter);

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
                    Billing.packageIntent(intent, billing.getTitle(), billing.getStatus());
                    startActivity(intent);
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
