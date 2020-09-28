package io.javabrains.coronavirustracker;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoronavirusRecoveredDTO extends CrudRepository<LocationStatsRecovered, String>{


	List<LocationStatsRecovered> findByCountryLike(String country);

	LocationStatsRecovered findByCountry(String country);

	
	 @Query("FROM LocationStatsRecovered ORDER BY length(totalRecovered) DESC, totalRecovered DESC")
	List<LocationStatsRecovered> findAllOrderByTotalRecoveredDesc();
	 

	 @Query("FROM LocationStatsRecovered ORDER BY length(totalRecovered) ASC, totalRecovered ASC")
	    List<LocationStatsRecovered> findAllOrderByTotalRecoveredAsc();

}
