<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
	
		<script async src="https://www.googletagmanager.com/gtag/js?id=UA-114993452-1"></script>
		<script>
		  window.dataLayer = window.dataLayer || [];
		  function gtag(){dataLayer.push(arguments);}
		  gtag('js', new Date());
		
		  gtag('config', 'UA-114993452-1');
		</script>
	
		<title>Your Trader! ${page_title}</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="initial-scale=1.0, width=device-width">
		<meta name="context-root" content="${pageContext.request.contextPath}">
		<meta name="google-site-verification" content="57vTO4c-SbhNNvbqR0xSlTjHLgy0kd3Bi7173PGTMbc" />
		
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Static/img/logo.png" type="image/png">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/jquery.responsive.dataTables.min.css"> 
		
		<script src="${pageContext.request.contextPath}/Static/js/jQuery.js"></script>
        <script src="${pageContext.request.contextPath}/Static/js/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/Static/js/jquery.responsive.dataTables.min.js"></script>
		
		<!-- application css & js -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/style.css?version=${label.build.number}">
        
        <script src="${pageContext.request.contextPath}/Static/js/form.js?version=${label.build.number}"></script>
        <script src="${pageContext.request.contextPath}/Static/js/intiFormParams.js?version=${label.build.number}"></script>
        <script src="${pageContext.request.contextPath}/Static/js/localization.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/tableMaker.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/map.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/dialogbox.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/script.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/productList.js?version=${label.build.number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/testTrading.js?version=${label.build.number}"></script>
	</head>
	<body>
		<div id="form-container"></div>
		<div class="wrapper">
		    <div class="content">
		   		<header>
					<div class="container">
						<img class="logo" src="/Static/img/logo.png" alt="YourTrader" title="YourTrader">
						<nav class="main-page-nav">
							<a href="#ex1" class="active">${label.index.about_your_trader}</a>
							<a href="#ex2">${label.index.benefits}</a>
							<a href="#ex3">${label.index.products}</a>
							<a href="#ex4">${label.index.for_whom}</a>
						</nav>
						<div class="buttons-login">
							<button class="registration background-blue registration-button">
								${label.navigation.regestration}
							</button>
							<button class="sign-in login-button">
								${label.navigation.login}
	                		</button>
						</div>
					</div>
				</header>
				<main>
					<div class="front" id="ex1">
						<div class="container">
							<h1>${label.index.description_title}</h1>
							<p class="front-desc">${label.index.description}</p>
							<div class="front-desc-but">
								<div class="reg-as">${label.index.regestrate_as_farmer} </div>
								<div class="reg-but">
									<button class="req-far background-green registration-button">${label.index.farmer}</button>
									<div>${label.index.regestrate_as_buyer}</div>
									<button class="req-buy background-blue registration-button">${label.index.buyer}</button>
								</div>
							</div>
						</div>
					</div>
					<div class="advantages" id="ex2">
						<div class="container">
							<div class="advantage">
								<div class="adv-icon">
									<img src="/Static/img/organic.png" alt="YourTrader" title="YourTrader"/>
								</div>
								<p class="adv-title">${label.index.speed_title}</p>
								<p class="adv-desc">${label.index.speed_description}</p>
							</div>
							<div class="advantage">
								<div class="adv-icon">
									<img src="/Static/img/trusted.png" alt="YourTrader" title="YourTrader"/>
								</div>
								<p class="adv-title">${label.index.trust_title}</p>
								<p class="adv-desc">${label.index.trust_description}</p>
							</div>
							<div class="advantage">
								<div class="adv-icon">
									<img src="/Static/img/deal.png" alt="YourTrader" title="YourTrader"/>
								</div>
								<p class="adv-title">${label.index.deal_title}</p>
								<p class="adv-desc">${label.index.deal_description}</p>
							</div>
							<div class="advantage">
								<div class="adv-icon">
									<img src="/Static/img/platform.png" alt="YourTrader" title="YourTrader"/>
								</div>
								<p class="adv-title">${label.index.сonvenient_title}</p>
								<p class="adv-desc">${label.index.сonvenient_description}</p>
							</div>
						</div>
					</div>
					<div class="products" id="ex3">
						<div class="container">
							<h3>${label.index.products}</h3>
							<div class="product-cont unauthorised"></div>
						</div>
					</div>
					<div class="for-whom" id="ex4">
						<div class="for-farmers">
							<div class="for-farm-cont">
								<h3>${label.index.for_farmers}</h3>
								<ul>
									<li>${label.index.for_farmers_li1}</li>
									<li>${label.index.for_farmers_li2}</li>
									<li>${label.index.for_farmers_li3}</li>
								</ul>
								<button class="start-trade background-green registration-button">
									${label.button.start_trade_now }
								</button>
							</div>
						</div>
						<div class="for-buyers">
							<div class="for-buyers-cont">
								<h3>${label.index.for_buyers}</h3>
								<ul>
									<li>${label.index.for_buyers_li1}</li>
									<li>${label.index.for_buyers_li2}</li>
									<li>${label.index.for_buyers_li3}</li>
								</ul>
								<button class="start-buy background-blue registration-button">
									${label.button.start_buy_now}
								</button>
							</div>
						</div>
					</div>
					<div class="test-trading">
						<button class="add-test-lot">NEW LOT</button>
						<div id="table-container">
						    <table id="main-table" class="test-lot-table" data-url="/lot/json/TestList.spr" data-add-button="add-test-lot"></table>
						</div>
						<button class="add-test-tender">NEW TENDER</button>
						<div id="table-container">
						    <table id="main-table" class="test-tender-table" data-url="/tender/json/TestList.spr" data-add-button="add-test-tender"></table>
						</div>
						<button class="test-trade">TRADE</button>
						<div id="table-container">
						    <table id="main-table" data-url="/deal/json/TestDeals.spr" data-add-button="test-trade"></table>
						</div>
					</div>
					<div class="preview-platform">
						<div class="container">
							<div>
								<h3>${label.index.responsive_title}</h3>
								<p>${label.index.responsive_description}</p>
								<button class="register background-green registration-button">
									${label.button.registrate}
								</button>
							</div>
						</div>
					</div>
				</main>
		    </div>
		    <footer>
			 	<div class="container">
			 		<div class="contacts">
			 			<p class="phone">${label.index.phone_prefix}<span>${label.index.phone}</span></p>
			 			<p class="mail"><a href="">${label.index.email}</a></p>
			 		</div>
			 		<div class="copyright">
			 			 ${label.index.copyright}
			 		</div>
			 	</div>
			</footer>
		</div>
	</body>
</html>