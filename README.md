# SpringBootApp

## URL to run app-
http/localhost:8080/contacts

##REST calls-
list contacts-
curl http://localhost:8080/contactlist

add contact-
 curl -X POST -F 'name=davidwalsh' -F 'email=something' -F 'profession=cook' http://localhost:8080/contacts/createUser
 
update contact-
curl -X POST -F 'name=davidwalsh' -F 'email=something' -F 'profession=chef' http://localhost:8080/contacts/updateUser

delete contact-
curl http://localhost:8080/contacts/deleteUser/davidwalsh


