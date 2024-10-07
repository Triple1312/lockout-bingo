package net.abrikoos.lockout_bingo.util;

public interface HeptConsumer<K, V, S, T, U, E, W> {

    /**
     * Performs the operation given the specified arguments.
     * @param k the first input argument
     * @param v the second input argument
     * @param s the third input argument
     * @param t the fourth input argument // todo add others
     */
    void accept(K k, V v, S s, T t, U u, E e, W w);
}
