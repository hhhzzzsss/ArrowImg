package com.github.hhhzzzsss.arrowimg.generators;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class CircleGenerator extends PointGenerator {
    public final double radius;
    public final int count;

    private int index = 0;

    public CircleGenerator(Vec3d target, Direction axis1, Direction axis2, double radius, int count) {
        super(target, axis1, axis2);
        this.radius = radius;
        this.count = count;
    }

    public boolean hasNext() {
        return index < count;
    }

    public Vec3d next() {
        double angle = index * 2*Math.PI / count;
        Vec3d result = uvToVec3d( radius*Math.cos(angle) , radius*Math.sin(angle) );
        index++;
        return result;
    }
}
