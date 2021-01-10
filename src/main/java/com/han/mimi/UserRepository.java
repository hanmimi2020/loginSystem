package com.han.mimi;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByLoginId(String loginId);
}