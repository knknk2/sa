package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link To2mPersonSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class To2mPersonSearchRepositoryMockConfiguration {

    @MockBean
    private To2mPersonSearchRepository mockTo2mPersonSearchRepository;

}
