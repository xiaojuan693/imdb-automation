# BasePage.java --------------------------------------------------------------------
implements Selenide Page Object Model with robust utility methods for web automation foundation.

Core Logic & Strategy:

Centralized Element Interactions: Unified methods for typing, clicking, and navigation

Defensive Waiting Strategy: Explicit waits with graceful error handling

Cross-Browser Command Abstraction: Keyboard actions and element operations

Key Utility Methods:

URL Navigation: Safe page loading with Selenide wrapper

Text Input Handling: Clear-and-type pattern with element validation

Keyboard Integration: Enter key simulation for form submissions

Resilient Click Operations: Retry mechanism for flaky interactions

Timed Delays: Configurable wait states for dynamic content

Design: Template method pattern providing reusable web interaction blueprint with comprehensive exception handling and thread-safe operations.

# HomePage.java --------------------------------------------------------------------
implements intelligent search result filtering with DOM pattern recognition for reliable IMDb automation.

Core Logic & Strategy:

Smart Result Classification: Multi-layered filtering to distinguish promotional content from actual search results

Pattern-Based Recognition: DOM structure analysis and metadata validation

Progressive Element Discovery: Sequential scanning with type categorization

Key Automation Methods:

Cookie Consent Handling: Automatic banner dismissal for uninterrupted flow

Intelligent Search Execution: Query input with dynamic dropdown management

Result Type Discrimination: Promotional vs. person vs. actual content filtering

Resilient Click Targeting: First valid result selection with validation

Classification Algorithm:

Promotional Detection: Title pattern matching and structural analysis

Person Result Filtering: Metadata content analysis (actor/director cues)

Actual Result Validation: Year metadata, actor information, and query relevance

Design: Strategy pattern for result classification with comprehensive debugging capabilities and defensive element interaction handling.

# ProfilePage.java-------------------------------------------------------------------------------
implements minimal verification pattern for IMDb profile validation with essential element checks.

Core Logic & Strategy:

Presence Validation: Confirms profile page loading through key element visibility

Content Extraction: Retrieves profile name and bio information

Page State Verification: Dual-element check for comprehensive page load confirmation

Key Validation Methods:

Profile Identity Retrieval: Hero title extraction for name verification

Bio Content Access: HTML content retrieval for profile description

Load State Confirmation: Combined element visibility for page readiness

Design: Verification-focused page object with Selenide conditional waiting for reliable profile page interaction and state management.
# TitlePage.java------------------------------------------------------------
implements resilient element location strategy with fallback selectors for robust IMDb title page interaction.

Core Logic & Strategy:

Multi-Selector Fallback Pattern: Cascading element location with primary and backup selectors

Adaptive Page Verification: Multiple checks for page load state confirmation

Defensive Scrolling: Graceful navigation to critical sections with fallback mechanisms

Key Interaction Methods:

Flexible Title Retrieval: Triple-selector approach for page title extraction

Cast Section Navigation: Targeted scrolling with alternative section detection

Cast Member Operations: Index-based actor selection with dual-location strategy

Content Validation: Cast count verification and member information extraction

Element Location Strategy:

Primary Selectors: data-testid attributes for reliable element targeting

Backup Selectors: CSS classes and semantic HTML as fallbacks

Legacy Support: Basic element selectors for maximum compatibility

Design: Fallback pattern implementation with comprehensive state monitoring and graceful degradation for inconsistent DOM structures.

# BaseTest.java---------------------------------------------------------
implements Selenide test framework foundation with Allure BDD integration and lifecycle management.

Core Logic & Strategy:

Centralized Test Configuration: Unified setup and teardown for all test classes

Listener Pattern Integration: Allure reporting hook for behavior-driven documentation

Resource Management: Automated browser lifecycle control

Key Lifecycle Methods:

Pre-Test Setup: Browser configuration and test environment initialization

Post-Test Cleanup: WebDriver termination and resource release

Cross-Cutting Concerns: Reporting integration and framework configuration

Design: Template method pattern providing test infrastructure blueprint with configuration encapsulation and automated resource management.

# IMDbSearchTest.java --------------------------------------------------------------------
implements end-to-end search validation workflow with state preservation and comprehensive assertion strategy.

Core Logic & Strategy:

Sequential Flow Validation: 10-step navigation from search to profile verification

State Preservation Pattern: Saves intermediate results (title, cast member) for cross-page validation

Flexible Matching Algorithm: Contains-based title/name comparison for dynamic content handling

Key Test Methods:

End-to-End Navigation: Search → Title → Cast → Profile complete workflow

Multi-Page Validation: Cross-verification between dropdown, title page, and profile

Content Integrity Checks: Title consistency and cast member flow validation

Resilient Assertion Strategy: Flexible string matching with comprehensive error reporting

Validation Strategy:

Pre/Post State Comparison: Dropdown title vs. page title verification

Content Threshold Testing: Minimum cast member count validation

Flow Integrity: Cast member name preservation across page transitions

Design: Stateful test pattern with comprehensive exception handling, detailed logging, and progressive verification for robust end-to-end testing.

# TestConfig.java --------------------------------------------------------------------
implements centralized test automation configuration with Chrome optimization and Allure integration.

Core Logic & Strategy:

Unified Configuration Management: Single point for all framework and browser settings

Browser Optimization: Chrome performance and stability arguments

Reporting Integration: Allure listener setup for comprehensive test documentation

Key Configuration Areas:

Selenide Framework: Timeout, browser size, screenshot, and headless mode settings

Chrome Capabilities: Notification blocking, popup handling, and stability arguments

Allure Reporting: Screenshot capture and page source configuration

Optimization Strategy:

Performance: Disabled features that interfere with automation (notifications, popups)

Stability: Increased timeouts and added Chrome stability arguments

CI/CD Readiness: Headless mode capability and remote origin allowances

Design: Configuration facade pattern providing simplified interface for complex browser and framework setup with cross-environment compatibility.

# AllureTestListener.java -----------------------------------------------------------------
implements automated screenshot capture strategy with comprehensive test lifecycle monitoring.

Core Logic & Strategy:

Test Lifecycle Hook Integration: Listener pattern for test state transitions

Conditional Screenshot Capture: Visual evidence collection on success/failure/skip

Robust Attachment Handling: Binary screenshot management with error resilience

Key Monitoring Methods:

Failure Documentation: Automatic screenshot on test failure for debugging

Success Verification: Positive test case evidence capture

Skip Tracking: Screenshot documentation for skipped test scenarios

Attachment Strategy:

Binary Stream Management: Efficient screenshot byte handling for Allure reporting

Error-Resilient Capture: Graceful failure handling for screenshot operations

Contextual Naming: Descriptive attachment names for test state identification

Design: Observer pattern implementation for test lifecycle events with defensive programming for reliable screenshot capture across all test outcomes.

# TestData.java-------------------------------------------------------------------------
implements centralized test configuration management with constant encapsulation for maintainable test data.

Core Logic & Strategy:

Single Source of Truth: Centralized repository for all test parameters and configuration

Environment Abstraction: URL and test data separation from test logic

Maintainability Focus: Easy configuration updates without code changes

Key Data Categories:

Environment Configuration: Base URL for test execution

Test Parameters: Search queries and validation criteria

Navigation Constants: Index-based selectors for UI interactions

Design Strategy:

Constant Encapsulation: All test data exposed as static final constants

Zero-based Index Management: Consistent indexing for collection interactions

Separation of Concerns: Test logic isolated from test data configuration

Architecture: Configuration holder pattern providing immutable test parameters with clear semantic naming for test maintainability.
