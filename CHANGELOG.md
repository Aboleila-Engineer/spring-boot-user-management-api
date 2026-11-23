# Prepared-copy changes

- Preserved the original user-management backend purpose and MySQL choice.
- Replaced inconsistent experimental routes and unfinished code with clear REST endpoints. This is a breaking cleanup, not a drop-in API replacement.
- Added constructor injection, request validation, DTOs, transactions, generated timestamps and structured error responses.
- Added BCrypt hashing and removed password fields from responses.
- Removed bundled IDE state, compiled output and all original database credentials from this prepared copy. Added environment-based database configuration and git exclusions.
- Added a local H2 demo profile, browser interface, Maven wrapper pin and API integration tests.
- Standardized both prepared copies on Spring Boot 3.5.8 and Java 17.
- Original submissions are unchanged and are not included in this cleaned source archive.
- No deployment, GitHub upload or LinkedIn publication has occurred.
