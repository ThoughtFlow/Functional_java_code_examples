package chapter3;

public class SuperBanker {
    protected void onAccountExemption(Account account) {
        System.out.println("Account: " + account.getAccountId() + " was given an exemption.");
    }

    public void superAccountExemptionHandler(Account account) {
        System.out.println("Account: " + account.getAccountId() + " was given an exemption");
    }
}
