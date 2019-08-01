package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link O2oDriverSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class O2oDriverSearchRepositoryMockConfiguration {

    @MockBean
    private O2oDriverSearchRepository mockO2oDriverSearchRepository;

}
