package salehqaseerH3;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.AdditionalAnswers.answersWithDelay;


public class ServicesTest {

    private static final List<String> under13Movies = new ArrayList(Arrays.asList("Shrek","Coco","The Incredibles")); // Movies for under 13
    private static final List<String> under17Movies = new ArrayList(Arrays.asList("The Avengers","The Dark Knight","Inception")); // Movies for Under 17
    private static final List<String> above17Movies = new ArrayList(Arrays.asList("The Godfather","Deadpool","Saving Private Rayn")); // Movies for above 17

    // Required for initializing the mocking and annotations 
    @BeforeEach
    void init_mocks() {
		MockitoAnnotations.initMocks(this);
	}


    UserService service = mock(UserService.class); // Mocking the User Service
    @InjectMocks RecommendationServiceImpl recommendationService; // Injecting the UserService into the RecommendationService


    @Test
    public void boundary13Test(){
        when(service.getAge()).thenReturn(13); // returning 13 when .getAge() is invoked 
        assertEquals(under17Movies,recommendationService.getRecommendations()); // getting recommendations should return under17Movies
    }

    @Test
    public void under13Test() {
        when(service.getAge()).thenReturn(11); // returning 11 when .getAge() is invoked 
        assertEquals(under13Movies,recommendationService.getRecommendations()); // getting recommendations should return under13Movies
    }


    @Test
    public void under17Test() {
        when(service.getAge()).thenReturn(15); // returning 15 when .getAge() is invoked 
        assertEquals(under17Movies,recommendationService.getRecommendations()); // getting recommendations should return under17Movies  
    }

    @Test
    public void boundaryAbove17Test(){
        when(service.getAge()).thenReturn(17); // returing 17 when .getAge() is invoked 
        assertEquals(above17Movies,recommendationService.getRecommendations()); // getting recommendations should return above17Movies
    }

    @Test
    public void above17Test(){
        when(service.getAge()).thenReturn(20); // returning 20 when .getAge() is triggered 
        assertEquals(above17Movies,recommendationService.getRecommendations()); // getting recommendations should return above17Movies
    }

    @Test
    public void userserviceUnavailable(){
        when(service.getAge()).thenThrow(NullPointerException.class); // will throw a NullPointerException when .getAge() is invoked  
        assertEquals(under13Movies,recommendationService.execute()); // executing the RecommendationService should trigger the fallback and return under13Movies since the UserService isn't available
    }

    @Test 
    public void userserviceNotResponding() {
        Answer<Integer> answer = new Answer<Integer>() {
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Thread.sleep(500);
                return 25;
            }
        }; // Mocking the getAge as an Answer method that returns 25 after 500 milliseconds
        when(service.getAge()).thenAnswer(answer); // will execute answer when .getAge() is invoked  
        assertEquals(under13Movies,recommendationService.execute()); // executing the RecommendationService should trigger the fallback and return under13Movies since the UserService is taking longer than expected to respond
    }

    @Test 
    public void userserviceNotResponding2() {
        Answer<Integer> answer = new Answer<Integer>() {
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Thread.sleep(500);
                return 15;
            }
        }; // Mocking the getAge as an Answer method that returns 15 after 500 milliseconds, just to make sure that the returing result won't matter
        when(service.getAge()).thenAnswer(answer); // will execute answer when .getAge() is invoked  
        assertEquals(under13Movies,recommendationService.execute()); // executing the RecommendationService should trigger the fallback and return under13Movies since the UserService is taking longer than expected to respond
    }
	
}
