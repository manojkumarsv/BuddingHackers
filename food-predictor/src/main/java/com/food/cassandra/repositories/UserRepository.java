package com.food.cassandra.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.food.cassandra.entity.User;

@Repository
public interface UserRepository extends CassandraRepository<User, String> {

}
