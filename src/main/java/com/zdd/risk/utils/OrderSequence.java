package com.zdd.risk.utils;

import org.apache.tomcat.jni.Thread;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component

public class OrderSequence extends Thread {
 
	 private static int orderNum = 0;
    private static String date ;
	      
//	    public static void main(String[] args) throws InterruptedException {
////	        for (int i = 0; i < 10000; i++) {
////	            System.out.println(TestOrder.getOrderNo());
////	            Thread.sleep(1000);
////	        }
//
//
//	            System.out.println(OrderSequence.getOrderNo1());
//	    }
	  
	    /** 
	     * 生成订单编号 
	     * @return 
	     */  
	    public static synchronized int getOrderNo() {


	        String str = new SimpleDateFormat("yyMMddmm").format(new Date());
	        if(date==null||!date.equals(str)){
	            date = str;
	            orderNum  = 0;
	        }
	        orderNum ++;
	     //   int orderNo = int.parseInt((date)) * 1000;

	        int orderNo=Integer.parseInt((date)) * 10000;

	        orderNo += orderNum ;
	        return orderNo ;
	    }


	/**
	 * 生成订单编号
	 * @return
	 */
	public static synchronized long getOrderNo1() {

		long str =System.currentTimeMillis();
		orderNum ++;
		long orderNo=str * 100000;
		orderNo += orderNum ;
		System.out.println("随机生成的bizno为："+orderNo);
		return orderNo ;
	}


}