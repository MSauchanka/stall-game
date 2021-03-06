## Plans

- Проверка доступа к кешбоксу у npc
- Подключать пользователя к World
- Добавить события в world
- Улучшить npc.generateNames()
- Добавить загрузку товара через заднюю дверь
- Добавить npc список желаемых покупок основанной на типах товаров и таблицы стоимости
- Выделять main char в массиве других npc
- Добавить класс Storage с операциями remove, add, is product available и т.д.

- Добавить тест который будет проверять что какая-либо транзакция прошла между npc на сервере, один из которых получил ключ
- Назначение ролей по исполнении роли

- Файл NpcNames добавить расширение txt
- Конфигурация сервера в отдельном файле (порт на котором он ожидает клиента и прочие технические моменты)          

- Попытаться отделить Environment от сервера (сервер создает Environment и раздает клиентам,
но сервер был отдельным)
- иметь возможность запускать на одном сервере несколько Environment (Server) к которым можно подключаться

Для зданий добавить:
    // private ProductsLoadPlace productsLoadPlace;
    // private RadioPlace radioPlace;
    // private RestPlace restPlace;
    // private StallWindow stallWindow;
    // private StallQueue stallQueue;
    

- UI для клиента с выбором - Создать мир или Подключиться к существующему + у существующего количество клиентов
    - Коммуникация при помощи json
    - UI зависит от ответа сервера
- Многопоточность через Executor (снаружи while for game loop)
- Shell script для загрузки сервака в инстанс
- Добавить на тот же инстанс документацию
- Выбрать теоритические темы для java dev специализации

1. json для сервера и клиента (gson)
2. UI



- Почитать про creation patterns https://en.wikipedia.org/wiki/Creational_pattern

## Done

Dec, 18

- Enum Actions с имплементациями поведения
- Удалены StallVisitor.class & Visitor.class Все методы переехали в NonPlayableCharacter.class
- Консольный интерфейс:
    - вывод данных о текущем состоянии всего мира после каждой команды
    - loop для выполнения команд 
    - нумерованный список команд только тех которые доступны игроку на данный момент
??? Вопрос с входом в ларек - изначальная реализация проверки на роль не подходит, нужен ключ
    
Dec, 20

- Item получил description
- Продукт наследник Item имеет тип, цену и имя
- Добавлен ключи, наследник Item
- Добавлены Enter, leave, leave and lock для двери и рабочего места
- Добавлкны Actions leave and lock, review inventory
- Логика для Playable Char с выбором действий
    
Jan, 2

- Добавлены ключи для npc на Server
- Добавлен BUY в actions
- Метод isProductsAvailable проверяет есть ли продукты требуемого типа и количества на складе
- Добавить логирование для Server (read slf4j)
    - один стрим про действия
    - второй стрим про состояния
- Добавлены равномерные веса выбора действий

Jan, 4

- Исправлено getNames()
- Исправлена очистка консоли
- Дoбавлено вычитание продуктов и Storage по типу
- Дoбавлены цели TO_WORK, TO_HOME, TO_STALL
- Дoбавлены веса действий в зависимости цели и роли npc (POC)
- Дoбавлены роли IN_BUS, AROUND_STALL, AT_WORK (только на уровне enum)
- Дoбавлен таймер нахождения в роли
  
Jan, 16

- Добавлено -> Локация знает количество людей, люди знают в какой они локации (схема - можно юзать http://visjs.org/ )
    - Люди и здания в world, комнаты в здании
- Добавлены статические методы для инстанциации локаций мира (проапдейтаны классы и тесты)
- Добавлен класс Container который базовый для всех локаций с npc
- Дверь теперь класс который связывает локации (роли) между собой и вызывает у локаций add remove npc
- У npc убраны все методы которые привязывали их к локации и добавлены универсальные enter, leave, leave and lock
- Добавлена документация и схемы http://visjs.org/ НО НЕ СОБРАНЫ НА ОДНОЙ MD PAGE - НАДО РАЗОБРАТЬСЯ КАК
-  ЧАСТИЧНО - Закончить текцщие таски (баги, роли, документация (https://maven.apache.org/guides/mini/guide-snippet-macro.html), схема сервера (доп картинка))
    - Документацияв репо - https://blog.github.com/2016-08-22-publish-your-project-documentation-with-github-pages/
- ОЗНАКОМИЛСЯ С ДОКУМЕНТАЦИЕЙ HIGH LEVEL - Подключение клиента через сеть
    - протоколы подключения (HTTP/WebSocket | JavaSocket)
    - WebSocket - Jetty https://www.eclipse.org/jetty/documentation/9.4.x/index.html (start server, start client, read about web socket)
    
Jan, 18

- Container to Area - done
- Portal added
- Bugs fixed
- Overall count - workaround

Jan, 27

- Client & Server Jetty WebSocket - done
- Test connection - done
- add world reporting on java swing

Feb, 28

- UI для клиента с выбором - Создать мир или Подключиться к существующему + у существующего количество клиентов
      - Коммуникация при помощи json
      - UI зависит от ответа сервера
  - Многопоточность через Executor (снаружи while for game loop)
 
