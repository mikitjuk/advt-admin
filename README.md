# advt-admin

This test example app with created server a Spring Boot API and display its data with an Angular UI.
Also needs:
- java 8
- postgres(user=postgres, password=secret, datebase=advt)

## Getting Started

To install this example application, run the following commands:

     git clone https://github.com/mikitjuk/advt-admin.git
     cd advt-admin
     
To run the server, cd into the api folder and run:

    $ mvn spring-boot:run

To run the client, cd into the ui folder and run:

    npm install && npm start
    open http://localhost:4200

Users for testing:

    name                email            role        
    "Jenna Rodriquez" - "in@nonegestas.ca" - "ADMIN"
    "Rooney Nelson" - "luctus.sit@arcu.ca" - "ADOPS"
    "Latifah Key" - "vitae@famesac.com" - "PUBLISHER"

Authentication works only on the email field.


