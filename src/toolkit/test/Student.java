package toolkit.test;

import java.lang.reflect.Method;

public class Student {

        private String name;

        public Student() {
                this.kkk();
        }

        // @Annotation_my(CorrectReturn = "setName de CorrectReturn")
        // @Annotation_my("TABLE_USER")
        @Annotation_my("setName de CorrectReturn")
        public void setName(String name) {
                this.name = name;
        }

        @Annotation_my("getName de CorrectReturn")
        public String getName() {
                return this.name;
        }

        public void kkk() {
                try {
                        Class<?> stu = Class.forName(this.getClass().getName());// 静态加载类
                        Method m = stu.getMethod("getName");//
                        Annotation_my a = m.getAnnotation(Annotation_my.class);
                        System.out.println(a.value());
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public static void main(String args[]) {
                new Student();
        }
        // @Override
        // @Annotation_my(name = "流氓公子") // 赋值给name 默认的为张三
        // // 在定义注解时没有给定默认值时，在此处必须name赋初值
        // public void name(int a) {

        // }

        // @Override
        // @Annotation_my(name = " hello world ！")
        // public void say() {

        // }

        // @Override
        // @Annotation_my(age = 20)
        // public void age() {

        // }
}
