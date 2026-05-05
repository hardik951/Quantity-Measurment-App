public class qmda {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double toFeet(double value) {
            return value * conversionFactor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, String unit) {
            this.value = value;
            this.unit = LengthUnit.valueOf(unit.toUpperCase());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisValue = this.unit.toFeet(this.value);
            double otherValue = other.unit.toFeet(other.value);

            return Double.compare(thisValue, otherValue) == 0;
        }
    }

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, "YARD");
        QuantityLength q2 = new QuantityLength(3.0, "FEET");
        System.out.println(q1.equals(q2));

        QuantityLength q3 = new QuantityLength(1.0, "YARD");
        QuantityLength q4 = new QuantityLength(36.0, "INCH");
        System.out.println(q3.equals(q4));

        QuantityLength q5 = new QuantityLength(2.0, "YARD");
        QuantityLength q6 = new QuantityLength(2.0, "YARD");
        System.out.println(q5.equals(q6));

        QuantityLength q7 = new QuantityLength(2.0, "CENTIMETER");
        QuantityLength q8 = new QuantityLength(2.0, "CENTIMETER");
        System.out.println(q7.equals(q8));

        QuantityLength q9 = new QuantityLength(1.0, "CENTIMETER");
        QuantityLength q10 = new QuantityLength(0.393701, "INCH");
        System.out.println(q9.equals(q10));

        QuantityLength q11 = new QuantityLength(2.0, "YARD");
        QuantityLength q12 = new QuantityLength(6.0, "FEET");
        QuantityLength q13 = new QuantityLength(72.0, "INCH");
        System.out.println(q11.equals(q12) && q12.equals(q13));

        QuantityLength q14 = new QuantityLength(1.0, "YARD");
        QuantityLength q15 = new QuantityLength(2.0, "FEET");
        System.out.println(q14.equals(q15));

        QuantityLength q16 = new QuantityLength(1.0, "YARD");
        System.out.println(q16.equals(q16));

        QuantityLength q17 = new QuantityLength(1.0, "YARD");
        System.out.println(q17.equals(null));
    }
}