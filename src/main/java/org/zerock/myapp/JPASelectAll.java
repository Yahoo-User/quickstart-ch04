package org.zerock.myapp;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.zerock.myapp.domain.Board;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class JPASelectAll {
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

		// Step3. Define a JPQL query.
//		String jpql = "SELECT b FROM Board b ORDER BY b.seq DESC";
		String jpql = "from Board";

		// Step4. Create a Typed Query.
		TypedQuery<Board> typedQuery = entityManager.createQuery(jpql, Board.class);
		log.info("\t+ typedQuery: {}", typedQuery);

		/*
		 * Step5. Execute the JPQL and Get Result Set Data.
		 *
		 * Without a transaction, All found entities are *detached* from the persistence context. (***)
		 *
		 * This causes the found entities to remove the given entity from the persistence context,
		 * causing a managed entity to become detached. (***)
		 *
		 * Un-flushed changes made to the entity if any (including removal of the entity),
		 * will not be synchronized to the database. (***)
		 */
		List<Board> list = typedQuery.getResultList();
		list.forEach(log::info);
	} // main

} // end class
