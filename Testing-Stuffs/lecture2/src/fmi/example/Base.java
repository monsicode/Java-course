package fmi.example;

public class Base {

    protected int someData = 1;

    Base(int a)
    {
        System.out.println(multNumber(a));
    }

    public int  multNumber(int a)
    {
        return a*2;
    }

    @Override // метода трабва да е публичен и да приема параметър object 
   public boolean equals(Object object)
    {
        return  someData == 3;
    }

}


