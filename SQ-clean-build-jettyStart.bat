call start "google" http://127.0.0.1:8080/
call gradle clean --parallel
call gradle build --parallel 
call gradle jettyStart 