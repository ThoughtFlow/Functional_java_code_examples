package chapter2;

public class Banker {

    // First version
    public boolean isBalanceSufficient(Account account, double amount) {
        logAccess(account);
        boolean isBalanceSufficient = account.getBalance() - amount > 0;

        if (!isBalanceSufficient) {
            // It would be nice to let the caller vary this condition
            if (account.getCreditRating() > 700) {
                isBalanceSufficient = true;
                alertOverdraw(account);
            }
        }

        return isBalanceSufficient;
    }

    // Second version with refactored isBalanceSufficient() with separate doOverdraft() method
//    public boolean isBalanceSufficient(Account account, double amount)
//    {
//        logAccess(account);
//        return account.getBalance() - amount > 0;
//    }

//    public void doOverdraft(Account account, double amount)
//    {
//        alertOverdraw(account);
//    }

    // Third version with Exemptable interface
    public boolean isBalanceSufficient(Account account, double amount, Exemptable ex) {
        logAccess(account);
        boolean isBalanceSufficient = account.getBalance() - amount > 0;

        if (!isBalanceSufficient) {
            // Now, we can let the caller vary the condition
            if (ex.isExempt(account)) {
                isBalanceSufficient = true;
                alertOverdraw(account);
            }
        }

        return isBalanceSufficient;
    }

    private void logAccess(Account account) {
        System.out.println("Account has been accessed: " + account.getAccountId());
    }

    private void alertOverdraw(Account account) {
        System.out.println("Account has been overdrawn: " + account.getAccountId());
    }

    public static void main(String... args) {

        // Call first version
        Banker banker = new Banker();
        Account anAccount = new Account("10");
        double anAmount = 10;
        System.out.println("Is balance sufficient: " + banker.isBalanceSufficient(anAccount, anAmount));

        // Call second version
//        if (!isBalanceSufficient(anAccount, anAmount))
//        {
//            // Caller can now vary the condition and control the flow
//            if (anAccount.getCreditRating() > 750)
//            {
//                doOverdraft(anAccount, anAmount);
//            }
//        }

        // Call third version with anonymous class
        banker.isBalanceSufficient(anAccount, anAmount,
                                   new Exemptable() {
                                       @Override
                                       public boolean isExempt(Account account) {
                                           return account.getCreditRating() > 700;
                                       }
                                   });

        // Lambda: form 1
        // Our very first Java lambda!
        Exemptable ex = (Account acc) ->  {return acc.getCreditRating() > 700;};
        banker.isBalanceSufficient(anAccount, anAmount, ex);

        // Lambda: form 2
        ex = (Account acc) -> acc.getCreditRating() > 700;
        banker.isBalanceSufficient(anAccount, anAmount, ex);

        // Lambda: form 3
        ex = (acc) -> acc.getCreditRating() > 700;
        banker.isBalanceSufficient(anAccount, anAmount, ex);

        // Lambda: form 4
        ex = acc -> acc.getCreditRating() > 700;
        banker.isBalanceSufficient(anAccount, anAmount, ex);

        // Lambda: form 5
        banker.isBalanceSufficient(anAccount, anAmount, account -> account.getCreditRating() > 700);
    }
}
