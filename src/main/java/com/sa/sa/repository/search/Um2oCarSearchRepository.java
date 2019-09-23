package com.sa.sa.repository.search;
import com.sa.sa.domain.Um2oCar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Um2oCar} entity.
 */
public interface Um2oCarSearchRepository extends ElasticsearchRepository<Um2oCar, Long> {
}
