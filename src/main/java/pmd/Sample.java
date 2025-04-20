public class Sample {
    private int someValue;
    
    public Sample() {
        // 空的构造函数
    }

    public void unusedMethod() {
        // 这个方法没有被调用
        System.out.println("This method is never used.");
    }

    public void doSomething() {
        int value = 5; // 未被使用的局部变量
        someValue = 10;
        someValue = 20; // 覆盖了之前的赋值
    }

    public void longMethod() {
        // 过长的方法，应该被分解
        System.out.println("This is a very long method, and it should be split into smaller methods.");
        System.out.println("It does too many things at once.");
        System.out.println("It's hard to read and maintain.");
        System.out.println("It's also not good for testing.");
    }

    public double divide(int a, int b) {
        // 没有检查除数是否为零
        return a / b;
    }

    public String toString() {
        // 忽略了父类的 toString 方法
        return "Sample{" +
                "someValue=" + someValue +
                '}';
    }
}