<html>
<body>
    <#if error??>
        <h2>${error}</h2>
    </#if>
    <form method="post">
        <label for="idLogin">Логин:</label><input id="idLogin" name="login">
        <label for="idPassword">Пароль:</label><input id="idPassword" name="password" type="password">
        <button type="submit">Вход</button>
    </form>
</body>
</html>
