package com.sa.sa.repository.search;

import com.sa.sa.domain.O2oDriver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link O2oDriver} entity.
 */
public interface O2oDriverSearchRepository extends ElasticsearchRepository<O2oDriver, Long> {
}
