package org.zerock.myapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class JPAClientTest {
	
	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
	} // beforeAll
	
	@BeforeEach
	void beforeEach() {
		log.trace("beforeEach() invoked.");
		
	} // beforeEach

	@AfterAll
	void afterAll() {
		log.trace("afterAll() invoked.");
		
	} // afterAll
	
	@AfterEach
	void afterEach() {
		log.trace("afterEach() invoked.");
		
	} // afterEach
	
	
//	@Disabled
	@Test
	@Order(1)
	@DisplayName("contextLoads")
//	@Timeout(value=1L, unit=TimeUnit.SECONDS)
	@Timeout(1L)
	void contextLoads() {
		log.trace("contextLoads() invoked.");
		
	} // contextLoads
	
	
} // end class
