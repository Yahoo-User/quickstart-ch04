package org.zerock.myapp;

import java.util.Arrays;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.zerock.myapp.domain.Board;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class JPAInsert {
	// Defined in the `classpath:/META-INF/persistence.xml` JPA configuration file.
	private static final String persistenceUnitName = "H2";
	
	
    public static void main( String[] args ) {
    	log.trace("main({}) invoked.", Arrays.toString(args));

		// Step1. Create a `EntityManagerFactory` which is *NOT* AutoCloseable, but have `close()` method.
		@Cleanup
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);

		// Step2. Create a `EntityManager` from `EntityManagerFactory`.
    	@Cleanup
		EntityManager entityManager = entityManagerFactory.createEntityManager();
    	
    	// Step3. Create a `EntityTransaction` from `EntityManager`
    	EntityTransaction tx = entityManager.getTransaction();
    	
    	try {
    		// Step4. Transaction Begins
    		tx.begin();
    		
    		Board board = new Board();
    		
    		board.setTitle(UUID.randomUUID().toString());
    		board.setContent("NEW_CONTENT");
    		board.setWriter("Yoseph");
    		
    		log.info("\t+ Before : {}", board);
    		
    		// Step5. To save a Entity into Persistence Context by EntityManager
			entityManager.persist(board);

    		log.info("\t+ After : {}", board);
    		
    		// Step6. Transaction Committed
    		tx.commit();
    	} catch(Exception e) {
    		// Step6. Transaction Rolled Back
    		tx.rollback();
    		
    		throw e;
    	} // try-finally
    } // main
    
} // end class
