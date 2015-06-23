package iou.software.owesmoneyapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;


/**
 * Created by August on 19/06/15.
 */
public class AddPersonActivity extends Activity {

    EditText mName;
    EditText mNumber;
    EditText mAmountPaid;
    List<Person> persons;
    Cursor cursorNames;
    Cursor cursorNumbers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_person);


        //Getting the EditText views
        mName = (EditText) findViewById(R.id.enter_name);
        mNumber = (EditText) findViewById(R.id.enter_number);
        mAmountPaid = (EditText) findViewById(R.id.enter_amount_paid);

        final Gson gson = new Gson();


        // Listener for the submit button.
        final Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new Button.OnClickListener() {


            @Override
            public void onClick(View v) {
                String emptyString = "";

                //Getting strings from TextEdits and checks if data is inserted.
                if (!(mName.getText().toString().equals(emptyString)) && !(mNumber.getText().toString().equals(emptyString))
                        && !(mAmountPaid.getText().toString().equals(emptyString))) {

                    String name = mName.getText().toString();
                    String number = mNumber.getText().toString();
                    //Getting the amountPaid as string.
                    String amountPaidString = mAmountPaid.getText().toString();
                    //Parsing the string to int.
                    int amountPaid = Integer.parseInt(amountPaidString);


                    // Kasper
                    // packages an intent with the data acquired

                    Intent data = new Intent();
                    Person.packageIntent(data, name, number, amountPaid);
                    setResult(RESULT_OK, data);

                    finish();

                } else {
                    //Tells user to fill in all fields in case the user didn't.
                    Toast.makeText(getApplicationContext(), R.string.add_person_toast_message, Toast.LENGTH_LONG).show();
                }
            }

        });
        final Button contactsButton = (Button) findViewById(R.id.contact_button);
        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactIntent, 1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                ContentResolver content = getContentResolver();
                String[] projection = new String[]{ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.Contacts._ID};
                cursorNames = content.query(data.getData(),projection,null,null,null);
                if (cursorNames.moveToFirst()) {
                    String name = cursorNames.getString(cursorNames.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String contactID = cursorNames.getString(cursorNames.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                    mName.setText(name, TextView.BufferType.EDITABLE);

                    cursorNumbers = content.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = " + contactID,null,null);
                    if(cursorNumbers.moveToFirst()) {
                        String phoneNumber = cursorNumbers.getString(cursorNumbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        mNumber.setText(phoneNumber, TextView.BufferType.EDITABLE);

                    }

                }
            }
        }
    }


}
