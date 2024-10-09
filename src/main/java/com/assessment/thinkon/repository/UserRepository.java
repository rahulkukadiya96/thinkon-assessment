package com.assessment.thinkon.repository;

import com.assessment.thinkon.model.User;
import com.assessment.thinkon.projection.UserProjection;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * This is Repository for the User table
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByUserNameAndDeleteFlag(String userName, int deleteFlag);
    boolean existsByUserNameAndIdNotAndDeleteFlag(String userName, long id, int deleteFlag);


    List<UserProjection> findByDeleteFlag(int deleteFlag);

    <T> Optional<T> findByIdAndDeleteFlag(long id, int deleteFlag, Class<T> type);
}
