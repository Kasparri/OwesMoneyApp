package iou.software.owesmoneyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.*;

/**
 * Created by Kasper on 19/06/15.
 */
public class AddBillingListAdapter extends BaseAdapter {

    private final List<Billing> mBillings = new ArrayList<Billing>();
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
        titleView.setText(Billing.getTitle());


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
