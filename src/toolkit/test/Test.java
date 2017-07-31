package toolkit.test;

import library.string.CharacterString;

public class Test {

        public Test() {
                boolean b = CharacterString.regularExpressionCheck("^[男女]$", "哈");
                System.out.println(b);
        }

        public static void main(String[] args) {
                new Test();
        }
}