package org.hbs.gaya.bo;

import org.hbs.gaya.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialBo extends JpaRepository<User, Long>
{

}
