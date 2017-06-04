# all-files-download
An extensible design to download files over various protocols.


**Application flow:**

1. A list of file names to be downloaded is passed to `DownloadService.java`'s `downloadFiles(List<String> fileNames)` method.

2. A `FileDownloadService.java` is created with 3 implementations each for `http`, `ftp` and `sftp` type files.

3. The list of file names mentioned in step 1 is iterated over and each file name is passed to this `FileDownloadService.java`'s downloadFile(String fileName) method.

4. Each of the implementations of `FileDownloadService.java` overrides a `supports(String fileName)` method using which the `BeanService.java` decides the actual implementation to be called at the run time.
 
5. In the acutal implementation of `FileDownloadService.java`, a thread is launched which further completes the file download process for the particular file type.

6. Presently, a thread pool is used (Spring's `ThreadPoolTaskExecutor.java`) to restrict the number of threads running to 3. This can be changed as per the number of cores of the machine where the application is deployed.

7. `FailedRecordsService.java` is used to record all the failed records while downloading.

8. Once all the active threads are completed, one more attempt is made to download all the failed records.


**How to run:**

1. Checkout this project on your local machine.

2. Make sure Java8 and Maven 3.3.9 is installed on your machine.
 
3. Navigate to the project directory (eg. /Users/raviagrawal/projects/all-files-download)

4. Run `mvn clean install`

5. Run the file `Application.java` or `DownloadServiceImplIT.java` to test the application.
