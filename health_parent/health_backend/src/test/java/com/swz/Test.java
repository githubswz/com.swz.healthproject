package com.swz;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author : 苏文致
 * @date Date : 2021年07月14日 16:48
 * @Description: TODO:
 */
public class Test {
    /**
     * BigDecimal用法详解
     * 一、简介
     * Java在java.math包中提供的API类BigDecimal，用来对超过16位有效位的数进行精确的运算。双精度浮点型变量double可以处理16位有效数。在实际应用中，需要对更大或者更小的数进行运算和处理。float和double只能用来做科学计算或者是工程计算，在商业计算中要用java.math.BigDecimal。BigDecimal所创建的是对象，我们不能使用传统的+、-、*、/等算术运算符直接对其对象进行数学运算，而必须调用其相对应的方法。方法中的参数也必须是BigDecimal的对象。构造器是类的特殊方法，专门用来创建对象，特别是带有参数的对象。
     * <p>
     * <p>
     * 二、构造器描述
     * BigDecimal(int)       创建一个具有参数所指定整数值的对象。
     * BigDecimal(double) 创建一个具有参数所指定双精度值的对象。
     * BigDecimal(long)    创建一个具有参数所指定长整数值的对象。
     * BigDecimal(String) 创建一个具有参数所指定以字符串表示的数值的对象。
     * <p>
     * 三、方法描述
     * add(BigDecimal)        BigDecimal对象中的值相加，然后返回这个对象。
     * subtract(BigDecimal) BigDecimal对象中的值相减，然后返回这个对象。
     * multiply(BigDecimal)  BigDecimal对象中的值相乘，然后返回这个对象。
     * divide(BigDecimal)     BigDecimal对象中的值相除，然后返回这个对象。
     * toString()                将BigDecimal对象的数值转换成字符串。
     * doubleValue()          将BigDecimal对象中的值以双精度数返回。
     * floatValue()             将BigDecimal对象中的值以单精度数返回。
     * longValue()             将BigDecimal对象中的值以长整数返回。
     * intValue()               将BigDecimal对象中的值以整数返回。
     */

    @org.junit.Test
    public void BigDecemelTest (){
        //int a =128;
        //int b =256;
        // BigDecimal divide = new BigDecimal(a).divide(new BigDecimal(b), 2, ROUND_HALF_DOWN);
        // double v = divide.doubleValue();
        // System.out.println(v);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int i = calendar.get(Calendar.DAY_OF_MONTH);
        Date time = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(time));
    }


    @org.junit.Test
    public void TestClone (){
        //获得当前系统的毫秒值
        System.out.println(System.currentTimeMillis());
        //将一个数组拷贝一段到另一个数组中
        int[] ints = {1, 8, 6, 9, 5, 3};
        int[] ints1 = new int[9];
        System.arraycopy(ints, 1, ints1, 0, 5);
        System.out.println(Arrays.toString(ints1));
        //获得系统的环境变量
        Map<String, String> getenv = System.getenv();
        getenv.entrySet().forEach(x -> {
            System.out.print(x.getKey() + ":");
            System.out.println(x.getKey());
        });
        //获得系统的属性
        String propertieOS = System.getProperty("os.name");
        System.out.println(propertieOS);

        BigInteger bigInteger = new BigInteger("1203");
        BigInteger bigInteger1 = new BigInteger("1235");
        BigInteger add = bigInteger.add(bigInteger1);   //加
        BigInteger subtract = bigInteger.subtract(bigInteger1); //减
        BigInteger multiply = bigInteger.multiply(bigInteger1);//乘
        BigInteger divide = bigInteger.divide(bigInteger1);//除
        BigInteger[] bigIntegers = bigInteger.divideAndRemainder(multiply);//相除拿到商和余数
        BigInteger shang = bigIntegers[0];
        BigInteger yushu = bigIntegers[1];
        int i = shang.intValue();
        


    }

}