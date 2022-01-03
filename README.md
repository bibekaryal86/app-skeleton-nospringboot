# nospring-service-skeleton

* This is a template Repository to create a new Java web server without spring framework
* Things to update:
  * Refactor the package name from `nospring.service.skeleton.app` to as desired
    * keep it 3 words if possible, eg: `xxx.xxx.xxx.app`
  * `settings.gradle`
    * `rootProject.name`
  * `build.gradle`
    * Add/Remove dependencies as necessary
    * `archiveFileName` and `Main-Class` in `jar`
    * `mainClass` in `application`
  * gradle wrapper version as necessary
  * `logback.xml` as necessary
    * replace `nospring-service-skeleton` with application name in `LOG_PATTERN`
  * `Dockerfile` as necessary
    * esp `JAR_FILE`, `COPY` and environment variables in `ENTRYPOINT`
  * `Util.java`
    * Update `CONTEXT_PATH`
    * add/update/remove other constants/helper-methods
  * GCP configurations, in `gcp` folder as necessary
    * esp `app-credentials.yaml` and `app-credentials_DUMMY.yaml`
  * `README.md` i.e. this file to add the program's readme
  * `.gitignore` if necessary
  * `App.java`
    * begin and end logs
* Things to remove:
  * If not using cache
    * Remove `AppReset` from servlet package and it's mapping from `ServerJetty.java` and `ServletFilter.java`
