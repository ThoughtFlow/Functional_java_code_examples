package chapter4;

public class LexicalScoping {
    private Object attribute;

    public void readWriteClassAttributes() {
        LambdaExecutor executor = anInteger -> attribute = anInteger;
        executor.execute(1);
        System.out.println(attribute);
    }

    public void readLocalVariable() {
        Object localAttribute = 1;

        LambdaExecutor executor = anInteger -> System.out.println("Accessing localAttribute: " + localAttribute);
        executor.execute(null);
    }

    public void writeLocalVariable() {
//        // localAttribute is non-final so compilation would fail
//        Object localAttribute;
//
//        // Illegal: cannot modify a local attribute from within a lambda
//        LambdaExecutor executor = anInteger -> localAttribute = anInteger;
//        executor.execute(1);
    }

    public void writeEffectivelyFinal() {
//      Object localAttribute = 1;
//
//      // localAttribute has just lost its effectively final status. It cannot be used within the lambda unless this statement is removed.
//      localAttribute = 1;  // Must to remove to make compilation pass
//      LambdaExecutor executor = anInteger -> System.out.println("Accessing localAttribute: " + localAttribute);  // Fails compilation
//      executor.execute(null);
    }

    public LambdaExecutor getLambdaExecutor()
    {
//        // Fails compilation
//        Object localAttribute = 1;
//
//        // Not legal because localAttribute cannot be modified
//        return anObject -> localAttribute = 2;
        return null;
    }

    public void shadowLocalVariables() {
//        Object localAttribute;
//
//        // The lambda parameter localAttribute is shadowing the local
//        // variable localAttribute and prohibited by the compiler
//        LambdaExecutor executor = localAttribute -> localAttribute = 1;
//        executor.execute(1);
    }

    public void shadowLocalVariablesAnonymous() {
        Object localAttribute;

        LambdaExecutor executor = new LambdaExecutor() {
            // localAttribute shadowing is legal for anonymous classes
            @Override
            public void execute(Object localAttribute) {
                // Do something
            }
        };
        executor.execute(1);
    }

    public void mutatingState() {
        attribute = new StringBuffer();

        // Inner state of attribute is mutated inside the lambda
        LambdaExecutor executor = aString -> ((StringBuffer) attribute).append(aString);
        executor.execute("another string");
        System.out.println(attribute);
    }

    public static void main(String... args) {
        LexicalScoping scoping = new LexicalScoping();

        scoping.readWriteClassAttributes();
        scoping.readLocalVariable();
        scoping.writeLocalVariable();
        scoping.writeEffectivelyFinal();
        scoping.getLambdaExecutor();
        scoping.shadowLocalVariables();
        scoping.shadowLocalVariablesAnonymous();
        scoping.mutatingState();

        // Certainly not legal!
        //(StringExecutor executor = s -> s + "There").execute("Hello");
    }
}
