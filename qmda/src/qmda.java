public class qmda {

    // ===================== INTERFACE =====================

    interface IMeasurable {
        double toBaseUnit(double value);
        double fromBaseUnit(double baseValue);
        String getUnitName();
    }

    // ===================== LENGTH UNIT =====================

    enum LengthUnit implements IMeasurable {

        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.0328084);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toBaseUnit(double value) {
            return value * factor;
        }

        public double fromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return name();
        }
    }

    // ===================== WEIGHT UNIT =====================

    enum WeightUnit implements IMeasurable {

        KILOGRAM(1.0),
        GRAM(0.001),
        POUND(0.453592);

        private final double factor;

        WeightUnit(double factor) {
            this.factor = factor;
        }

        public double toBaseUnit(double value) {
            return value * factor;
        }

        public double fromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return name();
        }
    }

    // ===================== GENERIC QUANTITY CLASS =====================

    static class Quantity<U extends IMeasurable> {

        private final double value;
        private final U unit;

        public Quantity(double value, U unit) {
            if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.toBaseUnit(value);
        }

        public Quantity<U> convertTo(U targetUnit) {
            double base = this.toBase();
            return new Quantity<>(targetUnit.fromBaseUnit(base), targetUnit);
        }

        public Quantity<U> add(Quantity<U> other) {
            return add(other, this.unit);
        }

        public Quantity<U> add(Quantity<U> other, U targetUnit) {
            double sumBase = this.toBase() + other.toBase();
            return new Quantity<>(targetUnit.fromBaseUnit(sumBase), targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;

            Quantity<?> other = (Quantity<?>) obj;

            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit.getUnitName() + ")";
        }

        @Override
        public int hashCode() {
            return Double.valueOf(toBase()).hashCode();
        }
    }

    // ===================== APP (SIMPLE DEMO) =====================

    public static void main(String[] args) {

        // ===== LENGTH =====
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);

        System.out.println(l1.equals(l2)); // true
        System.out.println(l1.convertTo(LengthUnit.INCHES));
        System.out.println(l1.add(l2, LengthUnit.FEET));

        // ===== WEIGHT =====
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println(w1.equals(w2)); // true
        System.out.println(w1.convertTo(WeightUnit.GRAM));
        System.out.println(w1.add(w2, WeightUnit.KILOGRAM));

        // ===== CROSS CATEGORY SAFETY (WILL NOT COMPILE if uncommented) =====
        // System.out.println(l1.equals(w1)); // compile-safe prevention via generics
    }
}