package com.swz;

/**
 * @author : 苏文致
 * @date Date : 2021年07月15日 15:54
 * @Description: TODO:
 */
public class Person implements Cloneable {
    private String name ;
    private int age;
    private boolean mamery ;

    public Person (){
    }

    public Person (String name, int age, boolean mamery){
        this.name = name;
        this.age = age;
        this.mamery = mamery;
    }

    @Override
    protected Person clone () throws CloneNotSupportedException{
        return (Person)super.clone();
    }
}
