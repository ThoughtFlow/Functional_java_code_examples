package chapter3;

@FunctionalInterface
public interface AccountCreator {
    Account create(String accountId);
}
