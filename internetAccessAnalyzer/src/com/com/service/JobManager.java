package com.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.database.DBConnection;
import com.database.model.PingData;
import com.database.transactions.WritePingDataTransaction;
import com.logger.UseLogger;

public class JobManager {
	private PingData pingResult;
	private static final Logger log = LogManager.getLogger(JobManager.class.getName());

	public static void main(String[] args) {
		UseLogger.init();
		JobManager manager = new JobManager();
		try {
			manager.start();
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	private void start() throws InterruptedException, IOException {
		try {
			while (true) {
				ping();
				writeBackIntoDatabase();

				long duration = pingResult.getDuration();
				if (duration < 5000)
					Thread.sleep(5000 - duration);
			}
		} finally {
			DBConnection.closeEntityManagerFactory();
		}
	}

	private void ping() {
		pingResult = new PingData();
		Date timeBeforePing = new Date();
		String url = "http://www.google.de";
		try {
			final URLConnection connection = new URL(url).openConnection();
			connection.connect();
			pingResult.setSuccessState(true);
		} catch (final MalformedURLException e) {
			throw new IllegalStateException("Bad URL: " + url, e);
		} catch (final IOException e) {
			pingResult.setSuccessState(false);
		}
		pingResult.setTransferDate(timeBeforePing);
		pingResult.setDuration(new Date().getTime() - timeBeforePing.getTime());

		if (pingResult.isSuccessState())
			log.info("Ping www.google.de in " + pingResult.getDuration() + " ms");
		else
			log.info("Zeitüberschreitung der Anforderung.");
	}

	private void writeBackIntoDatabase() throws IOException {
		DBConnection connection = new DBConnection();
		connection.execute(new WritePingDataTransaction(pingResult));
		connection.closeEntityManager();
	}
}
