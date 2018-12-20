package com.fbm.mgmt.supervisor.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;

/**
 * 
 * @author Parasu17
 *
 */
public class FbmUtil {
	
	private static Method getDeclaredMethod(Class<?> objClass, String methodName) {
		for(Method meth : objClass.getDeclaredMethods()) {
			if(meth.getName().equals(methodName)) {
				return meth;
			}
		}
		return null;
	}

	public static <T> T getObjectFromResultSet(ResultSet rs, Class<T> objClass) {
		T result = null;
		try {
			result = objClass.getDeclaredConstructor().newInstance();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return result;
		}

		Field[] fields = objClass.getDeclaredFields();
		if(fields != null) {
			String fName = null;
			Object obj = null;
			String methodName = null;
			Method meth = null;
			for(Field f : fields) {
				try {
					fName = f.getName();
					obj = rs.getObject(fName);
					methodName = "set" + Character.toUpperCase(fName.charAt(0)) + fName.substring(1);
					meth = getDeclaredMethod(objClass, methodName);
					if(meth != null) {
						meth.invoke(result, meth.getParameterTypes()[0].cast(obj));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}

}
