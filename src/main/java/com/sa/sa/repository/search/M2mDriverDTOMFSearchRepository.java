package com.sa.sa.repository.search;
import com.sa.sa.domain.M2mDriverDTOMF;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link M2mDriverDTOMF} entity.
 */
public interface M2mDriverDTOMFSearchRepository extends ElasticsearchRepository<M2mDriverDTOMF, Long> {
}
