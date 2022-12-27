### Структура проекта:

#### <b>DAO слой:</b>
>- ##### [interface UserDAO](src/main/java/jm/task/core/jdbc/dao/UserDao.java)
>- ##### [class UserDaoJDBCImpl](src/main/java/jm/task/core/jdbc/dao/UserDaoJDBCImpl.java)
>- ##### class UserDaoHibernateImpl ( в данной задаче не используется )

#### <b>Service слой:</b>
>- ##### [interface UserService](src/main/java/jm/task/core/jdbc/service/UserService.java)
>- ##### [class UserServiceImpl](src/main/java/jm/task/core/jdbc/service/UserServiceImpl.java)

#### <b>Util ( для создания соединения с БД ):</b>
>- ##### [class Util](src/main/java/jm/task/core/jdbc/util/Util.java)

#### <b>class Main ( точка входа ):</b>
>- ##### [class Main](src/main/java/jm/task/core/jdbc/Main.java)


#### <b>MySQL server:</b>
>- ##### [Запустить docker-контейнер](.docker)
