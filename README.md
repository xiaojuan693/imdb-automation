# imdb.com Test Automation

This project provides comprehensive UI test automation for imdb.com using Java 17, Selenide, TestNG, Allure, and Gradle.

# file structure
imdb-automation/
├── src/test/java/com/imdb/
│   ├── base/
│   │   └── BaseTest.java          # Base test class with setup/teardown
│   ├── config/
│   │   └── TestConfig.java         # Test configuration
│   ├── pages/                      # Page Object Model
│   │   ├── BasePage.java
│   │   ├── HomePage.java
│   │   ├── TitlePage.java
│   │   └── ProfilePage.java
│   ├── tests/
│   │   └── IMDbSearchTest.java     # Main test class
│   └── utils/
│       ├── AllureTestListener.java # Allure reporting listener
│       └── TestData.java           # Test constants
├── src/test/resources/
│   ├── testng.xml                  # TestNG configuration
│   ├── allure.properties           # Allure configuration
│   └── log4j2.xml                  # Logging configuration
└── build.gradle                    # Gradle build file
## Requirements

- Java 17 or higher
- Gradle 7.0 or higher
- Chrome browser
- git
--github
-intelij IDE
## Setup

1.-------------------------- if on local,
install and configure jdk17. gradle, inteij IDE
create necessary file,
run:
run code
./gradlew clean build 
generate allure report on brower
 allure serve allure-results

 2. -------------------------if use git commit and push
 1. Install Git on Windows
Download and Install:
Go to https://git-scm.com/download/win

Download the 64-bit Git for Windows Setup

Run the installer with these settings:

Install Location: D:\Git\ (or C:\Program Files\Git\)

Select Components: Keep all defaults

Choose Default Editor: Choose VS Code or Notepad++

Adjust PATH: Choose "Git from the command line and also from 3rd-party software"

SSH Executable: Use OpenSSL

Line Endings: Choose "Checkout as-is, commit as-is"

Terminal Emulator: Use MinTTY

Default Behavior: "Git Pull" - choose default

Credential Helper: Git Credential Manager

Extra Options: Enable file system caching

Verify Installation:
Open Command Prompt or PowerShell:

bash
git --version
2. Create GitHub Account
Go to https://github.com/join

Fill in:

Username

Email address

Password

Verify your email address

Complete setup

3. Configure Git with Your Identity
Open Command Prompt or Git Bash:

bash
git config --global user.name "Your GitHub Username"
git config --global user.email "your-email@example.com"
git config --global init.defaultBranch main
4. Connect Git with IntelliJ IDEA
Configure Git in IntelliJ:
Open IntelliJ IDEA

Go to File → Settings (Ctrl+Alt+S)

Navigate to Version Control → Git

In "Path to Git executable":

It should auto-detect: D:\Git\bin\git.exe or C:\Program Files\Git\bin\git.exe

If not, click ... and browse to the location

Click Test - should show Git version

Click OK

Configure GitHub Account in IntelliJ:
File → Settings → Version Control → GitHub

Click + → Login with GitHub

Authorize IntelliJ in your browser

Or use Token:

Go to GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)

Generate new token with repo permissions

Copy token and paste in IntelliJ

5. Prepare Your Gradle Project
Project Structure Should Look Like:
text
your-project/
├── src/
│   └── main/
│       └── java/
├── build.gradle
├── settings.gradle
├── gradle/
│   └── wrapper/
├── gradlew
├── gradlew.bat
└── .gitignore
Create/Verify .gitignore File:
Create .gitignore in project root with:

gitignore
# Gradle
.gradle/
build/
!gradle/wrapper/gradle-wrapper.jar

# IntelliJ
.idea/
*.iml
*.iws
*.ipr
out/

# Allure
allure-results/
allure-reports/

# System
.DS_Store
Thumbs.db

# Logs
*.log
logs/
6. Initialize Git Repository in IntelliJ
Enable Version Control:
VCS → Enable Version Control Integration

Select Git → OK

Or Initialize via Terminal in IntelliJ:
bash
git init
git add .
git commit -m "Initial commit"
7. Create GitHub Repository
Method 1: Via GitHub Website:
Go to https://github.com/new

Repository name: your-project-name

Description: (optional)

Choose Private (recommended)

DO NOT initialize with README, .gitignore, or license

Click Create repository

Method 2: Via IntelliJ (after connecting GitHub):
Git → GitHub → Share Project on GitHub

Enter repository name

Description: (optional)

Choose Private

Click Share

8. Connect Local Repository to GitHub
If you created repository manually on GitHub:
Git → Manage Remotes

Click +

Name: origin

URL: https://github.com/your-username/your-repo-name.git

Click OK

Or via Terminal:
bash
git remote add origin https://github.com/your-username/your-repo-name.git
9. Commit and Push to GitHub
Using IntelliJ Interface:
Git → Commit (Ctrl+K)

Select files to commit (check all needed files)

Commit message: Initial commit

Click Commit and Push

Or Step by Step:
bash
# Add files
git add .

# Commit
git commit -m "Initial commit"

# Push to GitHub
git push -u origin main
10. Verify Upload
Visit your GitHub repository: https://github.com/your-username/your-repo-name

Confirm all files are present

Check file structure is correct


## Test Scenarios
Automate below test steps as you would automate a regression test case:

Open imdb.com
Search for "QA" with the search bar
When dropdown opens, save the name of the first title
Click on the first title
Verify that page title matches the one saved from the dropdown
Verify there are more than 3 members in the "top cast section"
Click on the 3rd profile in the "top cast section"
Verify that correct profile have opened
Use: Gradle, Selenide, java 17, TestNG, Allure-report

notice:
Here's the clear distinction:
Promotional Sections:
Halloween Family Fun (section header)

Stranger Things (featured in Halloween section)

The Black Phone (featured in Halloween section)

Book Tickets (action button)

Actual Search Results (Titles):
Q&A ← FIRST TITLE ✓

Qarib Qarib Single ← Second title

Qais Karadsheh ← Person result

# Features
Page Object Model (POM) design pattern

Allure reporting with automatic screenshots

Real IMDb DOM element interactions

Smart search result detection

Automatic cookie consent handling

Flexible title matching

Cross-browser support

Comprehensive test logging

# Design Patterns
Page Object Model: Separate page classes for Home, Title, and Profile pages

Base Test Class: Common setup and teardown for all tests

Utility Classes: Test data management and Allure listeners

Configuration Management: Centralized test configuration

Listener Pattern: For test execution monitoring and reporting

Builder Pattern: For search result classification and validation
