package jack.movieCatalog.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import jack.movieCatalog.model.Rating;
import jack.movieCatalog.model.UserRating;

@Service
public class RatingInfoService {

	@Autowired
	RestTemplate rt ;
	

	@HystrixCommand(fallbackMethod = "getFallbackUserRating")
	public UserRating getUserRating(String userId) {

		return rt.getForObject("http://MOVIERATING/rating/user/"+ userId, UserRating.class);
		
	}

	public UserRating getFallbackUserRating(String userId) {
		List<Rating> ratingList = Arrays.asList( new Rating("0", 0)) ;
		UserRating ur = new UserRating( ratingList ) ;
		return ur;
	}
}
