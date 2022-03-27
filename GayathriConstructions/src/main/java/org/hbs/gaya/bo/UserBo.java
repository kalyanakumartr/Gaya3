package org.hbs.gaya.bo;

import org.hbs.gaya.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBo extends JpaRepository<Users, Long>
{

}
