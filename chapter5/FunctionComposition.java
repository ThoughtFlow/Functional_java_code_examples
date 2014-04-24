package chapter5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FunctionComposition {

    private void psGrep() {
        Function<List<String>, List<String>> ps = s -> new ArrayList<>(Arrays.asList("1001 java mySpringApplication", "1002 myOtherApp", "1003 java myApplicationServer"));
        Function<List<String>, List<String>> grep = list ->
        {
            list.removeIf(s -> !s.contains("java"));
            return list;
        };

        Function<List<String>, List<String>> listJavaProcs = ps.andThen(grep);

        for (String nextProcess : listJavaProcs.apply(null)) {
            System.out.println(nextProcess);
        }
    }

    private void viWhich() {
        Function<String, String> which = s -> "/bin/" + s;
        Function<String, String> vi = s -> "File edited was : " + s;

        Function<String, String> viComposeWhich = vi.compose(which);
        Function<String, String> whichComposeVi = which.compose(vi);
        Function<String, String> viAndThenWhich = vi.andThen(which);
        Function<String, String> whichAndThenVi = which.andThen(vi);
        System.out.println(viComposeWhich.apply("myScript"));
        System.out.println(whichComposeVi.apply("myScript"));
        System.out.println(viAndThenWhich.apply("myScript"));
        System.out.println(whichAndThenVi.apply("myScript"));
    }

    public static void main(String...args)
    {
        FunctionComposition functionComposition = new FunctionComposition();
        functionComposition.psGrep();
        functionComposition.viWhich();
    }
}
