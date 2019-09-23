package com.sa.sa.repository.search;
import com.sa.sa.domain.Fields;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Fields} entity.
 */
public interface FieldsSearchRepository extends ElasticsearchRepository<Fields, Long> {
}
