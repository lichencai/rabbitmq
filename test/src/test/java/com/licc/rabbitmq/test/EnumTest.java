package com.licc.rabbitmq.test;

public class EnumTest {
	public static void main(String[] args) {
		
		GameEnum big = GameEnum.BIG;
		
		System.out.println(big.getValue());
		
		System.out.println(big.getLabel());
		
		System.out.println(big.ordinal());
		
	    boolean bool = GameEnum.BIG.equals(big);
	    System.out.println(bool);
		System.out.println(big);
	}
}
