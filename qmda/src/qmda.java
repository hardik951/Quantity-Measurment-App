public class qmda {

    public interface IMeasurable {
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double value);
    }

    public enum LengthUnit implements IMeasurable {
        FEET(1.0), INCHES(12.0);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double convertToBaseUnit(double value) {
            if (this == FEET) return value;
            return value / factor;
        }

        public double convertFromBaseUnit(double value) {
            if (this == FEET) return value;
            return value * factor;
        }
    }

    public enum WeightUnit implements IMeasurable {
        KILOGRAM(1.0), GRAM(1000.0);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double convertToBaseUnit(double value) {
            if (this == KILOGRAM) return value;
            return value / factor;
        }

        public double convertFromBaseUnit(double value) {
            if (this == KILOGRAM) return value;
            return value * factor;
        }
    }

    public enum VolumeUnit implements IMeasurable {
        LITRE(1.0), MILLILITRE(1000.0);

        private final double factor;

        VolumeUnit(double factor) {
            this.factor = factor;
        }

        public double convertToBaseUnit(double value) {
            if (this == LITRE) return value;
            return value / factor;
        }

        public double convertFromBaseUnit(double value) {
            if (this == LITRE) return value;
            return value * factor;
        }
    }

    public enum ArithmeticOperation {
        ADD {
            public double compute(double a, double b) {
                return a + b;
            }
        },
        SUBTRACT {
            public double compute(double a, double b) {
                return a - b;
            }
        },
        DIVIDE {
            public double compute(double a, double b) {
                if (b == 0) throw new ArithmeticException();
                return a / b;
            }
        };

        public abstract double compute(double a, double b);
    }

    public static class Quantity<U extends IMeasurable> {

        private final double value;
        private final U unit;

        public Quantity(double value, U unit) {
            this.value = value;
            this.unit = unit;
        }

        private void validate(Quantity<U> other) {
            if (other == null) throw new IllegalArgumentException();
            if (Double.isNaN(value) || Double.isInfinite(value)) throw new IllegalArgumentException();
            if (Double.isNaN(other.value) || Double.isInfinite(other.value)) throw new IllegalArgumentException();
        }

        private double base(U unit, double value) {
            return unit.convertToBaseUnit(value);
        }

        private double perform(Quantity<U> other, ArithmeticOperation op) {
            validate(other);
            double a = base(this.unit, this.value);
            double b = base(other.unit, other.value);
            return op.compute(a, b);
        }

        public Quantity<U> add(Quantity<U> other) {
            double r = perform(other, ArithmeticOperation.ADD);
            return new Quantity<>(this.unit.convertFromBaseUnit(r), this.unit);
        }

        public Quantity<U> subtract(Quantity<U> other) {
            double r = perform(other, ArithmeticOperation.SUBTRACT);
            return new Quantity<>(this.unit.convertFromBaseUnit(r), this.unit);
        }

        public double divide(Quantity<U> other) {
            double r = perform(other, ArithmeticOperation.DIVIDE);
            double b = base(other.unit, other.value);
            return r;
        }

        public double getValue() {
            return value;
        }

        public U getUnit() {
            return unit;
        }

        public String toString() {
            return "Quantity(" + value + "," + unit + ")";
        }
    }

    public static void main(String[] args) {

        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println(q1.add(q2));
        System.out.println(q1.subtract(q2));
        System.out.println(q1.divide(new Quantity<>(2.0, LengthUnit.FEET)));

        Quantity<WeightUnit> w1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5000.0, WeightUnit.GRAM);

        System.out.println(w1.add(w2));
        System.out.println(w1.subtract(w2));
        System.out.println(w1.divide(new Quantity<>(5.0, WeightUnit.KILOGRAM)));

        Quantity<VolumeUnit> v1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println(v1.add(v2));
        System.out.println(v1.subtract(v2));
        System.out.println(v1.divide(new Quantity<>(10.0, VolumeUnit.LITRE)));
    }
}