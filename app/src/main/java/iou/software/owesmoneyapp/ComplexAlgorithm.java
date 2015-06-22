package iou.software.owesmoneyapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sami on 21-06-2015.
 */
public class ComplexAlgorithm {

    //Field
    private ArrayList<Person> friends;
    private ArrayList<Person> takesMoney;
    private ArrayList<Person> givesMoney;
    private ArrayList<Person> balanceIsZero;
    private ArrayList<String> transactions;

    public void testdata() {
        this.friends = new ArrayList<Person>();
        friends.add(new Person("Sami", "51147616", 200));
        friends.add(new Person("August", "51147617", 100));
        friends.add(new Person("Engberg", "51147618", 0));
    }

    private int calculateTotal(ArrayList<Person> friends) {
        int sum = 0;
        if (!friends.isEmpty()) {
            for (int i = 0; i < friends.size(); i++) {
                sum += friends.get(i).getAmountPaid();
            }
        }
        return sum;
    }

    private int calculateMean(ArrayList<Person> friends) {
        return calculateTotal(friends) / friends.size();
    }

    @SuppressWarnings("unchecked")
    private void whoOwesWhoTakes(ArrayList<Person> friends) {

        int mean = calculateMean(friends);

        this.takesMoney = new ArrayList<Person>();
        this.givesMoney = new ArrayList<Person>();
        this.balanceIsZero = new ArrayList<Person>();

        for (int i = 0; i < friends.size(); i++) {
            Person person = friends.get(i);
            int balance = mean - person.getAmountPaid();
            person.setAmountPaid(balance);
            if (balance < 0) {
                takesMoney.add(person);
            } else if (balance > 0) {
                givesMoney.add(person);
            } else {
                balanceIsZero.add(person);
            }

        }
        Collections.sort(takesMoney);
        Collections.sort(givesMoney);
    }

    @SuppressWarnings("unchecked")
    public void calculateTransactions(ArrayList<Person> friendsPayment) {

        this.transactions = new ArrayList<String>();

        whoOwesWhoTakes(friendsPayment);

        while (!(takesMoney.isEmpty() || givesMoney.isEmpty()) ) {
            for (int i = 0; i < takesMoney.size(); i++) {
                for (int j = 0; j < givesMoney.size(); j++) {
                    Person taker = takesMoney.get(i);
                    Person giver = givesMoney.get(j);
                    int diff = taker.getAmountPaid() + giver.getAmountPaid();
                    if (diff == 0) {
                        transactions.add(giver.getPersonName() + " owes " + giver.getAmountPaid() +
                                " to " + taker.getPersonName());

                        takesMoney.remove(i);
                        givesMoney.remove(j);

                        if (takesMoney.isEmpty() || givesMoney.isEmpty()) {
                            break;
                        }

                    }
                }
            }

            // takesMost = lowest balance, givesMost = highest balance
            Person takesMost = takesMoney.get(takesMoney.size()-1);
            Person givesMost = givesMoney.get(0);

            int diff = takesMost.getAmountPaid() + givesMost.getAmountPaid();

            //If takesMost needs more money than givesMost owes
            if ( diff < 0) {
                transactions.add(givesMost.getPersonName() + " owes " + givesMost.getAmountPaid() +
                        " to " + takesMost.getPersonName());

                takesMost.setAmountPaid(diff);
                Collections.sort(takesMoney);
                givesMoney.remove(givesMost);
            }

            //If givesMost owes more money than takesMost needs
            else {
                transactions.add(givesMost.getPersonName() + " owes " + (takesMost.getAmountPaid()*-1) +
                        " to " + takesMost.getPersonName());
                givesMost.setAmountPaid(diff);
                Collections.sort(givesMoney);
                takesMoney.remove(takesMost);
            }

        }

    }

}


