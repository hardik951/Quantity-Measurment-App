public class qmda {

    interface IMeasurable {
        double getConversionFactor();
        double convertToBaseUnit(double value);
        double convertFromBaseUnit(double baseValue);
        String getUnitName();
    }

    enum VolumeUnit implements IMeasurable {

        LITRE(1.0),
        MILLILITRE(0.001),
        GALLON(3.78541);

        private final double factor;

        VolumeUnit(double factor) {
            this.factor = factor;
        }

        public double getConversionFactor() {
            return factor;
        }

        public double convertToBaseUnit(double value) {
            return value * factor;
        }

        public double convertFromBaseUnit(double baseValue) {
            return baseValue / factor;
        }

        public String getUnitName() {
            return name();
        }
    }

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

        public Quantity<U> convertTo(U targetUnit) {
            double base = unit.convertToBaseUnit(value);
            double converted = targetUnit.convertFromBaseUnit(base);
            return new Quantity<>(converted, targetUnit);
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity<?> other = (Quantity<?>) obj;

            if (!this.unit.getClass().equals(other.unit.getClass()))
                return false;

            double a = this.unit.convertToBaseUnit(this.value);
            double b = other.unit.convertToBaseUnit(other.value);

            return Math.abs(a - b) < EPSILON;
        }

        public Quantity<U> add(Quantity<U> other) {
            double sum =
                    this.unit.convertToBaseUnit(this.value)
                            + other.unit.convertToBaseUnit(other.value);

            double result = this.unit.convertFromBaseUnit(sum);
            return new Quantity<>(result, this.unit);
        }

        public Quantity<U> add(Quantity<U> other, U targetUnit) {
            double sum =
                    this.unit.convertToBaseUnit(this.value)
                            + other.unit.convertToBaseUnit(other.value);

            double result = targetUnit.convertFromBaseUnit(sum);
            return new Quantity<>(result, targetUnit);
        }

        public Quantity<U> subtract(Quantity<U> other) {
            if (other == null) throw new IllegalArgumentException("Null not allowed");

            double diff =
                    this.unit.convertToBaseUnit(this.value)
                            - other.unit.convertToBaseUnit(other.value);

            double result = this.unit.convertFromBaseUnit(diff);
            return new Quantity<>(result, this.unit);
        }

        public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
            if (other == null || targetUnit == null)
                throw new IllegalArgumentException("Null not allowed");

            double diff =
                    this.unit.convertToBaseUnit(this.value)
                            - other.unit.convertToBaseUnit(other.value);

            double result = targetUnit.convertFromBaseUnit(diff);
            return new Quantity<>(result, targetUnit);
        }

        public double divide(Quantity<U> other) {
            if (other == null) throw new IllegalArgumentException("Null not allowed");

            double a = this.unit.convertToBaseUnit(this.value);
            double b = other.unit.convertToBaseUnit(other.value);

            if (Math.abs(b) < EPSILON)
                throw new ArithmeticException("Division by zero");

            return a / b;
        }

        public String toString() {
            return "Quantity(" + value + ", " + unit.getUnitName() + ")";
        }
    }

    public static void main(String[] args) {

        Quantity<VolumeUnit> v1 = new Quantity<>(10.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v3 = new Quantity<>(2.0, VolumeUnit.GALLON);

        System.out.println("EQUALITY:");
        System.out.println(v1.equals(v2));

        System.out.println("\nADDITION:");
        System.out.println(v1.add(v2));

        System.out.println("\nSUBTRACTION:");
        System.out.println(v1.subtract(v2));
        System.out.println(v1.subtract(v2, VolumeUnit.MILLILITRE));

        System.out.println("\nDIVISION:");
        System.out.println(v1.divide(v2));
        System.out.println(v3.divide(v1));

        System.out.println("\nEDGE CASES:");
        System.out.println(v1.subtract(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)));
        System.out.println(v1.add(v2, VolumeUnit.GALLON));
    }
}