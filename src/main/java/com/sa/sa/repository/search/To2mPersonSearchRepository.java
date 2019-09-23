package com.sa.sa.repository.search;
import com.sa.sa.domain.To2mPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link To2mPerson} entity.
 */
public interface To2mPersonSearchRepository extends ElasticsearchRepository<To2mPerson, Long> {
}
