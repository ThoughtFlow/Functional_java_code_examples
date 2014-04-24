package chapter3;

import java.util.LinkedList;
import java.util.List;

public class Banker extends SuperBanker {
    public boolean isBalanceSufficient(Account account, double amount, Exemptable exemptable, AccountExemptionHandler handler) {
        logAccess(account);
        boolean isBalanceSufficient = account.getBalance() - amount > 0;

        if (!isBalanceSufficient) {
            // Now, we can let the caller vary the condition
            if (exemptable.isExempt(account)) {
                isBalanceSufficient = true;

                // Give caller the opportunity to do something extra
                handler.onAccountExempted(account);
            }
        }

        return isBalanceSufficient;
    }

    private void logAccess(Account account) {
        System.out.println("Account was accessed: " + account.getAccountId());
    }

    public List<Account> makeDefaultAccounts(int count, AccountCreator accountCreator, ListCreator<List<Account>> listCreator) {
        List<Account> returnList = listCreator.create();
        for (int index = 0; index < count; ++index) {
            returnList.add(accountCreator.create("default"));
        }

        return returnList;
    }

    public Account[] makeArrayDefaultAccounts(int count, AccountCreator accountCreator, AccountArrayCreator accountArrayCreator) {
        Account[] returnList = accountArrayCreator.create(count);

        for (int index = 0; index < count; ++index) {
            returnList[index] = accountCreator.create("default");
        }

        return returnList;
    }

    public static boolean defaultExemption(Account account) {
        return account.getCreditRating() > 700;
    }

    public void instanceAccountExemptionHandler(Account account) {
        System.out.println("Account: " + account.getAccountId() + " was given an exemption");
    }

    public void runTheBank(Account account, double amount) {

        // Anonymous class implementation of AccountExemptionHandler
        AccountExemptionHandler  anonymousAccountExemptionHandler  =
            new AccountExemptionHandler ()
            {
                @Override
                public void onAccountExempted(Account account)
                {
                    System.out.println(account);
                }
            };
        // Call with anonymous class
        isBalanceSufficient(account, amount, acc -> acc.getCreditRating() > 700, anonymousAccountExemptionHandler);

        // Call with lambda
        isBalanceSufficient
            (account, amount,
             acc -> acc.getCreditRating() > 700,
             acc -> System.out.println(account));

        // Call with method reference
        isBalanceSufficient
            (account, amount,
             acc -> acc.getCreditRating() > 700,
             // The lambda has been replaced by a method reference
             System.out::println);

        // Call with defaultExemption
        isBalanceSufficient
            (account, amount,
             // Refer to a static method within the Banker class.
             // Replaces:  a -> Banker::defaultExemption (a)
             Banker::defaultExemption,
             System.out::println);

        // Call with static and method references
        isBalanceSufficient
            (account, amount,
             Banker::defaultExemption,  // Static method reference
             // Instance method reference
             this::instanceAccountExemptionHandler);

        // Call using superAccountExemptionHandler
        isBalanceSufficient
            (account, amount,
             Banker::defaultExemption,
             // Refer to super AccountExemptionHandler in the parent class
             super::superAccountExemptionHandler);

        // Call makeDefaultAccounts
        makeDefaultAccounts
            (10,
             // These do nothing more than bridge to other methods
             id -> new Account(id),
             () -> new LinkedList<Account>());

        // Call using constructor references
        makeDefaultAccounts
            (10,
             Account::new,              // Constructor reference
             LinkedList<Account>::new); // Generic constructor reference

        // Call without using array constructor
        makeArrayDefaultAccounts
            (10, Account::new, size -> new Account[size]);

        // Call using array constructor
        makeArrayDefaultAccounts(10, Account::new, Account[]::new);
    }

    public static void main(String... args) {
        Banker bankerInstance = new Banker();
        double amount = 10;
        Account account = new Account("1", 700, 0);

        // This is how method references are used outside of the class
        bankerInstance.isBalanceSufficient
            (account, amount,
             Banker::defaultExemption,
             bankerInstance::instanceAccountExemptionHandler);
    }
}
