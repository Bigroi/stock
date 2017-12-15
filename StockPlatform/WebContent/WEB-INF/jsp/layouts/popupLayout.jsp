<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<div class="dialogbox">
	<div class="dialogbox-child">
		<div class="dialogbox-Head"></div>
		<div class="dialogbox-Content">
			<div class="dialogbox-message"></div>
			<div class="dialogbox-inner">
				<div class="dialogbox-elementContent">
					<tiles:insertAttribute name="body" />
				</div>
			</div>
		</div>
		<div class="dialogbox-Buttons"></div>
	</div>
</div>
