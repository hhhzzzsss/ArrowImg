package com.github.hhhzzzsss.arrowimg.generators;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class JuliaSetGenerator extends PointGenerator {
    private final double c_real;
    private final double c_imag;
    private final double scale;
    private final int count;
    private final int power;

    private final Random rand;

    private double z_real = Math.random()*2.0 - 1.0;
    private double z_imag = Math.random()*2.0 - 1.0;
    private int index = 0;

    public JuliaSetGenerator(Vec3d target, Direction axis1, Direction axis2, int power, double c_real, double c_imag, double scale, int count) {
        super(target, axis1, axis2);
        this.power = power;
        this.c_real = c_real;
        this.c_imag = c_imag;
        this.scale = scale;
        this.count = count;

        this.rand = new Random();

        for (int i = 0; i < 256; i++) {
            doIteration();
        }
    }

    public boolean hasNext() {
        return index < count;
    }

    public Vec3d next() {
        doIteration();
        index++;
        return uvToVec3d( scale*z_real , scale*z_imag );
    }

    private void doIteration() {
        z_real -= c_real;
        z_imag -= c_imag;
        double z_r = Math.sqrt(z_real*z_real + z_imag*z_imag);
        double z_t = Math.atan2(z_imag, z_real);
        z_r = Math.pow(z_r, 1.0/power);
        z_t /= power;
        z_t += rand.nextInt(power) * 2 * Math.PI / power;
        z_real = z_r * Math.cos(z_t);
        z_imag = z_r * Math.sin(z_t);
    }
}
