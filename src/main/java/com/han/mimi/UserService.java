package com.han.mimi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

	public void saveUser(User user, String loginId, String password) {
    	user.setLoginId(loginId);
        user.setPassword(password);
        userRepository.save(user);

        log.info(user.toString()+" saved to the repo");
	}


	public  List<User>  findUser(String loginId, String password) {
        List<User> users = userRepository.findByLoginId(loginId);
        return users;
	}
}
