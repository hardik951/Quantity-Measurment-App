public class qmda {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double factor;

        LengthUnit(double factor) {
            this.factor = factor;
        }

        public double toFeet(double value) {
            return value * factor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / factor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        public double convertTo(LengthUnit targetUnit) {
            double base = unit.toFeet(value);
            return targetUnit.fromFeet(base);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisBase = this.unit.toFeet(this.value);
            double otherBase = other.unit.toFeet(other.value);

            return Double.compare(thisBase, otherBase) == 0;
        }
    }

    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (!Double.isFinite(value) || source == null || target == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base = source.toFeet(value);
        return target.fromFeet(base);
    }

    public static void main(String[] args) {

        System.out.println(convert(1.0, LengthUnit.FEET, LengthUnit.INCH));
        System.out.println(convert(3.0, LengthUnit.YARD, LengthUnit.FEET));
        System.out.println(convert(36.0, LengthUnit.INCH, LengthUnit.YARD));
        System.out.println(convert(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH));
        System.out.println(convert(0.0, LengthUnit.FEET, LengthUnit.INCH));

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
        System.out.println(q1.equals(q2));

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength q4 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println(q3.equals(q4));

        QuantityLength q5 = new QuantityLength(2.0, LengthUnit.CENTIMETER);
        QuantityLength q6 = new QuantityLength(2.0, LengthUnit.CENTIMETER);
        System.out.println(q5.equals(q6));

        System.out.println(convert(72.0, LengthUnit.INCH, LengthUnit.YARD));
    }
}