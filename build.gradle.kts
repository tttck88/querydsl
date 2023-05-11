plugins {
    java
    id("org.springframework.boot") version "2.7.11"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    //querydsl 추가
    id ("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
    id ("java")
}

val queryDslVersion = "5.0.0"

group = "study"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    //querydsl 추가
    implementation ("com.querydsl:querydsl-jpa:${queryDslVersion}")
    annotationProcessor ("com.querydsl:querydsl-apt:${queryDslVersion}")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//querydsl 추가 시작
val querydslDir = "$buildDir/generated/querydsl"

querydsl {
    jpa = true
    querydslSourcesDir = querydslDir
}
sourceSets.getByName("main") {
    java.srcDir(querydslDir)
}
configurations {
    named("querydsl") {
        extendsFrom(configurations.compileClasspath.get())
    }
}
tasks.withType<com.ewerk.gradle.plugins.tasks.QuerydslCompile> {
    options.annotationProcessorPath = configurations.querydsl.get()
}
//querydsl 추가 끝
