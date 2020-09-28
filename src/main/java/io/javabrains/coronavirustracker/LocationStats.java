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
@Table(name = "Coviddata")
public class LocationStats {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	@Column(nullable=true)
    private String state;
	
	@Column(nullable=true)
    private String country;
	
	@Column(nullable=true)
    private int latestTotalCases;
	
	@Column(nullable=true)
    private int diffFromPrevDay;
	
	@Column(nullable=true)
    private String todaysCases = Integer.toString(latestTotalCases);
    
    
	
	
	
	public LocationStats(){}
	
	
    public LocationStats(String country, String todaysCases) {
   	
        this.country = country;
        this.todaysCases = todaysCases;
	}

	public String getTodaysCases() {
		return todaysCases;
	}

	public void setTodaysCases(String todaysCases) {
		this.todaysCases = todaysCases;
	}

	public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
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

    public int getLatestTotalCases() {
        return latestTotalCases;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        this.latestTotalCases = latestTotalCases;
    }

	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", latestTotalCases=" + latestTotalCases
				+ ", diffFromPrevDay=" + diffFromPrevDay + ", todaysCases=" + todaysCases + "]";
	}

	public String ToString() {
		// TODO Auto-generated method stub
		 return this.country+" "+this.todaysCases;
		
	}





	

    

}


