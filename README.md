Code-Sharing-Platform
===================
Данное приложение это платформа для совместного использования кода.
С помощью него разработчики могут поделиться частью кода.

Приложение разделено на Web и API интерфейсы.
Чтобы добавить новый код надо отправить POSt на api/code/new JSON вида:
````
{"code":"some code", "time":120, "views":10}
````
* code - сама часть кода;
* views - количество просмотров, после которых код удалится. 0 - нет ограничений;
* time - время через которое код удалится. 0 - нет ограничений.

В ответ придет JSON с id. Id используется для доступа к коду.
Для того чтобы посмотреть конкретный код надо отправить GET запрос
на /api/code/{id}. Чтобы получить 10 последних кусков надо отправить
GET запрос на /api/code/latest. Здесь будут видны только те куски, которые
не имеют ограничений. Web интерфейс работает аналогично, только без /api.