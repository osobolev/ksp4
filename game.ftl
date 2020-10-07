<html>
<body>

<#if winner>
<h1>Поздравляем, ${user}, вы победитель!</h1>
<#else>
<h1>Здравствуйте, ${user}!</h1>
</#if>

<form method="post">
    Ваш счет: ${clickCount} <button type="submit">+1</button>
</form>

<#if !winner>
    Текущий рекорд: ${maxClickCount}
    <#list winners as w>
        <p>Игрок: ${w}
    </#list>
</#if>

<p>
<form method="post" action="/logout.html">
    <button type="submit">Выход</button>
</form>

</body>
</html>
