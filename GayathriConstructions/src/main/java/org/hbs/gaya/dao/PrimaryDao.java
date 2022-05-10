package org.hbs.gaya.dao;

import org.hbs.gaya.model.Sequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryDao extends JpaRepository<Sequence, String>
{
	@Query("select S from Sequence S where S.sequenceKey = :sequenceKey")
	Sequence findBySequenceKey(@Param("sequenceKey") String sequencekey);

}
