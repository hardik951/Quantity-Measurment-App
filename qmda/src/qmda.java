public class qmda {

    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    public static void main(String[] args) {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println(f1.equals(f2));

        Feet f3 = new Feet(1.0);
        Feet f4 = new Feet(2.0);
        System.out.println(f3.equals(f4));

        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);
        System.out.println(i1.equals(i2));

        Inches i3 = new Inches(1.0);
        Inches i4 = new Inches(2.0);
        System.out.println(i3.equals(i4));

        Inches i5 = new Inches(1.0);
        System.out.println(i5.equals(null));

        Feet f5 = new Feet(1.0);
        System.out.println(f5.equals(f5));
    }
}