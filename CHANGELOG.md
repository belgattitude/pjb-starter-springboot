# Change Log

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/).

## 1.2.0 TBD

### Updated

  - Gradle wrapper updated to 3.5.0

## 1.1.0 (2017-03-08) 

### Changed

  - Removed exclusions of jackson xml and groovy (build size increase)

### Added 

  - Spring boot actuator (useful functions for monitoring on administrative port)
  
### Updated

  - Spring boot 1.5.2
  - Provided gradle version updated to 3.4.1
  - Updated PHPJavabridge server to 7.0.0

### Removed

  - Java.inc deprecated client (committed by mistake)

## 1.0.0 (2017-02-18) 

### Changed

  - Updated deps to `io.soluble.pjb:php-java-bridge:6.2.11`
  - Increment to v1.0.0

## 0.4.0 (2017-02-15) 

### Changed

  - Namespace changed to `io.soluble.pssb` (pssb = pjb starter spring boot)
  - Updated to `io.soluble.pjb:php-java-bridge:6.2.11-rc-2`

### Added

  - Documentation examples for adding java libs from gradle `init-script` method.


# Releases from older pjb-starter-gradle (older name)

## 0.3.0 (2017-02-09) 

### Changed

  - Now use the php-java-bridge dependency from maven central instead
    of downloading from github. 
    
    ```
    // PHPJavaBridge
    compile("io.soluble.pjb:php-java-bridge:6.2.11-rc-1") {
        // In sprint-boot, you can exclude javax.servlet and log4j
        exclude group: 'javax.servlet', module: 'javax.servlet-api'
    }
    ```
    
    This means also that the 'php.java' namespace is replaced by 'io.soluble.pjb'.

  - Updated to spring-boot 1.5.1 release

## 0.2.0 (2017-01-20) 

### Changed

  - Application.java now lives in package io.soluble.pjb-starter-gradle.

### Added

  - Bridge landing page
  - Ping monitoring address
  - More doc madness

### Removed

  - Dropped PHPFastCGI servlet by default (doc exists)

  

## 0.1.0 (2017-01-19) Initial implementation

First implementation

  - Spring-boot with embedded tomcat 8 (self-contained, deployable to tomcat)
  - JavaBridgeServlet and PhpCGIServlet registered
  - Gradle support
  - Basic documentation
  



