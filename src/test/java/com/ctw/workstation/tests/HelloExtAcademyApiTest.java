package com.ctw.workstation.tests;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.ctw.workstation.config.CtwAcademyTestProfile;
import com.ctw.workstation.config.WiremockResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@QuarkusTest
@TestProfile(CtwAcademyTestProfile.class)
@QuarkusTestResource(WiremockResource.class)
public class HelloExtAcademyApiTest {

    @Inject
    HelloExtAcademy helloExtAcademy;


    @Test
    @DisplayName("Saying hello to external api")
    void saying_hello_to_external_api() {
        // given
        String name = "Rennan";
        stubFor(get("/external/hello").willReturn(ok("Hello " + name)));
        // when
        String result = helloExtAcademy.sayHello(name);
        // then
        assertThat(result)
                .isEqualTo("Hello Rennan!");
    }
}
