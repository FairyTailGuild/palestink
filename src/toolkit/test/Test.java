package toolkit.test;

public class Test {

        public Test() {
                for (int i = 'a'; i <= 'z'; i++) {
                        for (int j = 'a'; j <= 'z'; j++) {
                                for (int n = 'a'; n <= 'z'; n++) {
                                        char a = (char) i;
                                        char b = (char) j;
                                        char c = (char) n;
                                        String name = String.valueOf(a) + String.valueOf(b) + String.valueOf(c) + ".com";
                                        System.out.println(name);
                                }
                        }
                }
        }

        public static void main(String[] args) {
                new Test();
        }
}