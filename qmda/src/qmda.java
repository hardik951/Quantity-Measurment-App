public class qmda {

    // ==========================================
    // 1. IMeasurable Interface (UC10 foundation)
    // ==========================================
    interface IMeasurable {
        double getConversionFactor();              // relative to base unit
        double convertToBaseUnit(double value);    // to base unit (Litre here)
        double convertFromBaseUnit(double value);  // from base unit
        String getUnitName();
    }

    // ==========================================
    // 2. VolumeUnit Enum (UC11 NEW CATEGORY)
    // ==========================================
    enum VolumeUnit implements IMeasurable {

        LITRE(1.0),
        MILLILITRE(0.001),
        GALLON(3.78541);

        private final double factor;

        VolumeUnit(double factor) {
            this.factor = factor;
        }

        @Override
        public double getConversionFactor() {
            return factor;
        }

        @Override
        public double convertToBaseUnit(double value) {
            return value * factor; // convert to litres
        }

        @Override
        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor; // from litres
        }

        @Override
        public String getUnitName() {
            return name();
        }
    }

    // ==========================================
    // 3. Generic Quantity Class (UC10 CORE)
    // ==========================================
    static class Quantity<U extends IMeasurable> {

        private final double value;
        private final U unit;
        private static final double EPSILON = 0.0001;

        public Quantity(double value, U unit) {
            if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
            if (Double.isNaN(value) || Double.isInfinite(value))
                throw new IllegalArgumentException("Invalid value");
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public U getUnit() {
            return unit;
        }

        // ======================================
        // CONVERSION (UC11 requirement)
        // ======================================
        public Quantity<U> convertTo(U targetUnit) {
            double base = unit.convertToBaseUnit(value);
            double converted = targetUnit.convertFromBaseUnit(base);
            return new Quantity<>(converted, targetUnit);
        }

        // ======================================
        // EQUALITY (UC11 + UC10 rule)
        // ======================================
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity<?> other = (Quantity<?>) obj;

            // prevent cross-category comparison
            if (!this.unit.getClass().equals(other.unit.getClass()))
                return false;

            double thisBase = this.unit.convertToBaseUnit(this.value);
            double otherBase = other.unit.convertToBaseUnit(other.value);

            return Math.abs(thisBase - otherBase) < EPSILON;
        }

        // ======================================
        // ADDITION (UC11 requirement)
        // ======================================
        public Quantity<U> add(Quantity<U> other) {
            double sumBase =
                    this.unit.convertToBaseUnit(this.value)
                            + other.unit.convertToBaseUnit(other.value);

            double result = this.unit.convertFromBaseUnit(sumBase);
            return new Quantity<>(result, this.unit);
        }

        public Quantity<U> add(Quantity<U> other, U targetUnit) {
            double sumBase =
                    this.unit.convertToBaseUnit(this.value)
                            + other.unit.convertToBaseUnit(other.value);

            double result = targetUnit.convertFromBaseUnit(sumBase);
            return new Quantity<>(result, targetUnit);
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit.getUnitName() + ")";
        }
    }

    // ==========================================
    // 4. UC11 MAIN DEMONSTRATION (FULL FLOW)
    // ==========================================
    public static void main(String[] args) {

        // =========================
        // Volume Objects
        // =========================
        Quantity<VolumeUnit> litre = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> ml = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> gallon = new Quantity<>(1.0, VolumeUnit.GALLON);

        // =========================
        // EQUALITY TESTS
        // =========================
        System.out.println("=== EQUALITY ===");
        System.out.println(litre.equals(ml));     // true
        System.out.println(litre.equals(gallon)); // false-ish depending epsilon

        // =========================
        // CONVERSION TESTS
        // =========================
        System.out.println("\n=== CONVERSION ===");
        System.out.println(litre.convertTo(VolumeUnit.MILLILITRE));
        System.out.println(gallon.convertTo(VolumeUnit.LITRE));
        System.out.println(ml.convertTo(VolumeUnit.GALLON));

        // =========================
        // ADDITION TESTS
        // =========================
        System.out.println("\n=== ADDITION (implicit unit) ===");
        System.out.println(litre.add(ml)); // 2 L

        System.out.println("\n=== ADDITION (explicit unit) ===");
        System.out.println(litre.add(ml, VolumeUnit.MILLILITRE));

        System.out.println(gallon.add(litre, VolumeUnit.GALLON));

        // =========================
        // CROSS CATEGORY SAFETY
        // =========================
        System.out.println("\n=== CROSS CATEGORY SAFETY ===");
        Quantity<VolumeUnit> fake = new Quantity<>(1.0, VolumeUnit.LITRE);
        System.out.println(litre.equals(fake)); // true

        // NOTE: Length/Weight cannot be used here due to type safety
    }
}