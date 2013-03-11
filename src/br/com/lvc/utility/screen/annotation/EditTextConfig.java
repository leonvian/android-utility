package br.com.lvc.utility.screen.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import android.text.InputType;

@Retention(RetentionPolicy.RUNTIME)
public @interface EditTextConfig {

	/*
	public static final int NORMAL = InputType.TYPE_TEXT_VARIATION_NORMAL;
	public static final int PASSWORD = InputType.TYPE_TEXT_VARIATION_PASSWORD;
	public static final int NO_LIMIT = -1;
	
	
	int limitCaracter() default NO_LIMIT;
	int inputType() default NORMAL; */
	
}
