package iou.software.owesmoneyapp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by August on 19/06/15.
 */
public class AddPeopleActivity extends Activity {

    EditText mName;
    EditText mNumber;
    EditText mAmountPaid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_person);

        //Getting the EditText views
        mName = (EditText)findViewById(R.id.enter_name);
        mNumber = (EditText)findViewById(R.id.enter_number);
        mAmountPaid = (EditText)findViewById(R.id.enter_amount_paid);


        // Listener for the submit button.
        final Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new Button.OnClickListener() {


            @Override
            public void onClick(View v) {
                String emptyString = "";
                //Getting strings from TextEdits and checks if data is inserted.
                if (!(mName.getText().toString().equals(emptyString))  && !(mNumber.getText().toString().equals(emptyString))
                        && !(mAmountPaid.getText().toString().equals(emptyString))) {

                    String name = mName.getText().toString();
                    String number = mNumber.getText().toString();
                    //Getting the amountPaid as string.
                    String amountPaidString = mAmountPaid.getText().toString();
                    //Parsing the string to int.
                    int amountPaid = Integer.parseInt(amountPaidString);

                    //Creating the new person with the given data
                    //Person name = new Person(name,number,amountPaid);
                    Person newPerson = new Person(name, number, amountPaid);
                    Log.i("Hello", "Person created");
                    finish();
                }else {
                    //Tells user to fill in all fields in case the user didnt.
                    Toast.makeText(getApplicationContext(), "Please fill in all fields!", Toast.LENGTH_LONG).show();
                }
            }

        });



    }











}
