dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    // https://resilience4j.readme.io/docs/getting-started-3
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.github.resilience4j:resilience4j-spring-boot3:2.2.0")
    implementation("org.springframework.boot:spring-boot-starter-aop")
}