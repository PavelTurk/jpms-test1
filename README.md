# jpms-test1
Layer graph and ResolutionException

How to run

    1. git clone https://github.com/PavelTurk/jpms-test1.git
    2. Set MAVEN_REPO_PATH in class Project
    3. mvn clean install
    4. java --add-modules ALL-DEFAULT --module-path . -m com.foo.main/com.foo.main.Project

Output:

    Resolved path: /home/pavel/.m2/repository/com/google/code/gson/gson/2.8.6/gson-2.8.6.jar
    Created layer A: com.google.gson
    Resolved path: /home/pavel/.m2/repository/com/google/code/gson/gson/2.8.6/gson-2.8.6.jar
    Resolved path: /home/pavel/.m2/repository/com/google/guava/guava/29.0-jre/guava-29.0-jre.jar
    Exception in thread "main" java.lang.module.ResolutionException: Module com.google.common reads more than one module named com.google.gson
            at java.base/java.lang.module.Resolver.resolveFail(Resolver.java:900)
            at java.base/java.lang.module.Resolver.checkExportSuppliers(Resolver.java:721)
            at java.base/java.lang.module.Resolver.finish(Resolver.java:380)
            at java.base/java.lang.module.Configuration.<init>(Configuration.java:139)
            at java.base/java.lang.module.Configuration.resolveAndBind(Configuration.java:493)
            at com.foo.main@1.0.0/com.foo.main.Project.createLayer(Project.java:51)
            at com.foo.main@1.0.0/com.foo.main.Project.createLayerB(Project.java:38)
            at com.foo.main@1.0.0/com.foo.main.Project.main(Project.java:25)
