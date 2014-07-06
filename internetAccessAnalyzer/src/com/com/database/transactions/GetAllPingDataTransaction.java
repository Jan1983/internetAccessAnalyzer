package com.database.transactions;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.database.model.PingData;
import com.util.Utils;

public class GetAllPingDataTransaction extends Transaction {
	private Date start;
	private Date end;
	
	public GetAllPingDataTransaction(Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	
	
  @SuppressWarnings("unchecked")
	@Override
	public Object execute() {
        Query query = entityManager.createNamedQuery("GetPingDataInPeriod");
        query.setParameter("startDate", Utils.formatDateToDbDate(start));
        query.setParameter("endDate", Utils.formatDateToDbDate(end));
        List<PingData> pingDataList = query.getResultList();
        return pingDataList;
    }
}
