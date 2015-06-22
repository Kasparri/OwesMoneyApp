package iou.software.owesmoneyapp;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sami on 21-06-2015.
 */
public class ComplexAlgorithm {

    public static void main(String[] args) {
        ArrayList<Person> friends = new ArrayList<Person>();
        friends.add(new Person("Sami", "51147616", 0));
        friends.add(new Person("Maibohm", "51147616", 0));
        friends.add(new Person("August", "51147617", 3));

        ComplexAlgorithm complex = new ComplexAlgorithm();

        complex.whoOwesWhoTakes(friends);

//		System.out.println(complex.takesMoney);
//		System.out.println(complex.givesMoney);
//		System.out.println(complex.balanceIsZero);

        complex.calculateTransactions(friends);
        System.out.println(complex.transactions);
    }

    //Field
    private ArrayList<Person> friends;
    private ArrayList<Person> takesMoney;
    private ArrayList<Person> givesMoney;
    private ArrayList<Person> balanceIsZero;
    private ArrayList<String> transactions;
    private ArrayList<Transactions> transactions1;

    public void testdata() {
        this.friends = new ArrayList<>();
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

        this.takesMoney = new ArrayList<>();
        this.givesMoney = new ArrayList<>();
        this.balanceIsZero = new ArrayList<>();

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

        this.transactions = new ArrayList<>();

        whoOwesWhoTakes(friendsPayment);

        while (!(takesMoney.isEmpty() || givesMoney.isEmpty()) ) {
            for (int i = 0; i < takesMoney.size(); i++) {
                for (int j = 0; j < givesMoney.size(); j++) {
                    Person taker = takesMoney.get(i);
                    Person giver = givesMoney.get(j);
                    int diff = taker.getAmountPaid() + giver.getAmountPaid();
                    System.out.println(diff);
                    if (diff == 0) {
                        transactions.add(giver.getPersonName() + " owes " + giver.getAmountPaid() +
                                " to " + taker.getPersonName());
                        transactions1.add(new Transactions(giver,taker,giver.getAmountPaid()));
                        takesMoney.remove(i);
                        givesMoney.remove(j);

                    }
                }

            }
            //break if one of the lists is empty
            if (takesMoney.isEmpty() || givesMoney.isEmpty()) {
                break;
            }

            // takesMost = lowest balance, givesMost = highest balance
            Person takesMost = takesMoney.get(takesMoney.size()-1);
            Person givesMost = givesMoney.get(0);

            int diff = takesMost.getAmountPaid() + givesMost.getAmountPaid();

            //If takesMost needs more money than givesMost owes
            if ( diff < 0) {
                transactions.add(givesMost.getPersonName() + " owes " + givesMost.getAmountPaid() +
                        " to " + takesMost.getPersonName());
                transactions1.add(new Transactions(givesMost,takesMost,givesMost.getAmountPaid()));

                takesMost.setAmountPaid(diff);
                Collections.sort(takesMoney);
                givesMoney.remove(givesMost);
            }

            //If givesMost owes more money than takesMost needs
            else {
                transactions.add(givesMost.getPersonName() + " owes " + (takesMost.getAmountPaid()*-1) +
                        " to " + takesMost.getPersonName());
                transactions1.add(new Transactions(givesMost,takesMost,takesMost.getAmountPaid()));
                givesMost.setAmountPaid(diff);
                Collections.sort(givesMoney);
                takesMoney.remove(takesMost);
            }

        }

    }

}


