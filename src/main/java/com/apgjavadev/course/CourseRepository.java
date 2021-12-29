package com.apgjavadev.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
  @RestResource(rel = "title-contains", path = "containsTitle")
  Page<Course> findByTitleContainingIgnoreCase(@Param("title") String title, Pageable page);

  Optional<Course> findByUrl(String url);

//  @PreAuthorize("hasRole('ROLE_USER')")
//  @Override
//  <S extends Course> S save(S entity);
//
//  @PreAuthorize("hasRole('ROLE_USER')")
//  @Override
//  <S extends Course> Iterable<S> saveAll(Iterable<S> entities);
}
