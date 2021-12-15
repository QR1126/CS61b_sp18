package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
    }

    @Override
    public double next() {
        state = state + 1;
        if (state == period) {
            period *= factor;
            state = 0;
        }
        return normalize(state);
    }

    private double normalize(int x) {
        return (double) x * 2 / period - 1;
    }
}
