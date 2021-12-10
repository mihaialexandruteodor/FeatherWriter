package com.mihaialexandruteodor.FeatherWriter.repository;

import com.mihaialexandruteodor.FeatherWriter.model.FWCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FWCharacterRepository extends JpaRepository<FWCharacter, Integer> {
}
