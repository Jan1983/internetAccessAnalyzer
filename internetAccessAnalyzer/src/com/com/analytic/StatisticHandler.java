package com.analytic;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
		Date start = null;
		Date end = null;
		try {
			start = df.parse("03.07.2014");
			end = df.parse("04.07.2014");
		} catch (ParseException e) {
			e.printStackTrace();
		};
		
		try {
			Period period = Period.ALL;
			switch (period) {
			case DAY:
				break;
			case MONTH:
				break;
			case YEAR:
				break;
			case ALL:
				log.info(String.valueOf(getPingDataInPeriod(start, end).size()));
				break;
			}
		} finally {
			connection.closeEntityManager();
		}
	}

	@SuppressWarnings("unchecked")
	private List<PingData> getPingDataInPeriod(Date start, Date end) {
		List<PingData> pingData = (List<PingData>) connection.execute(new GetAllPingDataTransaction(start, end));
		return pingData;
	}
}
