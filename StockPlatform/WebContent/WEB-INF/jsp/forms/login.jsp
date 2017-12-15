<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form">
    <ul id="form-list">
        <li>
             <h2>Contact Us</h2>
             <span class="required_notification">* Denotes Required Field</span>
        </li>
        <li>
            <label for="username">${lable.login.login}</label>
            <input type="email" name="username" placeholder="John Doe" required />
            <span class="form_hint">Proper format "name@something.com"</span>
        </li>
        <li>
            <label for="password">${lable.login.password}</label>
            <input type="password" name="password" placeholder="***********" required />
        </li>
    </ul>
</form>