# Wish List
## MySQL
### MySQL Workbench

The database model was designed using this app. To install it, use the command below:

    sudo snap install mysql-workbench-community

### Using Docker
To start a local mysql image to use for the application database:

    docker run --detach --name wlist-mysql -p 9906:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=wlist -e MYSQL_USER=wl_user -e MYSQL_PASSWORD=wl_pass -d mysql
   
or use the container id:
    
    docker start aef8d24b54bd 

To test if the image is running perfectly:

```
    docker logs wlist-mysql
    
    sudo apt install mysql-client-core-8.0
    mysql -u wl_user -P 9906 --protocol=tcp -p
```