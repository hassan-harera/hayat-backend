package com.harera.hayat.shared;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import com.harera.hayat.shared.security.WebSecurityConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@Import(WebSecurityConfig.class)
@SpringBootTest(webEnvironment = DEFINED_PORT)
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public abstract class ApplicationIT {

}
