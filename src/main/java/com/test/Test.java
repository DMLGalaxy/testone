package com.test;

import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import com.util.Person;
import com.util.Apptest.Column;
import com.util.Apptest.Table;

public class Test {

	public static void main(String[] args) {
		Person ps = new Person();
		//设置表名和列名
		ps.setNaem("1");
		ps.setUserName("2");
		String str = query(ps);
		System.out.println(str);
	}

	
	
	private static String query(Person person) {
		StringBuilder sb = new StringBuilder();
		//通过反射,获取class对象
		Class p = person.getClass();
		//判断此class是不是注解类
		boolean exist = p.isAnnotationPresent(Table.class);
		if(!exist) {
			return null;
		}
		//如果是,强制类型转换成table
		Table table = (Table) p.getAnnotation(Table.class);
		String tablename = table.value();
		//取值拼接sql语句,并重复这个过程
		sb.append("select * from ").append(" tablename ").append(" where 1=1 ");
		Field[] fArray = p.getDeclaredFields();
		for(Field field : fArray) {
			boolean fExist = field.isAnnotationPresent(Column.class);
			if(!fExist) {
				return null;
			}
			Column column = field.getAnnotation(Column.class);
			String columnname = column.value();
			String fieldname = field.getName();
			Object fieldValue = null;
			//这里将生成getxxx 方法,用于下面通过反射执行对应的get方法拿到里面的返回值
			String getMethodName = "get"+fieldname.substring(0, 1).toUpperCase()+fieldname.substring(1);
			try {
				Method method = p.getMethod(getMethodName);
				fieldValue = method.invoke(person);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			sb.append(" and ").append(columnname).append("=").append(fieldValue);
		}
		return sb.toString();
	}
}
