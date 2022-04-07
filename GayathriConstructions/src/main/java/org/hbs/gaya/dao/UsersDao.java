package org.hbs.gaya.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hbs.gaya.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UsersDao extends JpaRepository<Users, Long>
{
	@Query("select u FROM Users u")
	List<Users> findByEmail(String username);

	@Query("select u FROM Users u where u.mobileNo = :mobileNo")
	List<Users> getLoginDetails(@Param("mobileNo") String mobileNo);

}
