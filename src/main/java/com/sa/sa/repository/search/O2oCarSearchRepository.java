package com.sa.sa.repository.search;
import com.sa.sa.domain.O2oCar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link O2oCar} entity.
 */
public interface O2oCarSearchRepository extends ElasticsearchRepository<O2oCar, Long> {
}
