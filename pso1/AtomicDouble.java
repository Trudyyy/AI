import java.util.concurrent.atomic.AtomicReference;

public class AtomicDouble extends Number implements Comparable<AtomicDouble> {
    private static final long serialVersionUID = 1L;
    private AtomicReference<Double> atomicReference;

    public AtomicDouble(Double value) {
        atomicReference = new AtomicReference<>(value);
    }

    public double doubleValue() {
        return atomicReference.get();
    }

    public float floatValue() {
        return atomicReference.get().floatValue();
    }

    public int intValue() {
        return atomicReference.get().intValue();
    }

    public long longValue() {
        return atomicReference.get().longValue();
    }

    public int compareTo(AtomicDouble atomicDouble) {
        return Double.compare(this.doubleValue(), atomicDouble.doubleValue());
    }

    public boolean compareAndSet(double updateValue) {
        boolean flag = false;
        if (atomicReference.compareAndSet(atomicReference.get(), updateValue)) return flag = true;
        return flag;
    }
}
