package iou.software.owesmoneyapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ListActivity {

    private AddBillingListAdapter mAdapter;
    private static final int ADD_BILLING_ITEM_REQUEST = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new AddBillingListAdapter for this ListActivity's ListView
        mAdapter = new AddBillingListAdapter(getApplicationContext());



        // Inflate footerView for activity_main.xml file

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.footer_view,getListView(),false);
        TextView footerView = (TextView) row.findViewById(R.id.add_new_billing);


        // Add footerView to ListView

        getListView().addFooterView(footerView);

        // Attach Listener to FooterView
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Implement OnClick().

                Intent intent = new Intent(MainActivity.this, OverviewOfBillingActivity.class);
                startActivityForResult(intent,ADD_BILLING_ITEM_REQUEST);

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
}
