package com.sa.sa.repository.search;

import com.sa.sa.domain.Um2oOwner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Um2oOwner} entity.
 */
public interface Um2oOwnerSearchRepository extends ElasticsearchRepository<Um2oOwner, Long> {
}
