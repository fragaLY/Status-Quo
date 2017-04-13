call gradle clean --parallel
call gradle build 
call start "Chrome" "http:/127.0.0.1:8080/client/1"
call gradle jettyStart