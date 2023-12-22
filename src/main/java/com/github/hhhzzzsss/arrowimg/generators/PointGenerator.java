package com.github.hhhzzzsss.arrowimg.generators;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public abstract class PointGenerator {
    protected final Vec3d target;
    protected final Direction axis1;
    protected final Direction axis2;

    protected PointGenerator(Vec3d target, Direction axis1, Direction axis2) {
        this.target = target;
        this.axis1 = axis1;
        this.axis2 = axis2;
    }

    public Vec3d uvToVec3d(double u, double v) {
        return target.offset(axis1, u).offset(axis2, v);
    }

    public abstract boolean hasNext();
    public abstract Vec3d next();
}
