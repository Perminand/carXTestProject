<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Описание проекта "Синхронизация и накопление игровой статистики"</title>
</head>
<body>

<h1>Описание проекта "Синхронизация и накопление игровой статистики"</h1>

<h2>Общая информация</h2>
<p>Компания приступила к разработке новой игры, и вашей задачей является создание Rest-сервиса для синхронизации пользовательских данных и накопления игровой статистики.</p>

<h2>Основные компоненты проекта</h2>
<ul>
    <li><strong>Модель данных:</strong>
        <ul>
            <li><code>UserSyncData</code>: содержит поля <code>money</code> (целое число) и <code>country</code> (строка).</li>
            <li><code>UserActivityData</code>: содержит поле <code>activity</code> (целое число) и временную метку.</li>
        </ul>
    </li>
    <li><strong>Репозиторий:</strong>
        <ul>
            <li><code>UserSyncRepository</code>: для работы с данными синхронизации.</li>
            <li><code>UserActivityRepository</code>: для работы с данными игровой статистики.</li>
        </ul>
    </li>
    <li><strong>Сервис:</strong>
        <ul>
            <li><code>SyncService</code>: обработка и хранение данных синхронизации.</li>
            <li><code>ActivityService</code>: обработка и накопление данных игровой статистики.</li>
        </ul>
    </li>
    <li><strong>Контроллер:</strong>
        <ul>
            <li><code>SyncController</code>: обработка запросов на синхронизацию данных.</li>
            <li><code>ActivityController</code>: обработка запросов на накопление игровой статистики.</li>
        </ul>
    </li>
    <li><strong>DTO (Data Transfer Object):</strong>
        <ul>
            <li><code>UserSyncDataDto</code>: объект для передачи данных синхронизации.</li>
            <li><code>UserActivityDataDto</code>: объект для передачи данных игровой статистики.</li>
        </ul>
    </li>
</ul>

<h2>Функциональные требования</h2>
<ul>
    <li><strong>Синхронизация данных:</strong>
        <ul>
            <li>Прием JSON-данных от пользователя с полями <code>money</code> и <code>country</code>.</li>
            <li>Проверка и валидация данных (размер не более 10 КБ).</li>
            <li>Хранение данных синхронизации в базе данных.</li>
        </ul>
    </li>
    <li><strong>Игровая статистика:</strong>
        <ul>
            <li>Прием данных активности от пользователя (целочисленное значение <code>activity</code>).</li>
            <li>Накопление и хранение данных активности в базе данных.</li>
        </ul>
    </li>
    <li><strong>Обработка запросов:</strong>
        <ul>
            <li>100 запросов на синхронизацию данных в день от каждого активного пользователя.</li>
            <li>1 запрос на получение текущего состояния пользователя в день.</li>
            <li>10,000 запросов на накопление данных активности в день от каждого активного пользователя.</li>
        </ul>
    </li>
</ul>

<h2>Технологический стек</h2>
<ul>
    <li><strong>Язык программирования:</strong> Java</li>
    <li><strong>Фреймворк:</strong> Spring Boot</li>
    <li><strong>ORM:</strong> JPA (Java Persistence API)</li>
    <li><strong>База данных:</strong> PostgreSQL</li>
    <li><strong>Тестирование:</strong> JUnit, Mockito</li>
</ul>

<h2>Заключение</h2>
<p>Проект "Синхронизация и накопление игровой статистики" предоставляет мощный инструмент для обработки и хранения данных пользователей в новой игре. Он обеспечивает высокую производительность и надежность при работе с большим количеством пользователей и данных, что позволяет эффективно управлять пользовательскими данными и игровой статистикой.</p>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Развертывание и проверка Docker-решения</title>
</head>
<body>

<h2>Шаги для развертывания</h2>

<h3>1. Подготовка Docker-состава</h3>
<ol>
    <li>Убедитесь, что у вас установлен Docker и Docker Compose. Если нет, установите их согласно документации Docker(<a href="https://docs.docker.com/get-docker/" target="_blank">https://docs.docker.com/get-docker/</a>).</li>
</ol>

<h3>2. Клонирование репозитория (если необходимо)</h3>
<ol>
    <li>Если ваш проект находится в репозитории, клонируйте его:
        <pre><code>git clone https://github.com/Perminand/carXTestProject.git;
cd carXTestProject</code></pre>
    </li>
</ol>

<h3>3. Запуск Docker Compose</h3>
<ol>
    <li>Откройте терминал в директории, где находится ваш <code>docker-compose.yml</code>, и выполните команду для запуска сервисов:
        <pre><code>docker-compose up -d</code></pre>
        Эта команда запустит все сервисы в фоновом режиме.
    </li>
</ol>

<h3>4. Проверка состояния контейнеров</h3>
<ol>
    <li>Проверьте состояние запущенных контейнеров:
        <pre><code>docker-compose ps</code></pre>
        Убедитесь, что все контейнеры (<code>postgresDb</code>, <code>redis_container</code>, <code>user-service</code>) находятся в состоянии <code>Up</code>.</li>
</ol>

<h3>5. Проверка здоровья сервисов</h3>
<ol>
    <li>Для проверки здоровья PostgreSQL:
        <pre><code>docker inspect -f "{{json.State.Health }}" postgresDb</code></pre>
        Убедитесь, что статус здоровья (<code>Status</code>) показывает <code>healthy</code>.</li>
    <li>Для проверки Redis, можно подключиться к контейнеру и выполнить команду <code>PING</code>:
        <pre><code>docker exec -it redis_container redis-cli
PING</code></pre>
        Если возвращается <code>PONG</code>, Redis работает корректно.</li>
</ol>

<h3>6. Проверка работы вашего приложения</h3>
<ol>
    <li>Откройте веб-браузер и перейдите по адресу <code>http://localhost:8080</code>, чтобы убедиться, что ваше приложение доступно и работает корректно.</li>
</ol>

<h2>Проверка развертывания</h2>

<ol>
    <li><strong>Проверка доступности сервисов:</strong>
        <ul>
            <li>Убедитесь, что PostgreSQL, Redis и ваше приложение доступны и работают без ошибок.</li>
        </ul>
    </li>
    <li><strong>Мониторинг логов:</strong>
        <ul>
            <li>Осмотрите логи контейнеров для выявления возможных ошибок или проблем:
                <pre><code>docker-compose logs -f</code></pre>
            </li>
        </ul>
    </li>
    <li><strong>Тестирование функциональности:</strong>
        <ul>
            <li>Проведите тестирование основных функций вашего приложения, чтобы убедиться, что все зависимости работают корректно.</li>
        </ul>
    </li>
</ol>

</body>
</html>