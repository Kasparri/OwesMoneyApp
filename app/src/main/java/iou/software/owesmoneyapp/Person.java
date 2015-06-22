package iou.software.owesmoneyapp;

/**
 * Created by August on 19/06/15.
 */
public class Person implements Comparable {
    private String personName, phoneNumber;
    private int amountPaid;


    public Person(String personName, String phoneNumber, int amountPaid){
        this.personName = personName;
        this.phoneNumber = phoneNumber;
        this.amountPaid = amountPaid;
    }

    public String toString(){
        String outString = "( " + personName + ", " + phoneNumber + ", " + amountPaid + " )";
        return outString;
    }

    @Override
    public int compareTo(Object person){
        int compareAmountPaid = ((Person)person).getAmountPaid();
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

}
