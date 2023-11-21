package com.clean.code.springBoot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.xml.crypto.Data;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class SpringBoot2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot2Application.class, args);
	}
//	@Scheduled(fixedRate = 1000L)
//	public void startRate(){
//		System.out.println("new rate "+new Date());
//	}
//	@Scheduled(fixedDelay = 1000L)
//	public void startDelay(){
//		System.out.println("startDelay "+new Date());
//	}

//	@Scheduled(cron = "0 01 16 * * *")
//	public void startDelay(){
//		System.out.println("startDelay "+new Date());
//	}
//

}
