<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form">
    <ul id="form-list">
        <li>
             <h2>${label.login.loginForm }</h2>
        </li>
        <li>
            <input type="email" name="username" placeholder="John Doe" required />
            <span class="form_hint">Proper format "name@something.com"</span>
        </li>
        <li>
            <input type="password" name="password" placeholder="***********" required />
        </li>
    </ul>
</form>