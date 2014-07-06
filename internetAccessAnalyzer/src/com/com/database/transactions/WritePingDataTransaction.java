package com.database.transactions;

import com.database.model.PingData;

public class WritePingDataTransaction extends Transaction {
    private PingData pingData;

    public WritePingDataTransaction(PingData pingData) {
        this.pingData = pingData;
    }

    @Override
    public Object execute() {
        // write
    	entityManager.persist(pingData);
		return null;
    }
}
