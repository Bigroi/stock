<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="dialogbox">
	<div class="dialogbox-child">
		<div class="dialogbox-Head"></div>
		<div class="dialogbox-Content">
			<div class="dialogbox-inner">
				<div class="dialogbox-elementContent">
					<tiles:insertAttribute name="body" />
				</div>
			</div>
		</div>
	</div>
</div>
