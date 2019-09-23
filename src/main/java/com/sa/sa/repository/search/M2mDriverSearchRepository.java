package com.sa.sa.repository.search;
import com.sa.sa.domain.M2mDriver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link M2mDriver} entity.
 */
public interface M2mDriverSearchRepository extends ElasticsearchRepository<M2mDriver, Long> {
}
