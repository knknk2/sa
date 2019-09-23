package com.sa.sa.repository.search;
import com.sa.sa.domain.M2mCarDTOMF;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link M2mCarDTOMF} entity.
 */
public interface M2mCarDTOMFSearchRepository extends ElasticsearchRepository<M2mCarDTOMF, Long> {
}
