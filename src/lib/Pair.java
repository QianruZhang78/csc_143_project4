package lib;

import java.util.HashMap;

/**
 * A simple container for two variables.
 * @param <L> Left value.
 * @param <R> Right value.
 */
public class Pair<L, R> {
    /* DO NOT MODIFY
        If you need to modify left or right,
        construct a new Pair object.
     */
    public final L left;
    public final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }

        Pair other = (Pair) o;
        boolean leftEquals = false;
        if (left == null) {
            leftEquals = other.left == null;
        } else {
            leftEquals = left.equals(other.left);
        }

        if (!leftEquals) {
            return false;
        }

        boolean rightEquals = false;
        if (right == null) {
            rightEquals = other.right == null;
        } else {
            rightEquals = right.equals(other.right);
        }

        return rightEquals;
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }
}
