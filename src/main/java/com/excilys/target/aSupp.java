package com.excilys.target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class aSupp {
	public static void main(String[] args) {
	     List<String> l = new ArrayList(Arrays.asList("one", "two"));
	     Stream<String> sl = l.stream();
	     l.add("three");
	     
	     Optional<String> s = sl.max(Comparator.comparing(String::length));
	     //String s = sl.collect(Collectors.joining(" "));
	     System.out.println(s);
	}
}
