# Liberty Administration PoT Source Code

1. Build with some version of WebSphere Liberty:
   ```
   mvn -Dliberty.runtime.version=22.0.0.8 -DskipTests=true clean install
   ```
1. The server package is generated in `liberty-server/target/pbwServerX_package*`
