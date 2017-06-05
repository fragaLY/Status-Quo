call gradle clean --parallel
call gradle -q showModulesNames
call gradle build
call start "Chrome" "http:/localhost:8080/login"
