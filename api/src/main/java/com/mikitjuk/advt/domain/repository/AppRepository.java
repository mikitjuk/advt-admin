package com.mikitjuk.advt.domain.repository;

import com.mikitjuk.advt.domain.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AppRepository extends JpaRepository<App, Integer> {
}
