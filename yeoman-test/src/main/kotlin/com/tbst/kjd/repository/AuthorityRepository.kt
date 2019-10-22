package com.tbst.kjd.repository

import com.tbst.kjd.domain.Authority

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Spring Data JPA repository for the [Authority] entity.
 */

interface AuthorityRepository : JpaRepository<Authority, String>
