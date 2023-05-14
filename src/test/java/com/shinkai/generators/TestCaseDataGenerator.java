package com.shinkai.generators;

import com.github.javafaker.Faker;

import java.io.File;
import java.util.Objects;

public class TestCaseDataGenerator {
    ClassLoader classLoader = TestCaseDataGenerator.class.getClassLoader();
    static final Faker faker = new Faker();

    public File getFile(String fileName) {
        return new File(Objects.requireNonNull(classLoader.getResource("images/" + fileName)).getFile());
    }

    public String getTestName() {
        return faker.rockBand().name();
    }

    public String getTestDescription() {
        return faker.backToTheFuture().quote();
    }

    public String getTestStepName() {
        return faker.harryPotter().quote();
    }

    public String getRandomDigitWithLength(int length) {
        return faker.number().digits(length);
    }
}
