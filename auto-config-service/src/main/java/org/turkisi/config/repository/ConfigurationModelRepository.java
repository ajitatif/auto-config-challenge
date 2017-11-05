package org.turkisi.config.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.turkisi.config.domain.ConfigurationModel;

import java.util.List;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Repository
public interface ConfigurationModelRepository extends CrudRepository<ConfigurationModel, Long> {

    @Query("from ConfigurationModel config where applicationName = :appName and active = true")
    List<ConfigurationModel> findByApplicationName(@Param("appName") String application);
}
