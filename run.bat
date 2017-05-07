call gradle clean --parallel
call gradle -q showProjectNames
call gradle build
call start "Chrome" "http:/localhost:8080/login"
