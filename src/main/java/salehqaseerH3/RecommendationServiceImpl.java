package salehqaseerH3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class RecommendationServiceImpl extends HystrixCommand<List<String>> implements RecommendationService {

	private UserService userService;

	public RecommendationServiceImpl(UserService userService){
		super(HystrixCommandGroupKey.Factory.asKey("RecommendationService"));
		HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(100); // will execute fallback if the UserService doesn't respond in 100 Milliseconds
		this.userService = userService;
	}

	@Override
    protected List<String> run() {
        return getRecommendations(); // will call getRecommendations if everything is working as expected such that UserService is available and is responding in less than 100 milliseconds 
    }

	@Override
    protected List<String> getFallback() {
        return new ArrayList(Arrays.asList("Shrek","Coco","The Incredibles")) ; // will be executed if the UserService isn't available or takes forever to respond
    }

	@Override
	public List<String> getRecommendations() { // returning the List of movies based on the age returned from the UserService
		// TODO Auto-generated method stub
		if(userService.getAge() < 13)
			return new ArrayList(Arrays.asList("Shrek","Coco","The Incredibles")) ;
		else if(userService.getAge()>=13 && userService.getAge() < 17)
			return new ArrayList(Arrays.asList("The Avengers","The Dark Knight","Inception")) ;
		else
			return new ArrayList(Arrays.asList("The Godfather","Deadpool","Saving Private Rayn")) ;
	}

}
