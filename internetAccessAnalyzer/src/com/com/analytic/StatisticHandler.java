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
import com.logger.UseLogger;

public class StatisticHandler {
	private static final Logger log = LogManager.getLogger(StatisticHandler.class.getName());
	private DBConnection connection;
	private PingDataEvaluator evaluator;
	
	private enum Period {
		ALL, YEAR, MONTH, DAY
	}

	public StatisticHandler() throws IOException {
		connection = new DBConnection();
		evaluator = new PingDataEvaluator(connection);
	}

	public static void main(String[] args) {
		UseLogger.init();
		try {
			StatisticHandler handler = new StatisticHandler();
			handler.start();
		} catch (IOException | ParseException e) {
			log.error("", e);
		}
	}
	
	private Date getExampleDate() throws ParseException {
		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		try {
			return df.parse("03.07.2014");
		} catch (ParseException e) {
			log.error("", e);
			throw e;
		}
	}
	
	private Period getExamplePeriod() {
		return Period.YEAR;
	}

	private void start() throws ParseException {
		connection.openEntityManager();
		Date date = getExampleDate(); 
		try {
			switch (getExamplePeriod()) {
			case DAY:
				printRatio(evaluator.getPingDataForDay(date));				
				break;
			case MONTH:
				printRatio(evaluator.getPingDataForMonth(date));
				break;
			case YEAR:
				printRatio(evaluator.getPingDataForYear(date));
				break;
			case ALL:
				break;
			}
		} finally {
			connection.closeEntityManager();
		}
	}

	private void printRatio(List<PingData> data) {
		log.info("Success: " + IOUtils.round(evaluator.getSuccessPingsInPercent(data)) + "%");
		log.info("Failed:  " + IOUtils.round(evaluator.getFailedPingsInPercent(data)) + "%");
	}

}
