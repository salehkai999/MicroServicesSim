package salehqaseerH3;

import java.util.Random;

// Since we are mocking mockito would assume we don't know the code so the following implementations doesn't show the actual code they're for domenstration purposes only
public class UserServiceImpl implements UserService {

	private int age;
	
	public UserServiceImpl(int age) {
		this.age = age;
	}
	@Override
	public int getAge() {
		// TODO Auto-generated method stub
		return age;
	}



}
