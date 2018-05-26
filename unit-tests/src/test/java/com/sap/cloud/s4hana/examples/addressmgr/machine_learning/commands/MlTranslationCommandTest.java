package com.sap.cloud.s4hana.examples.addressmgr.machine_learning.commands;

import com.sap.cloud.sdk.odatav2.connectivity.ODataException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MlTranslationCommandTest {
    @Before
    public void before() {
    }

    @Test
    public void testCreateRequestJson() throws ODataException {
        final String requestJson = new MlTranslationCommand(null, null, null, null)
                .createRequestJson("en", "de", Collections.singletonList("test text"));

        assertThat(requestJson).isEqualTo("{\"sourceLanguage\":\"en\",\"targetLanguages\":[\"de\"]," +
                "\"units\":[{\"value\":\"test text\"}]}");
    }
}
