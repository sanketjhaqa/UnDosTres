**UnDosTres Assignment**

For Browser Compatibility **Device Properties** file is there.
For Config data **config properties** files is used.
For validation messages related things **messages.properties** getting used.
For environment such as running regression on **QA** or **DEV** or **UAT** **environment.properties** is there.

**Running the project:**
1. On Chrome Profile "WebSuiteChrome" is there
2. On FireFox Profile "WebSuiteFireFox" is there.

Similarly we can created profiles for the other browsers

**To run through maven :**
**mvn clean install -PWebSuiteChrome** : for Chrome
**mvn clean install -PWebSuiteChrome** : for firefox

Reports:
target/cucumber-report/finalReport/cucumber-html-report/overview-feature.html


**Plugins needed:**
    **IntelliJ**
        1.gherkins
        2.cucumber for java


