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
@Table(name = "Coviddatarecovered")
public class LocationStatsRecovered {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(nullable=true)
    private String state;
	
	@Column(nullable=true)
    private String country;
	
	@Column(nullable=true)
	private int latestTotalRecovered;
	
	@Column(nullable=true)
	private String totalRecovered = Integer.toString(latestTotalRecovered);
	
	@Column(nullable=true)
	private int diffFromPrevDayRecovered;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

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

	public int getLatestTotalRecovered() {
		return latestTotalRecovered;
	}

	public void setLatestTotalRecovered(int latestTotalRecovered) {
		this.latestTotalRecovered = latestTotalRecovered;
	}

	public String getTotalRecovered() {
		return totalRecovered;
	}

	public void setTotalRecovered(String totalRecovered) {
		this.totalRecovered = totalRecovered;
	}

	public int getDiffFromPrevDayRecovered() {
		return diffFromPrevDayRecovered;
	}

	public void setDiffFromPrevDayRecovered(int diffFromPrevDayRecovered) {
		this.diffFromPrevDayRecovered = diffFromPrevDayRecovered;
	}

	@Override
	public String toString() {
		return "LocationStatsRecovered [Id=" + Id + ", state=" + state + ", country=" + country
				+ ", latestTotalRecovered=" + latestTotalRecovered + ", totalRecovered=" + totalRecovered
				+ ", diffFromPrevDayRecovered=" + diffFromPrevDayRecovered + "]";
	}

	
	
	
}