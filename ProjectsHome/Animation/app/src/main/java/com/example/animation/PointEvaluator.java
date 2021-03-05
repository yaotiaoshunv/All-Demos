package com.example.animation;

import android.animation.TypeEvaluator;

/**
 * @author Zongwei Li
 */
public class PointEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        float x = startValue.getX() + fraction * (endValue.getX() - startValue.getX());
        float y = startValue.getY() + fraction * (endValue.getY() - startValue.getY());
        Point point = new Point(x,y);
        return point;
    }
}
