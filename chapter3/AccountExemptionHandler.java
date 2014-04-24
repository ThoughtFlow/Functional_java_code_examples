package chapter3;

@FunctionalInterface
public interface AccountExemptionHandler {
    public void onAccountExempted(Account account);
}
