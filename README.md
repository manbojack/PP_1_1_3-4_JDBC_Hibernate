### Структура проекта:

#### 1. <b>DAO слой:</b>
>- ##### [interface UserDAO](src/main/java/jm/task/core/jdbc/dao/UserDao.java)
>- #### [class UserDaoJDBCImpl](src/main/java/jm/task/core/jdbc/dao/UserDaoJDBCImpl.java)
>- ###### class UserDaoHibernateImpl ( в данной задаче не используется )

#### 2. <b>Service слой:</b>
>- #### [interface UserService](src/main/java/jm/task/core/jdbc/service/UserService.java)
>- #### [class UserServiceImpl](src/main/java/jm/task/core/jdbc/service/UserServiceImpl.java)

#### 3. <b>Util ( для создания соединения с БД ):</b>
>- #### [class Util](src/main/java/jm/task/core/jdbc/util/Util.java)

#### 4. <b>class Main ( точка входа ):</b>
>- #### [class Main](src/main/java/jm/task/core/jdbc/Main.java)


#### 5. <b>MySQL server:</b>
>- #### [Запустить docker-контейнер](.docker)
