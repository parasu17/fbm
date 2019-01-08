package com.fbm.mgmt.supervisor.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

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

	private static List<String> getColumnNames(ResultSet rs) {
		List<String> columnNames = new ArrayList<String>();
		int cc = 0;
		ResultSetMetaData rsMeta = null;
		try {
			rsMeta = rs.getMetaData();
		} catch (Exception e1) {
			return columnNames;
		}

		try {
			cc = rsMeta.getColumnCount();
		} catch (Exception e) {
			return columnNames;
		}

		for (int i = 1; i <= cc; i++) {
			try {
				columnNames.add(rsMeta.getColumnName(i));
			} catch (Exception e) {
			}
		}
		return columnNames;
	}

	public static <T> T getObjectFromResultSet(ResultSet rs, Class<T> objClass) {
		T result = null;
		try {
			result = objClass.getDeclaredConstructor().newInstance();
		} catch (Exception e1) {
			return result;
		}

		List<String> columnNames = getColumnNames(rs);
	
		Field[] fields = objClass.getDeclaredFields();
		if(fields != null) {
			String fName = null;
			Object obj = null;
			String methodName = null;
			Method meth = null;
			
			for(Field f : fields) {
				try {
					fName = f.getName();
					if(!columnNames.contains(fName)) {
						continue;
					}
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

	public static Double getDouble(String doubleValue) {
		Double result = null;
		try {
			result = Double.parseDouble(doubleValue);
		} catch(Exception exp) {
			
		}
		return result;
	}
}
