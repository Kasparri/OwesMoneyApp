package iou.software.owesmoneyapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


/**
 * Created by August on 19/06/15.
 */
public class AddPersonActivity extends Activity {

    EditText mName;
    EditText mNumber;
    EditText mAmountPaid;
    List<Person> persons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_person);


        //Getting the EditText views
        mName = (EditText)findViewById(R.id.enter_name);
        mNumber = (EditText)findViewById(R.id.enter_number);
        mAmountPaid = (EditText)findViewById(R.id.enter_amount_paid);

        final Gson gson = new Gson();


        // Listener for the submit button.
        final Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new Button.OnClickListener() {


            @Override
            public void onClick(View v) {
                String emptyString = "";
                SharedPreferences pManager = PreferenceManager.getDefaultSharedPreferences(AddPersonActivity.this);
                persons = gson.fromJson(pManager.getString("PERSON","[]"), new TypeToken<List<Person>>() {}.getType());
                //Getting strings from TextEdits and checks if data is inserted.
                if (!(mName.getText().toString().equals(emptyString))  && !(mNumber.getText().toString().equals(emptyString))
                        && !(mAmountPaid.getText().toString().equals(emptyString))) {

                    String name = mName.getText().toString();
                    String number = mNumber.getText().toString();
                    //Getting the amountPaid as string.
                    String amountPaidString = mAmountPaid.getText().toString();
                    //Parsing the string to int.
                    int amountPaid = Integer.parseInt(amountPaidString);


                   /*
                    Gson stuff
                    persons.add(newPerson);
                    String  s = gson.toJson(persons);
                    pManager.edit().putString("PERSON",s).apply();
                    Log.i("Hello", "Person created");
                    */


                    // packages an intent with the data acquired
                    Intent data = new Intent();
                    Person.packageIntent(data,name,number,amountPaid);
                    setResult(RESULT_OK, data);

                    finish();

                }else {
                    //Tells user to fill in all fields in case the user didn't.
                    Toast.makeText(getApplicationContext(), R.string.add_person_toast_message, Toast.LENGTH_LONG).show();
                }
            }

        });



    }











}
