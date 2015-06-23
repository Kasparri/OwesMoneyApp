package iou.software.owesmoneyapp;

import android.content.Intent;

/**
 * Created by August on 19/06/15.
 */
public class Person implements Comparable {

    public final static String NAME = "name";
    public final static String NUMBER = "number";
    public final static String AMOUNT = "amount";

    private String personName, phoneNumber;
    private int amountPaid;


    public Person(String personName, String phoneNumber, int amountPaid) {
        this.personName = personName;
        this.phoneNumber = phoneNumber;
        this.amountPaid = amountPaid;
    }

    public Person(Intent intent) {
        personName = intent.getStringExtra(NAME);
        phoneNumber = intent.getStringExtra(NUMBER);
        amountPaid = intent.getIntExtra(AMOUNT, 0);
    }

    public String toString() {
        return "( " + personName + ", " + phoneNumber + ", " + amountPaid + " )";
    }

    @Override
    public int compareTo(Object person) {
        int compareAmountPaid = ((Person) person).getAmountPaid();
        return compareAmountPaid - this.amountPaid;
    }

    // Getters
    public String getPersonName() {
        return personName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getAmountPaid() {
        return amountPaid;
    }


    // Setters - So it is possible  to edit later.
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAmountPaid(int amountPaid) {
        this.amountPaid = amountPaid;
    }

    // Kasper
    // packages the intent with a name, phone number and the amount paid

    public static void packageIntent(Intent intent, String personName, String phoneNumber, int amountPaid) {

        intent.putExtra(NAME, personName);
        intent.putExtra(NUMBER, phoneNumber);
        intent.putExtra(AMOUNT, amountPaid);

    }

}
