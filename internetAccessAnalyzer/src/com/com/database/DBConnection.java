package com.database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.constant.Connection;
import com.database.transactions.Transaction;

public class DBConnection {
    protected EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;

    public Map<String, String> getDBProperties() throws IOException {
            Map<String, String> entityManagerProperties = new HashMap<String, String>();
            Properties connectionProperties = new Properties();
            connectionProperties.load(DBConnection.class.getResourceAsStream("/connection.properties"));
            entityManagerProperties.put("javax.persistence.jdbc.user", connectionProperties.getProperty(Connection.DB_USER));
            entityManagerProperties.put("javax.persistence.jdbc.password", connectionProperties.getProperty(Connection.DB_PW));
            entityManagerProperties.put("javax.persistence.jdbc.url", connectionProperties.getProperty(Connection.DB_URL));
            entityManagerProperties.put("javax.persistence.jdbc.driver", connectionProperties.getProperty(Connection.DB_DRIVER));

            return entityManagerProperties;
    }
    
    public DBConnection() throws IOException {
    	if (entityManagerFactory == null)
    		entityManagerFactory = Persistence.createEntityManagerFactory("internetAccessAnalyzer", getDBProperties());
    }
    
    public void openEntityManager() {
    	entityManager = entityManagerFactory.createEntityManager();
    }
    
    public void closeEntityManager() {
    	entityManager.close();
    }

    public static void closeEntityManagerFactory() {
    	if (entityManagerFactory != null)
    		entityManagerFactory.close();
    }
    
    public Object execute(Transaction transaction) {
        Object result;
        entityManager.getTransaction().begin();
        result = transaction.execute(entityManager);
        entityManager.getTransaction().commit();
        return result;
    }
}
