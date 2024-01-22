package cn.com.cgh.core.util;

import jakarta.validation.constraints.NotNull;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public abstract class CoreDelay implements Delayed ,Runnable{

    private String orderId;
    private long timeout;

    public CoreDelay(String orderId, long time,TimeUnit timeUnit) {
        this.orderId = orderId;
        if (timeUnit != TimeUnit.NANOSECONDS){
            time = TimeUnit.NANOSECONDS.convert(time, timeUnit);
        }
        this.timeout = time + System.nanoTime();
    }

    @Override
    public long getDelay(@NotNull TimeUnit unit) {
        return unit.convert(timeout - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(@NotNull Delayed other) {
        if (other == this)
            return 0;
        CoreDelay t = (CoreDelay) other;
        long d = (getDelay(TimeUnit.NANOSECONDS) - t
                .getDelay(TimeUnit.NANOSECONDS));
        return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
    }
}
