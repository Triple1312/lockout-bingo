package net.abrikoos.lockout_bingo.server.listeners;

@FunctionalInterface
public interface HexConsumer<T, U, V, W, X, Y> {
    void accept(T t, U u, V v, W w, X x, Y y);
}
