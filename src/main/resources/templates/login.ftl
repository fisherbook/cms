<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link rel="stylesheet" href="/static/plugins/layui/css/layui.css"/>
    <link rel="stylesheet" href="/static/css/login.css"/>
    <script src="/static/plugins/layui/layui.js"></script>
    <title>内容管理系统 - 登录</title>
</head>
<body>
<div class="login">
    <h3>用户登录</h3>
    <div class="login-wrap">
        <#if error??>
            <blockquote class="layui-elem-quote">${error}</blockquote>
        </#if>
        <form class="layui-form" id="login-form" action="/login.do" method="post">
            <div class="layui-form-item">
                <input type="text" name="username" id="username" required placeholder="用户名" class="layui-input">
            </div>
            <div class="layui-form-item">
                <input type="password" name="password" id="password" required placeholder="密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <input type="text" name="vcode" id="vcode" required placeholder="验证码" class="layui-input">
                </div>
                <div class="layui-inline">
                    <img src="/vcode" class="verify-img" id="verify-img"/>
                </div>
            </div>
            <div class="layui-form-item">
                <input name="rememberMe" lay-skin="primary" title="记住我" type="checkbox" value="true">
            </div>
            <div class="layui-form-item">
                <input type="submit" class="layui-btn layui-btn-normal" id="login-btn" value="登陆">
            </div>
        </form>
        <a href="/register">注册用户</a>
    </div>
</div>
<script type="text/javascript" src="/static/js/login.js"></script>
</body>
</html>