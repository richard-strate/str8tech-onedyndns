# Client usage 

The usage is pretty stright forward and best illustrated in the integration test [OneClientSessionImplIT](https://github.com/richard-strate/str8tech-onedyndns/blob/master/client/src/test/java/com/str8tech/onedyndns/client/jdk/OneClientSessionImplIT.java). If you are interested in running the integration tests, see below.

# Client integration test

The client comes with a junit-based integration test(s), these test classes are suffixed IT (instead of Test) and not part of the normal test run. To execute these properly there must be system properties available with test account information. Commonly these are eneterd in the maven `settings.xml` as profile-properties. These following system keys are used:

Key|Meaning
-|-
`it.one.userName`|Username used to access the one.com DNS management account 
`it.one.password`|Password used to access the one.com DNS management account 
`it.one.domain`|Domain to use for testing record managment, the test will create (and hopefull remove) UUID based record. No existing record will touched
