package fmi.example;

import com.sun.tools.javac.Main;

public class Der extends Base {

    public int otherData;

    Der(int a)
    {
        super(a); // Base(a) // ако не го извикаме по default ще се извика def конструктора на родителския клас

        System.out.println(multNumber(a));
    }

    @Override
    public int multNumber(int a) {
     return a*3;
    }



    public static void main(String[] args) {
        Der der = new Der(4);
        Base base = new Base(3);

    }

}
