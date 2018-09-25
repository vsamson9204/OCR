/**
 * 
 */
package com.earthteam.ocr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.earthteam.ocr.domain.Doctor;

/**
 * 
 * @author Vivian Samson - vsamson92044@gmail.com
 *
 *
 */
@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {

	@Query ("SELECT d FROM Doctor d WHERE doctorCategory.id = :categoryId")
	List<Doctor> findByCategoryId(@Param("categoryId") int categoryId);
	
	@Query ("SELECT d FROM Doctor d WHERE credentials.username = :username")
	Doctor findByUserName(@Param("username") String userName);
	
	
	
}
