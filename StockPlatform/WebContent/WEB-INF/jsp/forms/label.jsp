<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form" action="#" method="post" name="form">
	<input type="hidden" name="id" value="${label.id}">
	<h3>${label.labels.labelForm}</h3>
    <div>
		<div class="dialogbox-message"></div>
	</div>
	<div class="flex-input">
		<div>
            <label for="category">${label.labels.category}</label>
            <input type="text" name="category"/>
        </div>
        <div>
            <label for="name">${label.labels.name}</label>
            <input type="text" name="name"/>
        </div>
        <div>
            <label for="enUs">${label.labels.english}</label>
            <input type="text" name="enUs"/>
        </div>
        <div>
            <label for="ruRu">${label.labels.russian}</label>
            <input type="text" name="ruRu"/>
        </div>
         <div>
            <label for="pl">${label.labels.poland}</label>
            <input type="text" name="pl"/>
        </div>
        <div id="form-list"></div>
	</div>
</form>
