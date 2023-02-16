package com.harera.hayat.authorization;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@RunWith(SpringRunner.class)
public abstract class ApplicationIT {

}
