package com.database.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@NamedQuery(name="GetPingDataInPeriod", query="select p from PingData p where p.transferDate > :startDate and p.transferDate < :endDate")

@Entity
@Table(name="PINGDATA")
public class PingData {

	@Id
    @Column
    private String transferDate;

    @Column
    private boolean successState;

    @Column
    private long duration; // in ms

    @Transient
    private final DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
      
	public Date getTransferDate() throws ParseException {
		return dateFormat.parse(transferDate);
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = dateFormat.format(transferDate);
	}

	public boolean isSuccessState() {
		return successState;
	}

	public void setSuccessState(boolean successState) {
		this.successState = successState;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}


}
