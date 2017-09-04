package com.gospell.nms.service.netty.base.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分组参数注解  注解于帧体中id和type字段
 * @author zhaohw
 * 2017年9月4日下午2:59:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface GroupField {
	
	boolean id() default false; 
	
	boolean type() default false; 
}
