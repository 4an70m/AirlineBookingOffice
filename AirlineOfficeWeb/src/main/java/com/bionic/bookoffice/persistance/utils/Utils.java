package com.bionic.bookoffice.persistance.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Utils {

	/**
	 * <p>
	 * <strong>public <T> T setIfAlter(T objectA, T objectB)</strong>
	 * </p>
	 * Using reflections invokes every get method from the two objects (but not
	 * getId()) then compares their output (with equals). If parameter of
	 * objectA alters from same parameter of objectB then appropriate set()
	 * method is invoked for objectB with a parameter got from get() method. If
	 * no set() method is found for appropriate get() method - no altering is
	 * performed.
	 * 
	 * @param objectA
	 *            - what is going to alter other object
	 * @param objectB
	 *            - the object we are altering
	 * @return <strong>objectB</strong> - altered object
	 */
	public static <T> T setIfAlter(T objectA, T objectB) {
		Method[] methods = objectA.getClass().getMethods();

		for (Method mA : methods) {
			if (mA.getName().contains("get") && !mA.getName().equals("getId")) {
				Object getValueA = null;
				Object getValueB = null;
				String methodNameWithoutGet = mA.getName().substring(3);
				try {
					getValueA = mA.invoke(objectA, (Object[]) null);
					getValueB = mA.invoke(objectB, (Object[]) null);
					if (getValueA.equals(getValueB)) {
						continue;
					} else {
						for (Method mB : methods) {
							if (mB.getName().equals(
									"set" + methodNameWithoutGet)) {
								mB.invoke(objectB, getValueA);
								break;
							}
						}
					}
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					System.out
							.println("Invoking a 'get()' method has caused some mistake.");
					e.printStackTrace();
				}
			}
		}
		return objectB;
	}

}
