## Структура проекта:

### DAO слой:
>- #### [interface UserDAO](src/main/java/jm/task/core/jdbc/dao/UserDao.java)
>- #### [class UserDaoJDBCImpl](src/main/java/jm/task/core/jdbc/dao/UserDaoJDBCImpl.java)
>- ###### class UserDaoHibernateImpl ( в данной задаче не используется )

### Service слой:
>- #### [interface UserService](src/main/java/jm/task/core/jdbc/service/UserService.java)
>- #### [class UserServiceImpl](src/main/java/jm/task/core/jdbc/service/UserServiceImpl.java)

### Util ( для создания соединения с БД ):
>- #### [class Util](src/main/java/jm/task/core/jdbc/util/Util.java)

### class Main ( точка входа ):
>- #### [class Main](src/main/java/jm/task/core/jdbc/Main.java)


### MySQL server:
>- #### [Запустить docker-контейнер](.docker)
