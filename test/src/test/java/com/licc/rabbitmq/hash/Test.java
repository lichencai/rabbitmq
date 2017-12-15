package com.licc.rabbitmq.hash;

import java.util.SortedMap;
import java.util.TreeMap;

public class Test {
	public static void main(String[] args) {
		TreeMap<Long, String> treeMap = new TreeMap<Long, String>();
		
		treeMap.put(8l, "8");
		treeMap.put(16l, "16");
		treeMap.put(24l, "24");
		
		SortedMap<Long, String> sortMap = treeMap.tailMap(15l);
		
		System.out.println(sortMap.isEmpty() ? treeMap.firstKey() : sortMap.firstKey());
	}
}
