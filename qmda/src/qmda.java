enum LengthUnit {
    FEET(12.0),
    INCH(1.0),
    YARD(36.0),
    CENTIMETER(0.393701);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }

    public double convertToBaseUnit(double value) {
        return toBase(value);
    }

    public double convertFromBaseUnit(double baseValue) {
        return fromBase(baseValue);
    }
}

class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value) || unit == null) {
            throw new IllegalArgumentException();
        }
        this.value = value;
        this.unit = unit;
    }

    public QuantityLength convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException();
        }
        double base = unit.convertToBaseUnit(value);
        return new QuantityLength(targetUnit.convertFromBaseUnit(base), targetUnit);
    }

    private double toBase() {
        return unit.convertToBaseUnit(value);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        QuantityLength other = (QuantityLength) obj;
        return Double.compare(this.toBase(), other.toBase()) == 0;
    }

    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException();
        }
        double sum = this.toBase() + other.toBase();
        return new QuantityLength(targetUnit.convertFromBaseUnit(sum), targetUnit);
    }

    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}

public class qmda {

    public static QuantityLength add(double v1, LengthUnit u1, double v2, LengthUnit u2, LengthUnit target) {
        QuantityLength q1 = new QuantityLength(v1, u1);
        QuantityLength q2 = new QuantityLength(v2, u2);
        return q1.add(q2, target);
    }

    public static void main(String[] args) {
        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println(q1.convertTo(LengthUnit.INCH));
        System.out.println(add(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCH, LengthUnit.FEET));
        System.out.println(q2.equals(new QuantityLength(1.0, LengthUnit.YARD)));
        System.out.println(add(1.0, LengthUnit.YARD, 3.0, LengthUnit.FEET, LengthUnit.YARD));
    }
}