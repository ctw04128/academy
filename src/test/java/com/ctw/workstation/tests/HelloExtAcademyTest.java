package com.ctw.workstation.tests;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.arjuna.ats.jta.exceptions.NotImplementedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HelloExtAcademyTest {

    @Mock
    ExternalMessageService externalMessageService;

    @InjectMocks
    HelloExtAcademy helloExtAcademy;

    @Test
    @DisplayName("Hello from outer space with mock")
    void hello_from_outer_space_with_mock() {
        //given
        String name = "Rennan";

        //mock config
        Mockito.when(externalMessageService.sayHelloFromOuterSpace())
                .thenThrow(new NotImplementedException("This feature is not implemented"));

        //when
        String result = helloExtAcademy.sayHello(null);

        //then
        assertThat(result).isEqualTo("Hello Rennan from outer space");
    }

//    @Test
//    @DisplayName("Hello from outer space with spy")
//    void hello_from_outer_space_with_spy() {
//        //given
//        String name = "Rennan";
//        HelloExtAcademy hello = new HelloExtAcademy();
//        //spy config
//        ExternalMessageService svc = new ExternalMessageServiceImpl();
//        ExternalMessageService spy = Mockito.spy(svc);
//        hello.externalMessageService = spy;
//
//        Mockito.doReturn("Hello World").when(spy).sayHelloFromOuterSpace();
//
//
//        //when
//        String result = hello.sayHello(null);
//
//        //then
//        assertThat(result).isEqualTo("Hello World");
//        Mockito.verify(spy, Mockito.times(1)).sayHelloFromOuterSpace();
//    }
}