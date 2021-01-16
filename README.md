# Wish List
## MySQL

To use the application database:

    docker run --detach --name wlist-mysql -p 9906:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=wlist -e MYSQL_USER=wl_user -e MYSQL_PASSWORD=wl_pass -d mysql
