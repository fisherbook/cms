<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"/>
    <link rel="stylesheet" href="/static/plugins/layui/css/layui.css"/>
    <link rel="stylesheet" href="/static/css/register.css"/>
    <script src="/static/plugins/layui/layui.js"></script>
    <title>内容管理系统 - 注册</title>
</head>
<body>
<div class="register">
    <h3>用户注册</h3>
    <div class="register-wrap">
        <blockquote class="layui-elem-quote hidden"></blockquote>
        <form class="layui-form">
            <div class="layui-form-item">
                <input type="text" id="username" required placeholder="用户名" class="layui-input">
            </div>
            <div class="layui-form-item">
                <input type="tel" id="telephone" required placeholder="电话" class="layui-input">
            </div>
            <div class="layui-form-item">
                <input type="email" id="email" required placeholder="邮箱" class="layui-input">
            </div>
            <div class="layui-form-item">
                <input type="password" id="password" required placeholder="密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <input type="password" id="re-password" required placeholder="确认密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <input type="text" id="vcode" required placeholder="验证码" class="layui-input">
                </div>
                <div class="layui-inline">
                    <img src="/vcode" class="verify-img" id="verify-img"/>
                </div>
            </div>
        </form>
        <div class="layui-form-item">
            <button class="layui-btn layui-btn-normal" id="register-btn">注册</button>
        </div>
    </div>
</div>
<script type="text/javascript" src="/static/js/register.js"></script>
</body>
</html>