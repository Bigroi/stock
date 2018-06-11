package com.bigroi.stock.json;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Column {

	public boolean allowSorting() default false;
	
	public FilterMethod filterMethod() default FilterMethod.NONE;
	
	public String value();
	
}
