package org.zerock.myapp;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.zerock.myapp.domain.Board;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Arrays;


@Log4j2
public class JPADelete {
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

		// Step3. Create a `EntityTransaction` from `EntityManager`
		EntityTransaction tx = entityManager.getTransaction();
		
		try {
			// Step4. Transaction Begins
			tx.begin();

			// Step5. Search The Specified Entity Using Entity Type & Primary Key.
			Board foundBoard = entityManager.find(Board.class, 3L);
			log.trace("\t+ foundBoard: {}", foundBoard);
			
			// Step6. Delete The Found Entity.
			if(entityManager.contains(foundBoard)) {
				entityManager.remove(foundBoard);
			} // if

			// Step7. Transaction Committed.
			tx.commit();
		} catch(Exception e) {
			// Step7. Transaction Rolled Back.
			tx.rollback();
			
			throw e;
		} // try-catch
	} // main

} // end class
