package chapter8;

import java.util.function.Function;

public class Decoration {
    private static void functionalComposition() {
        Function<Object, String> decoratedFunction = o -> o + ", then do this first";
        Function<Object, String> function = o -> o + ", then do this after";

        System.out.println(function.compose(decoratedFunction).apply("Start"));
        System.out.println(function.andThen(decoratedFunction).apply("Start"));
    }

    private static void functionalCompositionReversed() {
        Function<Object, String> decoratedFunction = o -> o + ", then do this first";
        Function<Object, String> function = o -> o + ", then do this after";

        System.out.println(function.compose(decoratedFunction).apply("Start again"));
        System.out.println(decoratedFunction.andThen(function).apply("Start again"));
    }

    private static interface Component {
        public String doAction(String string);
    }

    private static class ConcreteComponent implements Component {
        private final Component decoratedComponent;

        private ConcreteComponent(Component decorator) {
            decoratedComponent = decorator;
        }

        @Override
        public String doAction(String string) {
            return string + ", then ConcreteComponent did something" + decoratedComponent.doAction("");
        }
    }

    private static class DecoratedComponent implements Component {
        @Override
        public String doAction(String string) {
            return string + ", then DecoratedComponent did something else";
        }
    }

    public static void main(String... args) {
        Component component = new ConcreteComponent(new DecoratedComponent());
        System.out.println(component.doAction("Start"));
        functionalComposition();
        functionalCompositionReversed();
    }
}
