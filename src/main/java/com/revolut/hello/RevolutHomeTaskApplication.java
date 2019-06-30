package com.revolut.hello;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RevolutHomeTaskApplication {

	/*
	 * static{
	 * 
	 * }
	 */
	public static void main(String[] args) {
		System.setProperty("logfilename", SystemUtils.USER_DIR+"\\HomeTaskLogs");
		SpringApplication.run(RevolutHomeTaskApplication.class, args);
	}

}
