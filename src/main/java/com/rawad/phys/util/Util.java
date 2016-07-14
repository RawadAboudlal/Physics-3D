package com.rawad.phys.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Util {
	
	public static final String NL = "\r\n";// System.lineSeperator(); // Current one much better and more consistent.
	
	private Util() {}
	
	public static double clamp(double value, double min, double max) {
		
		if(value < min) {
			return min;
		} else if(value > max) {
			return max;
		}
		
		return value;
		
	}
	
	public static String getStringFromLines(String[] lines, String regex, boolean addRegexToEnd) {
		
		String re = "";
		
		for(int i = 0; i < lines.length; i++) {
			
			if(lines[i] == null || lines[i].isEmpty()) continue;// Questionable...
			
			re += lines[i];
			
			if(i >= lines.length - 1) {
				
				if(addRegexToEnd) {
					re += regex;
				}
				
			} else {
				
				re += regex;
				
			}
			
		}
		
		return re;
		
	}
	
	public static <T> T getNullSafe(T valueToCheck, T defaultValue) {
		
		if(valueToCheck != null) {
			return valueToCheck;
		}
		
		return defaultValue;
		
	}
	
	public static int parseInt(String potentialInt) {
		
		try {
			return Integer.parseInt(potentialInt);
		} catch(Exception ex) {
			return 0;
		}
		
	}
	
	public static double parseDouble(String potentialDouble) {
		
		try {
			return Double.parseDouble(potentialDouble);
		} catch(Exception ex) {
			return 0d;
		}
		
	}
	
	public static float parseFloat(String potentialFloat) {
		
		try {
			return Float.parseFloat(potentialFloat);
		} catch(Exception ex) {
			return 0f;
		}
		
	}
	
	public static <T extends Closeable> void silentClose(T streamToClose) {
		
		try {
			streamToClose.close();
		} catch(IOException | NullPointerException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.WARNING, "The stream: " + streamToClose + " could not be "
					+ "closed", ex);
		}
		
	}
	
	@SafeVarargs
	public static <T> T[] append(T[] arr, T... lastElements) {
		final int length = arr.length;
		
		arr = Arrays.copyOf(arr, length + lastElements.length);
		
		for(int i = 0; i < lastElements.length; i++) {
			arr[length + i] = lastElements[i];
		}
		
	    return arr;
	}
	
	@SafeVarargs
	public static <T> T[] prepend(T[] arr, T... firstElements) {
		final int length = firstElements.length;
		
		firstElements = Arrays.copyOf(firstElements, length + arr.length);
		
		for(int i = length; i < firstElements.length; i++) {
			firstElements[i] = arr[i - length];
		}
		
	    return firstElements;
	}
	
	/**
	 * Should only give an error at the level of another class using this method. Putting a log on that shows that
	 * it's casting to the same object...
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends A, A> T cast(A obj) {
		return (T) obj;
	}
	
}
