package chapter3;

@FunctionalInterface
public interface Exemptable {
    boolean isExempt(Account account);

    // Fails compilation (if we remove this line) because functional interfaces can only declare one abstract method
    //boolean anotherExemption(Account account);
}

