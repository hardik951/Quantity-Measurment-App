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
            if (!Double.isFinite(value) || unit == null) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Null operand");
            }

            double thisBase = this.unit.toFeet(this.value);
            double otherBase = other.unit.toFeet(other.value);

            double sumBase = thisBase + otherBase;
            double resultValue = this.unit.fromFeet(sumBase);

            return new QuantityLength(resultValue, this.unit);
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

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);
        System.out.println(q1.add(q2));

        QuantityLength q3 = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength q4 = new QuantityLength(12.0, LengthUnit.INCH);
        System.out.println(q3.add(q4));

        QuantityLength q5 = new QuantityLength(12.0, LengthUnit.INCH);
        QuantityLength q6 = new QuantityLength(1.0, LengthUnit.FEET);
        System.out.println(q5.add(q6));

        QuantityLength q7 = new QuantityLength(1.0, LengthUnit.YARD);
        QuantityLength q8 = new QuantityLength(3.0, LengthUnit.FEET);
        System.out.println(q7.add(q8));

        QuantityLength q9 = new QuantityLength(36.0, LengthUnit.INCH);
        QuantityLength q10 = new QuantityLength(1.0, LengthUnit.YARD);
        System.out.println(q9.add(q10));

        QuantityLength q11 = new QuantityLength(2.54, LengthUnit.CENTIMETER);
        QuantityLength q12 = new QuantityLength(1.0, LengthUnit.INCH);
        System.out.println(q11.add(q12));

        QuantityLength q13 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q14 = new QuantityLength(0.0, LengthUnit.INCH);
        System.out.println(q13.add(q14));

        QuantityLength q15 = new QuantityLength(5.0, LengthUnit.FEET);
        QuantityLength q16 = new QuantityLength(-2.0, LengthUnit.FEET);
        System.out.println(q15.add(q16));
    }
}