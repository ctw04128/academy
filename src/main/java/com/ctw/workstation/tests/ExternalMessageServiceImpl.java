package com.ctw.workstation.tests;

public class ExternalMessageServiceImpl implements ExternalMessageService {
    @Override
    public String sayHelloFromOuterSpace() {
        return "Hello from outer space";
    }

    @Override
    public String sayHelloFromOuterSpace(String name) {
        return "Hello " + name + " from outer space";
    }
}
