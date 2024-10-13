package net.abrikoos.lockout_bingo.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BlockoutList<T> extends ArrayList<T> {

    public BlockoutList(Collection<? extends T> c) {
        super(c);
    }

    public BlockoutList(){super();}

    public boolean contains( @NotNull Function<T, Boolean> func) {
        for (T value : this) {
            if (func.apply(value)) {
                return true;
            }
        }
        return false;
    }


    public @Nullable T firstWhere( @NotNull Function<T, Boolean> func) {
        for (T value : this) {
            if (func.apply(value)) {
                return value;
            }
        }
        return null;
    }

    public BlockoutList<T> where(Function<T,Boolean> func ) {
        BlockoutList<T> returnv = new BlockoutList<T>();
        for (T value : this) {
         if (func.apply(value)) {
                returnv.add(value);
            }
        }
        return returnv;
    }

    public boolean any(Function<T, Boolean> func) {
        for (T value : this) {
            if(func.apply(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean every(Function<T, Boolean> func) {
        for (T value : this) {
            if (!func.apply(value)) {
                return false;
            }
        }
        return true;
    }

    public static <R> BlockoutList<R> filled(R value, int length ) {
        BlockoutList<R> list = new BlockoutList<R>();
        for (int i = 0; i < length; i++ ) {
            list.add(value);
        }
        return list;
    }

    public static <R> BlockoutList<R> generate(int length, Function<Integer, R> func) {
        BlockoutList<R> list = new BlockoutList<>();
        for (int i = 0; i < length; i++) {
            list.add(func.apply(i));
        }
        return list;
    }

    public <S> S fold(S defaultValue, BiFunction<S, T, S> func) {
        S value = defaultValue;
        for (T t : this) {
            value = func.apply(value, t);
        }
        return value;
    }

    public T reduce(BiFunction<T, T, T> func) throws Exception { // todo check if works correctly
        Iterator<T> iterator = this.iterator();
        if (!iterator.hasNext()) {
            throw new Exception("has only no elements");
        }
        T value = iterator.next();
        while(iterator.hasNext()) {
            value = func.apply(value, iterator.next());
        }
        return value;
    }

    // if  true, first should be before second
    public void sort(BiFunction<T, T, Boolean> compare) {
        BlockoutList<T> new_list = new BlockoutList<T>();
        Iterator<T> it = this.iterator();
        T value = it.next();

        while(it.hasNext()) {
            T new_value = it.next();
            if (compare.apply(value, new_value)) {
                new_list.add(value);
                value = new_value;
            }
            else {
                new_list.add(new_value);
            }
        }
        this.clear();
        this.addAll(new_list);
    }

    public BlockoutList<Integer> indecesWhere(Function<T, Boolean> func) {
        BlockoutList<Integer> list = new BlockoutList<>();
        for (int i = 0; i < this.size(); i++) {
            if (func.apply(this.get(i))) {
                list.add(i);
            }
        }
        return list;
    }

    public Integer firstIndexWhere(Function<T, Boolean> func) {
        for (int i = 0; i < this.size(); i++) {
            if (func.apply(this.get(i))) {
                return i;
            }
        }
        return null;
    }

}
