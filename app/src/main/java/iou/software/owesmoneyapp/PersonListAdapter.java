package iou.software.owesmoneyapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PersonListAdapter extends BaseAdapter {

	private final List<Person> mItems = new ArrayList<>();
	private final Context mContext;
	private static final int ADD_PERSON_REQUEST = 0;

	private static final String TAG = "ActivityOverview";

	public PersonListAdapter(Context context) {

		mContext = context;

	}

	// Add a PersonItem to the adapter
	// Notify observers that the data set has changed

	public void add(Person person) {

		mItems.add(person);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	public void remove(Person person){

		mItems.remove(person);
		notifyDataSetChanged();

	}

	// Returns the number of PersonItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of PersonItems

	@Override
	public Person getItem(int pos) {

		return mItems.get(pos);

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
		RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.person_item,parent,false);
		// Fill in specific item data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file

		//  - Display Title in TextView
		final TextView nameView = (TextView) itemLayout.findViewById(R.id.person_name);
		nameView.setText(person.getPersonName());

		//  - Display Title in TextView
		final TextView moneyView = (TextView) itemLayout.findViewById(R.id.person_money);
		moneyView.setText("" + person.getAmountPaid());

		//Display the edit button and set the on click listener
		final Button editButton = (Button) itemLayout.findViewById(R.id.edit_button);

		editButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

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
