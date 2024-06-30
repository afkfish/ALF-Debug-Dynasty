package hu.aut.bme.springbootalf

import hu.aut.bme.springbootalf.components.TestingComponent
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * Main entry point of the Spring Boot application.
 */
@SpringBootApplication
@EnableScheduling
class SpringBootAlfApplication

/**
 * Function to start the Spring Boot application.
 * @param args Command-line arguments.
 */
fun main(args: Array<String>) {
    val context: ConfigurableApplicationContext = runApplication<SpringBootAlfApplication>(*args)
    val testingComponent = context.getBean(TestingComponent::class.java)
    testingComponent.runTests()
}
