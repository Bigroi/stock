<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<link href="/css/style.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
		
		<link rel="stylesheet" href="/css/formStyle.css">
		<link rel="stylesheet" href="/css/dialogboxStyle.css">
		<link rel="stylesheet" href="/css/buttonsStyle.css">
		
		<script src="/js/jQuery.js"></script>
		<script src="/js/jquery.dataTables.min.js"></script>
        <script src= "/js/localization.js"></script>
		<script src= "/js/tableMaker.js"></script>
		<script src="/js/map.js"></script>
		<script src="/js/dialogbox.js"></script>
		<script src="/js/form.js"></script>
		<script src="/js/script.js"></script>
		<script src="/js/plotly-latest.min.js"></script>
		<script src="/js/productList.js"></script>

	</head>
	<body>
		<div id="login-form-container"></div>
		<div class="wrapper">
		    <div class="content">
		   		<header>
					<div class="container">
						<img class="logo" src="img/logo.png" alt="YourTrader" title="YourTrader">
						<nav>
							<a href="#ex1" class="active">${label.index.about_your_trader}</a>
							<a href="#ex2">${label.index.benefits}</a>
							<a href="#ex3">${label.index.products}</a>
							<a href="#ex4">${label.index.for_whom}</a>
						</nav>
						<div class="buttons-login">
							<a class="button register blue-button" href="/account/Registration.spr">
								${label.navigation.regestration}
							</a>
							<button class="sign-in">
								${label.navigation.login}
	                			<script type="text/javascript">setLoginDialogPlugin($(".sign-in"))</script>
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
									<button class="req-far green-button">${label.index.farmer}</button>
									<div>${label.index.regestrate_as_buyer}</div>
									<button class="req-buy blue-button">${label.index.buyer}</button>
								</div>
							</div>
						</div>
					</div>
					<div class="advantages" id="ex2">
						<div class="container">
							<div class="advantage">
								<div class="adv-icon">
									<img src="img/organic.png" alt="YourTrader" title="YourTrader"/></a>
								</div>
								<p class="adv-title">${label.index.speed_title}</p>
								<p class="adv-desc">${label.index.speed_description}</p>
							</div>
							<div class="advantage">
								<div class="adv-icon">
									<img src="img/trusted.png" alt="YourTrader" title="YourTrader"/></a>
								</div>
								<p class="adv-title">${label.index.trust_title}</p>
								<p class="adv-desc">${label.index.trust_description}</p>
							</div>
							<div class="advantage">
								<div class="adv-icon">
									<img src="img/deal.png" alt="YourTrader" title="YourTrader"/></a>
								</div>
								<p class="adv-title">${label.index.deal_title}</p>
								<p class="adv-desc">${label.index.deal_description}</p>
							</div>
							<div class="advantage">
								<div class="adv-icon">
									<img src="img/platform.png" alt="YourTrader" title="YourTrader"/></a>
								</div>
								<p class="adv-title">${label.index.сonvenient_title}</p>
								<p class="adv-desc">${label.index.сonvenient_description}</p>
							</div>
						</div>
					</div>
					<div class="products" id="ex3">
						<div class="container">
							<h3>${label.index.products}</h3>
							<div class="product-cont"></div>
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
								<button class="start-trade green-button">${label.button.start_trade_now }</button>
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
								<button class="start-buy blue-button">${label.button.start_buy_now}</button>
							</div>
						</div>
					</div>
					<div class="preview-platform">
						<div class="container">
							<div>
								<h3>${label.index.responsive_title}</h3>
								<p>${label.index.responsive_description}</p>
								<button class="register green-button">${label.button.registrate}</button>
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