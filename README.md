Template-generator-maven-plugin
===============================
[![The Apache License](https://img.shields.io/github/license/danidemi/template-generator-maven-plugin.svg?label=License)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/com.danidemi/template-generator-maven-plugin.svg?label=Maven%20Central)](http://search.maven.org/#search%7Cga%7C1%7Ctemplate-generator-maven-plugin)
[![Build Status](https://api.travis-ci.org/danidemi/template-generator-maven-plugin.svg?branch=master)](https://travis-ci.org/danidemi/template-generator-maven-plugin)


A Maven plugin that generates code using template engines.

## References

* Maven Velocity Author Guide <https://velocity.apache.org/engine/devel/user-guide.html> 
* Maven Velocity Dev Guide <https://velocity.apache.org/engine/devel/developer-guide.html#the-fundamental-pattern>

## Contributing

Git flow with following settings:
* Branch name for production releases: `master`
* Branch name for "next release" development: `develop`
* Feature branches? `feature/`
* Bugfix branches? `bugfix/`
* Release branches? `release/`
* Hotfix branches? `hotfix/`
* Support branches? `support/`
* Version tag prefix? `[]`


## Use

### Stable versions

    <plugin>
        <groupId>com.danidemi</groupId>
        <artifactId>template-generator-maven-plugin</artifactId>
        <version>0.0.5</version>
    </plugin>
    
### Snapshot versions
Available here: <https://oss.sonatype.org/content/repositories/snapshots/com/danidemi/template-generator-maven-plugin/>,
to use in a project, please enable this

    <repositories>
        <repository>
            <id>central</id>
            <name>Sonatype Snapshot</name>
            <layout>default</layout>
            <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
    

## Dev Notes

    mvn com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate
    mvn install com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate -e -X -Dgenerate.pathToCsv=Hollow
    mvn install com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate -e -X -Dgenerate.pathToCsv=${project.build.testSourceDirectory}
    mvn install com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate -e -X -Dgenerate.pathToCsv=C:\Users\danidemi\workspace\repos\danidemi\template-generator-maven-plugin\src\test\resources\codeAndCountry.csv
    mvn install com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate -e -X -Dgenerate.pathToCsv=${project.build.testSourceDirectory}\..\resources\codeAndCountry.csv
    mvn install com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate -e -X -Dgenerate.pathToCsv=${project.build.testSourceDirectory}\..\resources\codeAndCountry.csv -Dgenerate.pathToTemplate=${project.build.sourceDirectory}\..\resources\Money.java.vm -Dgenerate.pathToOutputFolder=${project.build.directory}\generated


## Release

### Stable versions

    mvn versions:set
    mvn clean install
    mvn clean deploy -P release
    mvn versions:set

### Snapshot versions

    mvn clean deploy