package io.javabrains.coronavirustracker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;
@DynamicUpdate
@Component
@Entity
@Table(name = "Coviddatadeaths")
public class LocationStatsDeaths {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(nullable=true)
    private String state;
	
	@Column(nullable=true)
    private String country;
	
	@Column(nullable=true)
	private int latestTotalDeaths;
	
	@Column(nullable=true)
	private String totalDeaths = Integer.toString(latestTotalDeaths);
	
	@Column(nullable=true)
	private int diffFromPrevDayDeaths;
	
	
	
	
	
	
	   
	
	

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getLatestTotalDeaths() {
		return latestTotalDeaths;
	}

	public void setLatestTotalDeaths(int latestTotalDeaths) {
		this.latestTotalDeaths = latestTotalDeaths;
	}

	public String getTotalDeaths() {
		return totalDeaths;
	}

	public void setTotalDeaths(String totalDeaths) {
		this.totalDeaths = totalDeaths;
	}

	public int getDiffFromPrevDayDeaths() {
		return diffFromPrevDayDeaths;
	}

	public void setDiffFromPrevDayDeaths(int diffFromPrevDayDeaths) {
		this.diffFromPrevDayDeaths = diffFromPrevDayDeaths;
	}

	@Override
	public String toString() {
		return "LocationStatsDeaths [Id=" + Id + ", state=" + state + ", country=" + country + ", latestTotalDeaths="
				+ latestTotalDeaths + ", totalDeaths=" + totalDeaths + ", diffFromPrevDayDeaths="
				+ diffFromPrevDayDeaths + "]";
	}
	
	
	
	
	
}
	