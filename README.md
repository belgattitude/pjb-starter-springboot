[![Build Status](https://travis-ci.org/belgattitude/pjb-starter-springboot.svg?branch=master)](https://travis-ci.org/belgattitude/pjb-starter-springboot)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)


Skeleton to set up and customize a [PHPJavaBridge server](https://github.com/belgattitude/php-java-bridge)
based on [Spring boot](https://projects.spring.io/spring-boot/). 

See also [soluble-japha](https://github.com/belgattitude/soluble-japha) for PHP client. 

## Requirements

- Java JDK 8, see [docs/install_java.md](./docs/install_java.md)
- Optionally [Tomcat](./docs/install_tomcat.md) 

## Features

Based on [Spring boot](https://projects.spring.io/spring-boot/), the `pjb-starter-springboot` can
be easily configured or customized to build, run and deploy PHPJavabridge instances. It can also
be used as an example for embedding the PHPJavaBridge in your application.  

- [x] Modernized `JavaBridgeTemplate.jar` example
  - [x] JDK8, spring boot, thymeleaf.
  - [x] JavaBridgeServlet registered `PHP->Java`- (from php use the [soluble-japha](https://github.com/belgattitude/soluble-japha) client.
  - [x] PhpCGIServlet registration example `Java->PHP` (disabled by default, doc exists)        
  - [ ] Original PhpJavaBridge config properties in the original web.xml (like location of the php-cgi executable...)        
- [x] Service landing page 
  - [x] Refactored welcome landing page.
  - [x] *Ping* address for monitoring.
  - [ ] Display server/java infos
- [ ] Documentation 
  - [x] How to customize (WIP)
  - [ ] Basic security recipes, incl. bind on localhost
  - [ ] Document how to add local jar deps (out of maven)
   
## 1. Usage

Clone the project 
 
```shell
$ git clone https://github.com/belgattitude/pjb-starter-springboot/ 
```

> If you intend customize or contribute, make your [own fork](https://guides.github.com/activities/forking/) first and
> clone it instead of the main project. 

> If you want to skip the build process refer to the [release page](https://github.com/belgattitude/pjb-starter-springboot/releases) for pre-built binaries.


### 1.1 Build

Call the `build` gradle task: 

```shell 
$ ./gradlew build 
```

And check the `build\libs` directory for the following files:

| File          | Description   | Approx. size |
| ------------- | ------------- | ------------ |
| `JavaBridgeStandalone.jar`  | Standalone server with an embedded tomcat. | +/- 32Mb |
| `JavaBridgeTemplate.war`    | War file, ready to drop into Tomcat webapps folder. | +/- 12Mb |

> Approx. size is given as a reference of the current default build. 

### 1.2 Run from gradle (development)

```shell
$ ./gradlew bootRun 
# or specify the port with gradle bootRun -Dserver.port=8090
```

And point your browser to [http://localhost:8090](http://localhost:8090), you should see

![](./docs/images/browser-home.png "Homepage screenshot")

## 2. How to connect from PHP

Create a php project in a directory of your choice and install the [soluble-japha](https://github.com/belgattitude/soluble-japha) client.

```shell
$ composer require soluble/japha
```

Set the connection to the running phpjavabridge server (port 8090 by default)

```php
<?php

require 'vendor/autoload.php'; // for composer autoload

use Soluble\Japha\Bridge\Adapter as BridgeAdapter;

$ba = new BridgeAdapter([
    'driver' => 'Pjb62', 
    'servlet_address' => 'localhost:8090/servlet.phpjavabridge'
]);

$system = $ba->javaClass('java.lang.System');
echo  $system->getProperties()->get('java.vm_name');

```

## 3. How to customize

Have a look a those files:

| File  | Desc |
| ------------- | ------------- |
| [build.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/build.gradle)  |  Customize your dependencies here (jasper...), version,... |
| [main/resources/application.yml](https://github.com/belgattitude/pjb-starter-springboot/blob/master/src/main/resources/application.yml)  | Various [spring-boot application settings](https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html)  |
| [main/java/io/soluble/pssb/Application.java](https://github.com/belgattitude/pjb-starter-springboot/blob/master/src/main/java/io/soluble/pssb/Application.java)  | Where servlets, filters... are registered. |
| [settings.gradle](https://github.com/belgattitude/pjb-starter-springboot/blob/master/settings.gradle) | Customize the root project name |

Modify for your usage and rebuild with `grade bootRun` to check.

*Note that the welcome page is handled by the [main/java/io/soluble/pssb/mvc/IndexController.java](https://github.com/belgattitude/pjb-starter-springboot/blob/master/src/main/java/io/soluble/pssb/mvc/IndexController.java) with 
dependencies in the 'templates' and 'static' subdir in [resources](https://github.com/belgattitude/pjb-starter-springboot/tree/master/src/main/resources) folder.*  


## 4. How to distribute, run or deploy

> **WARNING** The phpjavabridge server is not supposed to be run on a public facing server
> and its use should be limited to interactions on the same host/network with the php client.
> Do not run it as root neither as it exposes the JVM methods through the network. 
> In its default configuration the pjb⁻starter example does not provide any security mechanisms
> so any url with an extension of '*.phpjavabridge' could be remotely exploited.      

### 4.1 Standalone (embedded tomcat8)

To run simply

```shell
$ java -jar ./build/libs/JavaBridgeStandalone.jar -Dserver_port=8090 
```
And point your browser to [http://localhost:8090](http://localhost:8090) to check.


### 4.2 Deploy on Tomcat 7/8

Copy to the Tomcat webapp directory to deploy: 

```shell
cp ./build/libs/JavaBridgeTemplate.war /var/lib/tomcat8/webapps/MyJavaBridge.war
```

wait few seconds and point your browser to [http://localhost:8080/MyJavaBridge](http://localhost:8080/MyJavaBridge).

> Note that the port may vary and the URI *(or server context)* is taken from the deplyed filename. 
> In this example: 'MyJavaBridge', feel free to change.  

Repeat operation whenever you need to (re-)deploy.

## 5. Faq

### How to add libraries to the builded war (jar) ?

> You can either add your dependencies directly in the `build.gradle` file 
> or use the [init-script method](https://github.com/belgattitude/pjb-starter-springboot/tree/master/init-scripts).
> The latter is preferred if you prefer to keep the `build.gradle` file intact.
 

1. Alternative 1: Use the init-script method.

    Have a look to the [init-script method](https://github.com/belgattitude/pjb-starter-springboot/tree/master/init-scripts) documentation. 

2. Alternative 2: Modify the `build.gradle` file:
    Just open the [build.gradle](./build.gradle) and
    check the runtime (or compile) sections.
    
    ```gradle
    dependencies {
        // Spring-boot stuffs
        providedRuntime "org.springframework.boot:spring-boot-starter-tomcat:${spring_boot_version}"
        testCompile "org.springframework.boot:spring-boot-starter-test:${spring_boot_version}"
        compile "org.springframework.boot:spring-boot-starter-web:${spring_boot_version}"
        compile ("org.springframework.boot:spring-boot-starter-thymeleaf:${spring_boot_version}") {
            exclude group: 'org.codehaus.groovy', module: 'groovy'
        }
        compile "org.springframework.boot:spring-boot-devtools:${spring_boot_version}"  // Useful for dev (auto disabled on prod)
        compile "com.google.code.gson:gson:2.8.0"  // Deps for the ping address
    
        // PHPJavaBridge
        compile("io.soluble.pjb:php-java-bridge:${pjb_version}") {
            // In sprint-boot, you can exclude javax.servlet and log4j
            exclude group: 'javax.servlet', module: 'javax.servlet-api'
        }
    
        /** #######################################################
         *  Here you can add runtime dependencies to the project
         *  -------------------------------------------------------
         *  Those dependencies will be included available in the
         *  builded war file. Alternatively you may register deps
         *  through the 'init-script' method.
         *  (check 'init-scripts/README.md' folder for insights)
         *  #######################################################
         */
    
        // example: mysql jdbc connector
        /*runtime "mysql:mysql-connector-java:6.+"*/
    
        // example: jasper reports
        /*
        runtime ('net.sf.jasperreports:jasperreports:6.+') {
            exclude group: 'com.lowagie' // better to use latest com.itextpdf instead
            exclude group: 'org.olap4j' // Generally don't need this
        }
        */
        
        // example: itextpdf (required for jasperreports)
        /* runtime 'com.itextpdf:itextpdf:5.5.+' */
        /* runtime 'com.itextpdf:itext-pdfa:5.5.+' */
    }
    ```
    
    Have a look to [maven repo](https://mvnrepository.com/) to quickly find your deps
    
    > For PHP users, gradle might not sound familiar. In the context of adding libraries, you
    > can consider it as a package manager like composer, npm... To add or define dependencies  
    > simply edit the dependencies section of the [build.gradle](./build.gradle) file. 
    > Generally in the 'runtime' subsection.   
 

### How to start at boot ?

If you use the Tomcat deployment it should start by default. With the self-container tomcat, ask google for "supervisord spring boot".

### OutOfMemory errors ?

With the self-contained tomcat

```shell
> export JAVA_OPTS=-Xmx512m -XX:MaxPermSize=128M
```
If deployed on Tomcat

```shell
$ vi /etc/default/tomcat8
```

Look for the Xmx default at 128m and increase 

```
JAVA_OPTS="-Djava.awt.headless=true -Xmx512m -XX:+UseConcMarkSweepGC"
```

and restart

```shell
sudo service tomcat8 restart
```

### How can I monitor that the bridge runs ?

The `pjb-starter-springboot` includes a very basic [PingController]((https://github.com/belgattitude/pjb-starter-springboot/blob/master/src/main/java/io/soluble/pssb/mvc/PingController.java)), you can call it on [http://localhost:8090/ping.json](http://localhost:8090/ping.json), you should see

```json
{
    "date": 1484919571831,
    "success": true,
    "message": "PHPJavaBridge running"
}
```


### How to enable the `Java->PHP` feature ? 

By default this template does not enable `Java->PHP` by default. So if you intend to
use PHP from Java, you must add to the [Application.java](https://github.com/belgattitude/pjb-starter-springboot/blob/master/src/main/java/io/soluble/pssb/Application.java) the following methods:
  
  
```java


public class Application extends SpringBootServletInitializer {


    /// Normal initialization sequence

    /**
     * Register the php.java.servlet.fastcgi.FastCGIServlet
     * <p>
     * It replaces the following section found in the original web.xml config:
     * <p>
     * <pre>
     * {@code
     * <servlet>
     *     <servlet-name>PhpCGIServlet</servlet-name>
     *     <servlet-class>php.java.servlet.fastcgi.FastCGIServlet</servlet-class>
     *     <load-on-startup>0</load-on-startup>
     * </servlet>
     * <servlet-mapping>
     *     <servlet-name>PhpCGIServlet</servlet-name>
     *     <url-pattern>*.php</url-pattern>
     * </servlet-mapping>
     * }
     * </pre>
     */
    @Bean
    public ServletRegistrationBean phpCGIServletDispatcherRegistration() {

        FastCGIServlet phpJavaServlet = new io.soluble.pjb.servlet.fastcgi.FastCGIServlet();
        ServletRegistrationBean registration = new ServletRegistrationBean(
                phpJavaServlet);
        registration.addUrlMappings("*.php");
        registration.setLoadOnStartup(0);
        return registration;
    }

    /**
     * Register the php.java.servlet.PhpCGIFilter
     * <p>
     * This filter extend the servlet spec 2.2 "url-pattern"
     * to handle PHP PATH_INFO: *.php/something?what=that.
     * Remove it, if you don't need this feature.
     * <p>
     * <pre>
     * {@code
     * <filter>
     *     <filter-name>PhpCGIFilter</filter-name>
     *     <filter-class>php.java.servlet.PhpCGIFilter</filter-class>
     * </filter>
     * <filter-mapping>
     *     <filter-name>PhpCGIFilter</filter-name>
     *     <url-pattern>/*</url-pattern>
     * </filter-mapping>
     * }
     * </pre>
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new io.soluble.pjb.servlet.PhpCGIFilter());
        registration.addUrlPatterns("/*");
        //registration.addInitParameter("paramName", "paramValue");
        registration.setName("PhpCGIFilter");
        registration.setOrder(1);
        return registration;
    }

}
  
```  

And ensure you have the [php-cgi](./docs/install_php-cgi.md) installed and available on your system. 

An example page [main/webapp/index.php](https://github.com/belgattitude/pjb-starter-springboot/blob/master/src/main/webapp/index.php) page is already provided as a starting point.... 
Try [http://localhost:8090/index.php](http://localhost:8090/index.php)


### How to enable debugging ?

Debugging can be enabled with the standalone server:

```shell
$ java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8000,suspend=n \
       -jar ./build/libs/JavaBridgeStandalone.jar
```



## Contribute

Feel free to fork and submit pull requests.



## Credits

* Initially made with love by [Sébastien Vanvelthem](https://github.com/belgattitude).
* [Contribute and appears here] 

