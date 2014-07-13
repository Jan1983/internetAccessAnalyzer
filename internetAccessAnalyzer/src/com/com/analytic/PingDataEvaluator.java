package com.analytic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.database.DBConnection;
import com.database.model.PingData;
import com.database.transactions.GetAllPingDataTransaction;

public class PingDataEvaluator {
	private DBConnection connection;
	
	public PingDataEvaluator(DBConnection connection) {
		this.connection = connection;
	}
	
	public List<PingData> getPingDataForDay(Date date) {	  return getPingDataForType(date, Calendar.DATE);	}
	public List<PingData> getPingDataForMonth(Date date) {	return getPingDataForType(date, Calendar.MONTH); }
	public List<PingData> getPingDataForYear(Date date) {	return getPingDataForType(date, Calendar.YEAR);	}
	
	private List<PingData> getPingDataForType(Date date, int type) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(type, 1);
		Date nextDate = c.getTime();
		return getPingDataInPeriod(date, nextDate);
	}
	
	@SuppressWarnings("unchecked")
	private List<PingData> getPingDataInPeriod(Date start, Date end) {
		List<PingData> pingData = (List<PingData>) connection.execute(new GetAllPingDataTransaction(start, end));
		return pingData;
	}
	
	public double getFailedPingsInPercent(List<PingData> pingData) {
		int failed = 0;
		for (PingData data : pingData) {
			if (!data.isSuccessState())
				failed++;
		}
		return failed / (double)pingData.size();
	}
	
	public double getSuccessPingsInPercent(List<PingData> pingData) {
		return 100 - getFailedPingsInPercent(pingData);
	}
}
