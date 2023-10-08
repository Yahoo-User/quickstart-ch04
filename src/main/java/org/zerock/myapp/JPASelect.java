package org.zerock.myapp;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.zerock.myapp.domain.Board;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class JPASelect {
	// Defined in the `classpath:/META-INF/persistence.xml` JPA configuration file.
	private static final String persistenceUnitName = "H2";

	
	public static void main(String[] args) {
		log.trace("main({}) invoked.", Arrays.toString(args));

		// Step1. Create a `EntityManagerFactory` which is *NOT* AutoCloseable, but have `close()` method.
		@Cleanup
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);

		// Step2. Create a `EntityManager` from `EntityManagerFactory`.
		@Cleanup
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		// Step3. Search A Entity Using Specified Entity Type & Primary Key Value.
		Board foundBoard = entityManager.find(Board.class, 1L);
		log.info("\t+ foundBoard: {}", foundBoard);
		
		// Step4. Check Whether The Specified Entity Exists.
		Board checkedBoard = new Board();
		checkedBoard.setSeq(2L);

		boolean isContains = entityManager.contains(foundBoard);
		log.info("\t+ isContains: {}", isContains);
	} // main

} // end class
