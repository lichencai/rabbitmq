package com.licc.rabbitmq.test;

public enum GameEnum {
	//  每一个变量都是一个对象
	BIG(10,"big"){
		//  重写Enum上的getLabel的方法
		@Override
		public String getLabel() {
			return super.getLabel();
		}
		//  实现抽象方法
		@Override
		public void testEnum() {
		}
	},  
    SMALL(11,"small") {
		@Override
		public void testEnum() {
		}
	},  
    FATTER(12,"fatter") {
		@Override
		public void testEnum() {
		}
	};

	private int value;	//  定义枚举类自身的变量
	private String label;	//  定义枚举类自身的变量
	//  子类的构造方法
	private GameEnum(int value, String label) {
		this.value = value;
		this.label = label;
	}
	//  相当于定义在Enum中的方法，可以在变量中进行重写
	public int getValue() {
		return this.value;
	}
	public String getLabel() {
		return this.label;
	}
	@Override
	public String toString() {
		return super.toString();
	}
	//  定义了一个抽象方法，故在枚举类的变量中，需要对这个方法进行实现，相当于把这个抽象方法定义在Enum中
	public abstract void testEnum();
}
