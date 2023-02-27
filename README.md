# Liberty Administration PoT Source Code

## First time

1. Install the DB2 license file locally, replacing `$DIR` with the local path to `db2jcc_license_cu.jar`:
   ```
   mvn install:install-file -Dfile=$DIR/db2jcc_license_cu.jar -DgroupId=com.ibm.db2 -DartifactId=db2jcc_license_cu -Dversion=1.0-SNAPSHOT -Dpackaging=jar -DgeneratePom=true
   ```

## Building Liberty Server Package

1. Build with some version of WebSphere Liberty:
   ```
   mvn -Dliberty.runtime.version=22.0.0.8 -DskipTests=true clean install
   ```
    1. You might need to override variable defaults; for example:
       ```
       mvn -Dliberty.runtime.version=22.0.0.8 -DskipTests=true -DNoTxPBWDataSource_rhel9_baseNode01_tWAS_85515_server_DataSource_1673564702594_serverName=server0.gym.lan -DNoTxPBWDataSource_rhel9_baseNode01_tWAS_85515_server_DataSource_1673564702594_serverName=server0.gym.lan clean install
       ```
1. The server package is generated in `liberty-server/target/pbwServerX_package*`
