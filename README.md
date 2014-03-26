voltdb-sample-product
=====================

This example application was built for VoltDB. 

Traditional RDBMS tend to provide all-in-one ( i.e. OLAP and OLTP ) solutions and
are not designed for large volume of data. They are very complex to scale and maintain. In an attempt to
solve, two different databases ( Vertica & VoltDB ) can be used for specific purposes.

This project serves purpose for OLTP Database.

To demonstrate the usage of voltdb following use case was considered:
An eCommerce website(well known and hence high volume) stores order placed and also, need to 
analyze user trends ( e.g; by geographic location ). This catalog can be used as OLTP Database.

Requirements:
- VoltDB (Platform Specific) 4.0.2.3 Community Edition Database
- JDK 1.7

Installation Steps:
- Extract VoltDB on the machine
- Add Extracted VoltDB directory to PATH environment Path Variable
- Set VoltDB directory as VOLTDB_HOME
- Create a new directory in ${VOLTDB_HOME} as "sample"
- Create sub directories - root, export, snapshots
- Copy 'Deployment.xml', 'sample.sql' files to newly
- Create a project using IntelliJ Idea or Eclipse
- Compile Source code using Java Files and VoltDB Java Archives ( From /bin & /lib Directories )
- Copy All *.class to ${VOLTDB_HOME}/sample/
- Generate Database by executing `voltdb compile --classpath="./" -o sample.jar sample.sql`
- Deploy Database by executing `voltdb create sample.jar`
- Use ${VOLTDB_HOME}/bin/**/sqlcmd or ${VOLTDB_HOME}/tool/**/index.html ( browser based query editor ) to check if database is accessible and to validate schema.

NOTES:
Currently Application only contains CRUD operations related code.
