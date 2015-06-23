package iou.software.owesmoneyapp;

/**
 * Created by Sami on 22-06-2015.
 */
public class Transactions {

    //Fields
    private Person takes, owes;
    private int transaction;

    public Transactions(Person owes, Person takes, int transaction) {
        this.owes = owes;
        this.takes = takes;
        this.transaction = transaction;
    }

    public Person getOwes() {
        return owes;
    }

    public void setOwes(Person owes) {
        this.owes = owes;
    }

    public Person getTakes() {
        return takes;
    }

    public void setTakes(Person takes) {
        this.takes = takes;
    }

    public int getTransaction() {
        return transaction;
    }

    public void setTransaction(int transaction) {
        this.transaction = transaction;
    }
}
