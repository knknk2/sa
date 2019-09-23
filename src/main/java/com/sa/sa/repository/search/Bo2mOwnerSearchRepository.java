package com.sa.sa.repository.search;
import com.sa.sa.domain.Bo2mOwner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Bo2mOwner} entity.
 */
public interface Bo2mOwnerSearchRepository extends ElasticsearchRepository<Bo2mOwner, Long> {
}
