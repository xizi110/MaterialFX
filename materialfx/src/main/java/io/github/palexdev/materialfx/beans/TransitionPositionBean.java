package io.github.palexdev.materialfx.beans;

import javafx.animation.Transition;

// TODO documentation
/**
 * This is an extension of {@link PositionBean} to be used
 * with {@link Transition}s that start from a point P(x, y) and
 * end at a point P1(endX, endY).
 * <p></p>
 * A very basic example:
 * <p>
 * Let's say I want to move a point P from (x, y) to the left
 * (x1, y) with an animation. The transition would probably look like this:
 * <pre>
 * {@code
 *     double startX = ...;
 *     double startY = ...;
 *     double endX = ...;
 *     double endY = startY; // The y coordinate doesn't change so it is equal to the start one
 *     TransitionPositionBean position = TransitionPositionBean.of(startX, startY, endX, endY);
 *     Transition move = new Transition() {
 *             @Override
 *             protected void interpolate(double frac) {
 *                 p.setX(x - position.deltaX() * frac);
 *             }
 *      }
 * }
 * </pre>
 */
public class TransitionPositionBean extends PositionBean {
    //================================================================================
    // Properties
    //================================================================================
    private final double endX;
    private final double endY;

    //================================================================================
    // Constructors
    //================================================================================
    public TransitionPositionBean(double x, double y, double endX, double endY) {
        super(x, y);
        this.endX = endX;
        this.endY = endY;
    }

    //================================================================================
    // Static Methods
    //================================================================================
    public static TransitionPositionBean of(double x, double y, double endX, double endY) {
        return new TransitionPositionBean(x, y, endX, endY);
    }

    //================================================================================
    // Getters/Setters
    //================================================================================

    /**
     * @return the end x coordinate
     */
    public double getEndX() {
        return endX;
    }

    /**
     * @return the end y coordinate
     */
    public double getEndY() {
        return endY;
    }

    /**
     * @return the difference between the star x and end x coordinates
     */
    public double deltaX() {
        return getX() - getEndX();
    }

    /**
     * @return the difference between the start y and end y coordinates
     */
    public double deltaY() {
        return getY() - getEndY();
    }
}
