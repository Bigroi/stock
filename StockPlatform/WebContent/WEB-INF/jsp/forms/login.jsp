<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form">
    <ul id="form-list">
        <li>
             <h2>${lable.login.loginForm }</h2>
        </li>
        <li>
            <label for="username"></label>
            <input type="email" name="username" placeholder="John Doe" required />
            <span class="form_hint">Proper format "name@something.com"</span>
        </li>
        <li>
            <label for="password"></label>
            <input type="password" name="password" placeholder="***********" required />
        </li>
    </ul>
</form>