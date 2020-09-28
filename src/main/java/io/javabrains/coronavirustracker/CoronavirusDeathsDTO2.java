package io.javabrains.coronavirustracker;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoronavirusDeathsDTO2 extends CrudRepository<LocationStatsDeaths, String>{


	List<LocationStatsDeaths> findByCountryLike(String country);

	LocationStatsDeaths findByCountry(String country);
	
	
	 @Query("FROM LocationStatsDeaths ORDER BY length(totalDeaths) DESC, totalDeaths DESC")
	    List<LocationStatsDeaths> findAllOrderByTotalDeathsDesc();

	 

	 @Query("FROM LocationStatsDeaths ORDER BY length(totalDeaths) ASC, totalDeaths ASC")
	    List<LocationStatsDeaths> findAllOrderByTotalDeathsAsc();

}
