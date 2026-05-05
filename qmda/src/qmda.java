public class qmda {

    // ================= LENGTH =================

    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        double toBase(double value) {
            return value * toFeet;
        }

        double fromBase(double baseValue) {
            return baseValue / toFeet;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        QuantityLength(double value, LengthUnit unit) {
            if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        double toBase() {
            return unit.toBase(value);
        }

        QuantityLength convertTo(LengthUnit target) {
            return new QuantityLength(target.fromBase(toBase()), target);
        }

        QuantityLength add(QuantityLength other) {
            return add(other, this.unit);
        }

        QuantityLength add(QuantityLength other, LengthUnit target) {
            double sum = this.toBase() + other.toBase();
            return new QuantityLength(target.fromBase(sum), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof QuantityLength)) return false;
            QuantityLength o = (QuantityLength) obj;
            return Double.compare(this.toBase(), o.toBase()) == 0;
        }

        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ================= WEIGHT =================

    enum WeightUnit {
        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double toKg;

        WeightUnit(double toKg) {
            this.toKg = toKg;
        }

        double toBase(double value) {
            return value * toKg;
        }

        double fromBase(double baseValue) {
            return baseValue / toKg;
        }
    }

    static class QuantityWeight {
        private final double value;
        private final WeightUnit unit;

        QuantityWeight(double value, WeightUnit unit) {
            if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        double toBase() {
            return unit.toBase(value);
        }

        QuantityWeight convertTo(WeightUnit target) {
            return new QuantityWeight(target.fromBase(toBase()), target);
        }

        QuantityWeight add(QuantityWeight other) {
            return add(other, this.unit);
        }

        QuantityWeight add(QuantityWeight other, WeightUnit target) {
            double sum = this.toBase() + other.toBase();
            return new QuantityWeight(target.fromBase(sum), target);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof QuantityWeight)) return false;
            QuantityWeight o = (QuantityWeight) obj;
            return Double.compare(this.toBase(), o.toBase()) == 0;
        }

        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        // LENGTH TESTS
        QuantityLength l1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println(l1.add(l2)); // feet
        System.out.println(l1.add(l2, LengthUnit.INCHES));
        System.out.println(l1.add(l2, LengthUnit.YARDS));

        // WEIGHT TESTS
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println(w1.equals(w2)); // true
        System.out.println(w1.add(w2)); // kg
        System.out.println(w1.add(w2, WeightUnit.GRAM));
        System.out.println(w1.add(w2, WeightUnit.POUND));

        System.out.println(w1.convertTo(WeightUnit.GRAM));
        System.out.println(w1.convertTo(WeightUnit.POUND));
    }
}