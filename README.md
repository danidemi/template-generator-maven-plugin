Template-generator-maven-plugin
===============================

A Maven plugin that generates code using template engines.

## References

* Maven Velocity Author Guide <https://velocity.apache.org/engine/devel/user-guide.html> 
* Maven Velocity Dev Guide <https://velocity.apache.org/engine/devel/developer-guide.html#the-fundamental-pattern>

## Use

    <plugin>
        <groupId>com.danidemi</groupId>
        <artifactId>template-generator-maven-plugin</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </plugin>
    

## Dev Notes

    mvn com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate
    mvn install com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate -e -X -Dgenerate.pathToCsv=Hollow
    mvn install com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate -e -X -Dgenerate.pathToCsv=${project.build.testSourceDirectory}
    mvn install com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate -e -X -Dgenerate.pathToCsv=C:\Users\danidemi\workspace\repos\danidemi\template-generator-maven-plugin\src\test\resources\codeAndCountry.csv
    mvn install com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate -e -X -Dgenerate.pathToCsv=${project.build.testSourceDirectory}\..\resources\codeAndCountry.csv
    mvn install com.danidemi:template-generator-maven-plugin:0.0.1-SNAPSHOT:generate -e -X -Dgenerate.pathToCsv=${project.build.testSourceDirectory}\..\resources\codeAndCountry.csv -Dgenerate.pathToTemplate=${project.build.sourceDirectory}\..\resources\Money.java.vm -Dgenerate.pathToOutputFolder=${project.build.directory}\generated
