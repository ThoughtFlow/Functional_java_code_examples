package chapter5;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Supplier;

public class AreaCalculator {
    public static double computeShapeArea
        (int angles, double measure1, double measure2,
         Consumer<String> consumer) {
        double area;
        switch (angles) {
            case 0: {
                // Circle: measure1 is radius
                area = Math.PI * Math.pow(measure1, 2);
                consumer.accept("Area formula for shape with " + angles +
                                    " angles is pi times radius: " + Math.PI +
                                    " times " + measure1 + " squared = " + area);
                break;
            }
            case 3: {
                // Triangle: measure1 is base, measure2 is height
                area = measure1 * measure2 / 2;
                consumer.accept("Area formula for shape with " + angles +
                                    " angles is height times base divided by 2: " +
                                    measure1 + " times " + measure2 +
                                    " divided by 2 = " + area);
                break;
            }
            case 4: {
                // Rectangle or square: measure1 is width, measure2 is length
                area = measure1 * measure2;
                consumer.accept("Area formula for shape with " + angles +
                                    " angles is width times length: " + measure1 +
                                    " times " + measure2 + " = " + area);
                break;
            }
            default: {
                throw new RuntimeException("Unsupported shape with " + angles
                                               + " angles");
            }
        }

        return area;
    }

    public static double computeShapeArea(double measure1, double measure2, BiFunction<Double, Double, Double> function) {
        return function.apply(measure1, measure2);
    }

    public static double computeShapeArea(double measure1, double measure2, DoubleBinaryOperator function, BiPredicate<Double, Double> roundUpPredicate) {
        double area = function.applyAsDouble(measure1, measure2);
        return roundUpPredicate.test(measure1, measure2) ? Math.rint(area) : area;
    }

    public static double computeShapeArea(Supplier<Double> measure1, Supplier<Double> measure2, DoubleBinaryOperator function) {
        // Determine measure1 and measure2 from Supplier
        return function.applyAsDouble(measure1.get(), measure2.get());
    }

    public static void main(String... args) {
        // Caller code for Consumer-based computeShapeArea
        computeShapeArea(0, 7, 0, System.out::println);  // Circle
        computeShapeArea(3, 8, 2, System.out::println);  // Triangle
        computeShapeArea(4, 10, 5, System.out::println); // Rectangle

        // Area formula for a rectangle
        System.out.println("Rectangle area is : " + computeShapeArea(10, 5, (w, l) -> w * l));

        // Round off calculation of area if either w or l is > 100
        System.out.println("Rectangle : " + computeShapeArea(5.5, 4, (w, l) -> w * l, (w, l) -> w > 100 || l > 100));

        // Pass 5.0 and 5.1 via the supplier interface and calculate the area of a triangle
        System.out.println("Triangle : " + computeShapeArea(() -> 5.0, () -> 5.1, (h, b) -> h * b / 2));
    }
}
