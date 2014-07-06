package com.database.transactions;

import javax.persistence.EntityManager;

public abstract class Transaction {
	protected EntityManager entityManager;
	
	public abstract Object execute();
	
    public Object execute(EntityManager entityManager) {
    	this.entityManager = entityManager;
		return execute();
    }
    

}
