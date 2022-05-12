package com.mihaialexandruteodor.FeatherWriter.repository;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FWCharacterRepository extends JpaRepository<FWCharacter, Integer> {

    @Query(value = "SELECT * FROM fwcharacter WHERE username = ?1", nativeQuery = true)
    List<FWCharacter> getCharactersForCurrentUser(String username);

    @Query(value = "SELECT * FROM fwcharacter WHERE username = :username and name LIKE %:name%", nativeQuery = true)
    List<FWCharacter> searchCharacterByName(@Param("name") String name, @Param("username") String username);
}
