package com.bigroi.stock.bean.db;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.bigroi.stock.util.BaseTest;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class DbClassesTest extends BaseTest {

	@Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                {Blacklist.class}, 
                {Company.class}, 
                {CompanyAddress.class}, 
                {Deal.class}, 
                {Email.class}, 
                {Label.class}, 
                {Lot.class}, 
                {Product.class},
                {Proposition.class},
                {TempKey.class},
                {Tender.class},
                {UserRole.class}
        });
    }
    
    @Test
    @Parameters(method = "testData")
    public void getterTest(Class<?> clazz) throws Exception{
    	Object instance = createObject(clazz, true);
    	for (Field field : clazz.getDeclaredFields()){
    		if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
				continue;
			}
    		//given
    		PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
    		if (descriptor.getReadMethod() != null){
    		//when
    			Object methodResult = descriptor.getReadMethod().invoke(instance);
    		//than
    			boolean accessable = field.canAccess(instance);
    			field.setAccessible(true);
    			Assert.assertEquals(field.get(instance), methodResult);
    			field.setAccessible(accessable);
    		}
    	}
    }
    
    @Test
    @Parameters(method = "testData")
    public void setterTest(Class<?> clazz) throws Exception{
    	Object instance = clazz.getConstructor().newInstance();
    	for (Field field : clazz.getDeclaredFields()){
    		if (Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
				continue;
			}
    		//given
    		PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
    		if (descriptor.getWriteMethod() != null){
    			Object value = getRundomVlaue(field, true);
    		//when
    			descriptor.getWriteMethod().invoke(instance, value);
    		//than
    			boolean accessable = field.canAccess(instance);
    			field.setAccessible(true);
    			Assert.assertEquals(value, field.get(instance));
    			field.setAccessible(accessable);
    		}
    	}
    }
    
}
