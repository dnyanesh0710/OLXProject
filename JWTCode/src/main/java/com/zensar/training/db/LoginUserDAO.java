package com.zensar.training.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zensar.training.bean.LoginUser;
import com.zensar.training.bean.Person;

@Repository
public interface LoginUserDAO extends JpaRepository<LoginUser, Integer>{
	LoginUser findByName(String name);
}
