package com.kevinlorenzo.api.users.repository;

import org.springframework.data.repository.CrudRepository;

import com.kevinlorenzo.api.users.entity.UserEntity;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);

}
