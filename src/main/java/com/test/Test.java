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
		//���ñ���������
		ps.setNaem("1");
		ps.setUserName("2");
		String str = query(ps);
		System.out.println(str);
	}

	
	
	private static String query(Person person) {
		StringBuilder sb = new StringBuilder();
		//ͨ������,��ȡclass����
		Class p = person.getClass();
		//�жϴ�class�ǲ���ע����
		boolean exist = p.isAnnotationPresent(Table.class);
		if(!exist) {
			return null;
		}
		//�����,ǿ������ת����table
		Table table = (Table) p.getAnnotation(Table.class);
		String tablename = table.value();
		//ȡֵƴ��sql���,���ظ��������
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
			//���ｫ����getxxx ����,��������ͨ������ִ�ж�Ӧ��get�����õ�����ķ���ֵ
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
