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

    public static void main(String[] args)
    {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);
        System.out.println(f1.equals(f2));

        Feet f3 = new Feet(1.0);
        Feet f4 = new Feet(2.0);
        System.out.println(f3.equals(f4));

        Feet f5 = new Feet(1.0);
        System.out.println(f5.equals(null));

        Feet f6 = new Feet(1.0);
        System.out.println(f6.equals("test"));

        Feet f7 = new Feet(1.0);
        System.out.println(f7.equals(f7));
    }
}