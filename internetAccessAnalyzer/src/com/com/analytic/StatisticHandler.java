package com.analytic;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.database.DBConnection;
import com.database.model.PingData;
import com.database.transactions.GetAllPingDataTransaction;
import com.logger.UseLogger;

public class StatisticHandler {
	private static final Logger log = LogManager.getLogger(StatisticHandler.class.getName());
	private DBConnection connection;

	private enum Period {
		ALL, YEAR, MONTH, DAY
	}

	public StatisticHandler() throws IOException {
		connection = new DBConnection();
	}

	public static void main(String[] args) {
		UseLogger.init();
		try {
			StatisticHandler handler = new StatisticHandler();
			handler.start();
		} catch (IOException e) {
			log.error("", e);
		}
	}

	private void start() {
		connection.openEntityManager();
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date date = null;

		try {
			date = df.parse("03.07.2014");
		} catch (ParseException e) {
			log.error("", e);
		}
		;

		try {
			Period period = Period.YEAR;
			switch (period) {
			case DAY:
				log.info(getPingDataForDay(date).size());
				break;
			case MONTH:
				log.info(getPingDataForMonth(date).size());
				break;
			case YEAR:
				getPingDataForYear(date);
				break;
			case ALL:
				break;
			}
		} finally {
			connection.closeEntityManager();
		}
	}

	private List<PingData> getPingDataForDay(Date day) {
		Calendar c = Calendar.getInstance();
		c.setTime(day);
		c.add(Calendar.DATE, 1);
		Date nextDay = c.getTime();
		return getPingDataInPeriod(day, nextDay);
	}

	private List<PingData> getPingDataForMonth(Date month) {
		Calendar c = Calendar.getInstance();
		c.setTime(month);
		c.add(Calendar.MONTH, 1);
		Date nextDay = c.getTime();
		return getPingDataInPeriod(month, nextDay);

	}
	
	private List<PingData> getPingDataForYear(Date year) {
		Calendar c = Calendar.getInstance();
		c.setTime(year);
		c.add(Calendar.YEAR, 1);
		Date nextDay = c.getTime();
		return getPingDataInPeriod(year, nextDay);
	}

	
	@SuppressWarnings("unchecked")
	private List<PingData> getPingDataInPeriod(Date start, Date end) {
		List<PingData> pingData = (List<PingData>) connection.execute(new GetAllPingDataTransaction(start, end));
		return pingData;
	}
}
