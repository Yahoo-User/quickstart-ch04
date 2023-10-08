package org.zerock.myapp.jpa;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.jupiter.api.*;
import org.zerock.myapp.domain.Board;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Log4j2
@NoArgsConstructor

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JPABootstrapProcessTests {

	/*	- Type Hierarchies:

        <C> SessionFactoryImpl (***)
                    implements  <I> SessionFactoryImplementor
                                    extends <I> SessionFactory (***)
                                                extends <I> Closeable
                                                extends <I> EntityManagerFactory (***)
    */


    @Test
    @Order(1)
    @DisplayName("1. Getting `javax.persistence.EntityManagerFactory`")
    void testGetEntityManagerFactory() {
        log.trace("testGetEntityManagerFactory() invoked.");

        // Returns `org.hibernate.internal.SessionFactoryImpl`
        @Cleanup    // *NOT* AutoCloseable, But Resource.
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("H2");

        Objects.requireNonNull(entityManagerFactory);
        log.info("\t+ entityManagerFactory: {}", entityManagerFactory);
    } // testGetEntityManagerFactory

    @Test
    @Order(2)
    @DisplayName("2. Getting `org.hibernate.SessionFactory`")
    void testGetSessionFactory() {
        log.trace("testGetSessionFactory() invoked.");

        // Returns `org.hibernate.internal.SessionFactoryImpl`
        SessionFactory sessionFactory = (SessionFactory) Persistence.createEntityManagerFactory("H2");

        try (sessionFactory) {  // AutoCloseable
            assertNotNull(sessionFactory);
            log.info("\t+ sessionFactory: {}", sessionFactory);
        } // try-with-resources
    } // testGetSessionFactory

    @Test
    @Order(3)
    @DisplayName("3. Saving Entities")
    void testSavingEntities() {
        log.trace("testSavingEntities() invoked.");

        // Returns `org.hibernate.internal.SessionFactoryImpl`
        SessionFactory sessionFactory = (SessionFactory) Persistence.createEntityManagerFactory("H2");

        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction tx = session.getTransaction();

            try (session) {
                tx.begin();

                for(int i=1; i<=3; i++) {
                    Board board = new Board();

                    board.setTitle("TITLE_" + i);
                    board.setContent("CONTENT_" + i);
                    board.setWriter("WRITER_" + i);

                    // 1st. method : Serializable save(Object object);
                    session.save(board);

                    // 2nd. method : Serializable save(String entityName, Object object);
                    // `entityName` is case-insensitive. (***)
//                    session.save("board", board);

                    // ---

                    // 3rd. method : void saveOrUpdate(Object object);
//                    session.saveOrUpdate(board);

                    // 4th. method : void saveOrUpdate(String entityName, Object object);
                    // `entityName` is case-insensitive. (***)
//                    session.saveOrUpdate("board", board);
                } // for

                tx.commit();
            } catch(RuntimeException e) {
                tx.rollback();

                throw e;
            } finally {
                TransactionStatus transactionStatus = tx.getStatus();
                assertTrue(transactionStatus.isOneOf(TransactionStatus.COMMITTED, TransactionStatus.ROLLED_BACK));
            } // try-catch-finally
        } // try-with-resources
    } // testSavingEntities

    @Test
    @Order(4)
    @DisplayName("4. Updating Entities - Method #1")
    void testUpdatingEntitiesByMethod1() {
        log.trace("testUpdatingEntitiesByMethod1() invoked.");

        @Cleanup EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("H2");
        @Cleanup EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();

            /*
             * 1) Automatically found entity in the *active* transaction belongs to the Persistence Context. (***)
             * 2) Updating Entity In The `Persistence context` Updated Automatically When TX Completes. (***)
             * 3) *NOT* Necessary To Update Entity Explicitly With `saveOrUpdate()` or `update()` Method. (***)
             */
            Board foundBoard = entityManager.find(Board.class, 2L);

            if(foundBoard != null) {
                foundBoard.setCnt(foundBoard.getCnt() + 1);
                log.info("\t+ foundBoard: {}", foundBoard);
            } // if

            tx.commit();
        } catch(RuntimeException e) {
            tx.rollback();

            throw e;
        } // try-catch
    } // testUpdatingEntitiesByMethod1

    @Test
    @Order(5)
    @DisplayName("4. Updating Entities - Method #2")
    void testUpdatingEntitiesByMethod2() {
        log.trace("testUpdatingEntitiesByMethod2() invoked.");

        SessionFactory sessionFactory = (SessionFactory) Persistence.createEntityManagerFactory("H2");

        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction tx = session.getTransaction();

            try (session) {
                tx.begin();

                /*
                 * 1) Automatically found entity in the *active* transaction belongs to the Persistence Context. (***)
                 * 2) Updating Entity In The `Persistence context` Updated Automatically When TX Completes. (***)
                 * 3) *NOT* Necessary To Update Entity Explicitly With `saveOrUpdate()` or `update()` Method. (***)
                 */
                Board foundBoard = session.find(Board.class, 4L);

                if(foundBoard != null) {
                    foundBoard.setCnt(foundBoard.getCnt() + 1);

                    // OK - 2nd. method
//                session.saveOrUpdate(foundBoard);

                    // OK - 3rd. method
//                session.update(foundBoard);

                    // OK - 4th. method
//                session.update("board", foundBoard);
                } // if

                tx.commit();
            } catch(RuntimeException e) {
                tx.rollback();

                throw e;
            } // try-catch
        } // try-with-resources
    } // testUpdatingEntitiesByMethod2

    @Test
    @Order(6)
    @DisplayName("4. Updating Entities - Method #3")
    void testUpdatingEntitiesByMethod3() {
        log.trace("testUpdatingEntitiesByMethod3() invoked.");

        SessionFactory sessionFactory = (SessionFactory) Persistence.createEntityManagerFactory("H2");

        try (sessionFactory) {
            Session session = sessionFactory.openSession();
            Transaction tx = session.getTransaction();

            try (session) {
                tx.begin();

                /*
                 * 1) Automatically found entity in the *active* transaction belongs to the Persistence Context. (***)
                 * 2) Updating Entity In The `Persistence context` Updated Automatically When TX Completes. (***)
                 * 3) *NOT* Necessary To Update Entity Explicitly With `saveOrUpdate()` or `update()` Method. (***)
                 */

                /*
                 * ----------------------------------
                 * Caution : from EntityName - `EntityName` is `case-sensitive`. (***)
                 * ----------------------------------
                 */

                Query<Board> query = session.createQuery("from Board", Board.class);

                List<Board> list = query.list();
                if(list != null) {

                    for (Board foundBoard : list) {
                        foundBoard.setCnt(foundBoard.getCnt() + 1);
                    } // enhanced for

                } // if

                tx.commit();
            } catch(RuntimeException e) {
                tx.rollback();

                throw e;
            } // try-catch
        } // try-with-resources
    } // testUpdatingEntitiesByMethod3

} // end class
