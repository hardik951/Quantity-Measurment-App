public class qmda {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

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

            double thisInFeet = this.unit.toFeet(this.value);
            double otherInFeet = other.unit.toFeet(other.value);

            return Double.compare(thisInFeet, otherInFeet) == 0;
        }
    }

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, "feet");
        QuantityLength q2 = new QuantityLength(1.0, "feet");
        System.out.println(q1.equals(q2));

        QuantityLength q3 = new QuantityLength(1.0, "inch");
        QuantityLength q4 = new QuantityLength(1.0, "inch");
        System.out.println(q3.equals(q4));

        QuantityLength q5 = new QuantityLength(12.0, "inch");
        QuantityLength q6 = new QuantityLength(1.0, "feet");
        System.out.println(q5.equals(q6));

        QuantityLength q7 = new QuantityLength(1.0, "feet");
        QuantityLength q8 = new QuantityLength(2.0, "feet");
        System.out.println(q7.equals(q8));

        QuantityLength q9 = new QuantityLength(1.0, "inch");
        QuantityLength q10 = new QuantityLength(2.0, "inch");
        System.out.println(q9.equals(q10));

        QuantityLength q11 = new QuantityLength(1.0, "feet");
        System.out.println(q11.equals(q11));

        QuantityLength q12 = new QuantityLength(1.0, "feet");
        System.out.println(q12.equals(null));
    }
}