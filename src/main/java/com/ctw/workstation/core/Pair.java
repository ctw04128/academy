package com.ctw.workstation.core;

import java.io.Serializable;

/**
 * An object representing a pair of elements. A pair contains a left and a right element.
 * The type of the elements is defined on declaration.
 * The elements are unchangeable and can only be obtained.
 *
 * @param <L> the left element's type
 * @param <R> the right element's type
 * @author Filipe Pregal
 * @version 1.0
 */
public record Pair<L, R>(L left, R right) implements Serializable {

    /**
     * Obtain the element of the left of the pair
     *
     * @return the left element
     * @since 1.0
     */
    @Override
    public L left() {
        return left;
    }

    /**
     * Obtain the element of the right of the pair
     *
     * @return the right element
     * @since 1.0
     */
    @Override
    public R right() {
        return right;
    }

    @Override
    public String toString() {
        return "(" + left + ", " + right + ")";
    }

    /**
     * Verifies if this pair has the same elements as the provided pair.
     * Verification is done through memory location.
     *
     * Comparison is done through the == symbol.
     *
     * @param pair another pair to compare
     * @return TRUE if the elements are the same, FALSE otherwise
     */
    public boolean equals(Pair<L, R> pair) {
        return left == pair.left() && right == pair.right();
    }

    /**
     * Verifies if both elements of this pair are equal to both elements of the second pair.
     * The elements are compared and might not be the same elements but considered equals.
     *
     * Method equals() is used on comparison.
     *
     * @param pair another pair to compare
     * @return TRUE if the elements are equal, FALSE otherwise
     */
    public boolean equalContents(Pair<L, R> pair) {
        return left.equals(pair.left()) && right.equals(pair.right());
    }
}
