package com.chi.makers.api.admin.repo;

import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.chi.makers.api.domain.Option;

public interface AdminMkOptionRepository extends CrudRepository<Option, String>{

}
