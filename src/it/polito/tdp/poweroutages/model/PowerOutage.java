package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutage implements Comparable<PowerOutage>{
	
	private int id;
	private int nerc_id;
	private int customers_affected;
	private LocalDateTime date_event_began;
	private LocalDateTime date_event_finished;
	
	public PowerOutage(int id, int nerc_id, int customers_affected, LocalDateTime date_event_began, LocalDateTime date_event_finished) {
		this.id = id;
		this.nerc_id = nerc_id;
		this.customers_affected = customers_affected;
		this.date_event_began = date_event_began;
		this.date_event_finished = date_event_finished;
	}

	public int getId() {
		return id;
	}

	public int getNerc_id() {
		return nerc_id;
	}

	public int getCustomers_affected() {
		return customers_affected;
	}

	public LocalDateTime getDate_event_began() {
		return date_event_began;
	}


	public LocalDateTime getDate_event_finished() {
		return date_event_finished;
	}
	
	public long getHours() {
		long oreBlackout = ChronoUnit.HOURS.between(getDate_event_began(), getDate_event_finished());
		return oreBlackout;
	}

	@Override
	public String toString() {
		return date_event_began.getYear()+" "+date_event_began+" "+date_event_finished+" "+this.getHours()+" "+customers_affected;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(PowerOutage other) {
		if(this.getDate_event_began().getYear() != other.getDate_event_began().getYear())
			return this.getDate_event_began().getYear() - other.getDate_event_began().getYear();
		else if(this.getDate_event_began().getMonth() != other.getDate_event_began().getMonth())
			return this.getDate_event_began().getMonthValue() - other.getDate_event_began().getMonthValue();
		else
			return this.getDate_event_began().getDayOfMonth() - other.getDate_event_began().getDayOfMonth();
					
	}
	
	
	

}
