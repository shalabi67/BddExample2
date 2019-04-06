# BddExample2
Demo project for Spring Boot exposing employee and department rest apis

###
notice this ran on windows7 commands should be the same, but you need to take care of directories.

##Start containers
###Start mysql

docker run -p 3306:3306 -e MYSQL_ROOT_HOST=192.168.1.* -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=BddExample2 -e MYSQL_USER=example -e MYSQL_PASSWORD=example  --name MySql -d mysql/mysql-server
docker exec -it MySql mysql -uroot -p

####manual test for connection
docker exec -it MySql mysql -uroot -p
password is root

####connection failed
select user,host from mysql.user where user='root';

CREATE USER 'root'@'10.0.2.2' IDENTIFIED WITH mysql_native_password BY 'root';
grant all privileges on *.* to 'root'@'10.0.2.2' with grant option;


##start rabbitmq
docker run -d --hostname rabbitmq --name rabbitmq -p 15671:15671 -p 567:5671 -p 5672:5672 rabbitmq:3-management


##run application
####start mysql container
####start rabbitmq container
#### start application from intellij by running EmployyApplication
#### to use swagger: http://localhost:8080/swagger-ui.html#/department-controller provide username:admin and password admin
