package com.swz;

import java.util.Objects;

public class RunoobTest implements Cloneable {

    // 声明变量
    Person person;
    int likes;

    public Person getPerson (){
        return person;
    }

    public void setPerson (Person person){
        this.person = person;
    }

    public int getLikes (){
        return likes;
    }

    public void setLikes (int likes){
        this.likes = likes;
    }

    public RunoobTest (int likes){
       this.person = person;
        this.likes = likes;
    }

    public RunoobTest (Person person, int likes){
        this.person = person;
        this.likes = likes;
    }

    public RunoobTest (){
    }

    @Override
    public boolean equals (Object o){
        if (this == o) return true;
        if (!(o instanceof RunoobTest)) return false;

        RunoobTest that = (RunoobTest) o;

        if (likes != that.likes) return false;
        return person != null ? person.equals(that.person) : that.person == null;
    }

    @Override
    public int hashCode (){
        int result = person != null ? person.hashCode() : 0;
        result = 31 * result + likes;
        return result;
    }

    //@Override
    //protected RunoobTest clone () throws CloneNotSupportedException{
    //    return (RunoobTest) super.clone();
    //}
    public static void main (String[] args){
        RunoobTest runoobTest = new RunoobTest(new Person("zs",21,false),18);
        try {
            RunoobTest clone = (RunoobTest) runoobTest.clone();
            System.out.println(runoobTest.getPerson());
            System.out.println(clone.getPerson());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }



}