package com.food.cassandra.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.food.cassandra.entity.HistoricalData;

@Repository
public interface HistoricalDataRepository extends CassandraRepository<HistoricalData, String> {

}
