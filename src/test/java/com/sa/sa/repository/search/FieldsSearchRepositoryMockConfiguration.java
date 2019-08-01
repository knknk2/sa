package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link FieldsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FieldsSearchRepositoryMockConfiguration {

    @MockBean
    private FieldsSearchRepository mockFieldsSearchRepository;

}
