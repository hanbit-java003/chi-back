package com.chi.makers.api.repo;

import java.util.List;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.View;
import org.springframework.data.repository.CrudRepository;

import com.chi.makers.api.domain.Airline;

public interface AirlineRepository extends CrudRepository<Airline, String>{
	@Query("#{#n1ql.selectEntity} WHERE type = $1 AND #{#n1ql.filter}") // #n1ql.selectEntity 전체에서 같은 type 전체 보여줌
	List<Airline> findByType(String type); // @Query - 강제 쿼리 만들면 메소드명은 의미 없다.
	
	List<Airline> findByTypeOrderById(String type);
	
	List<Airline> findByTypeOrderByIdDesc(String type);
	
	@View(viewName="US")
	List<Airline> findUS(); // couchBase에 미리 만들어 놓은 쿼리 - View(US)를 사용한다.
}
