package chapter3;

import java.util.List;

@FunctionalInterface
public interface ListCreator<T extends List> {
    T create();
}