# SpringBootApp
run createContactTable.sql(under src/main/resources) in postgres before running app. 

## URL to run app-
http/localhost:8080/contacts

##REST calls-
###list contacts-
curl http://localhost:8080/contactlist

###add contact-
 curl -X POST -F 'name=davidwalsh' -F 'email=something' -F 'profession=cook' http://localhost:8080/contacts/createUser
 
###update contact-
curl -X POST -F 'name=davidwalsh' -F 'email=something' -F 'profession=chef' http://localhost:8080/contacts/updateUser

###delete contact-
curl -X DELETE http://localhost:8080/contacts/davidwalsh/deleteUser

##Scripts
Located in scripts folder


