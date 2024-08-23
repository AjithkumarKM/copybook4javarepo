# Releasing a new version

1. Update version in the following files
* pom.xml
* Depending projects pom.xml
* README.md

```bash
git commit -m 'Releasing <project>@<version>' fx copybook4java-codegen-maven-plugin@v1.0.5
git push
git tag <project>@<version> # fx copybook4java-codegen-maven-plugin@v1.0.5
git push origin <project>@<version> # fx copybook4java-codegen-maven-plugin@v1.0.5
mvn clean deploy -P release --settings settings.xml
```

2. Build and deploy release

```bash
cd <project to be released>
mvn clean deploy -P release
```
3. https://oss.sonatype.org/#welcome

# Adding new developer

1. Generate key pair and export pubkey
```bash
gpg --gen-key # Create key with your company e-mail
gpg --armor --export user@company.com
```

2. Register key pair with https://pgp.mit.edu/

3. Add yourself to the pom.xml files in the developer section

4. Add login for osshr to .m2/settings.xml

```xml
<servers>
    <server>
        <id>ossrh</id>
        <username>NordeaOSSBuild</username>
        <password>{the password}</password>
    </server>
</servers>
```