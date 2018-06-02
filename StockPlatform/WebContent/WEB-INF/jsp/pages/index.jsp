<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Index</title>
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
		<script src="/js/chartBuilder.js"></script>
		<script src="/js/productList.js"></script>
	</head>
	<body>
		<script>
			localization();
		</script>
		<div id="login-form-container"></div>
		<div class="wrapper">
		    <div class="content">
		   		<header>
					<div class="container">
						<img class="logo" src="img/logo.png" alt="YourTrader" title="YourTrader">
						<nav>
							<a href="#ex1" class="active">About YourTrader</a>
							<a href="#ex2">Benefits</a>
							<a href="#ex3">Products</a>
							<a href="#ex4">For whom</a>
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
							<h1>Description</h1>
							<p class="front-desc">Some another description for food shops, restaurants and so on!</p>
							<div class="front-desc-but">
								<div class="reg-as">Register as </div>
								<div class="reg-but">
									<button class="req-far green-button">Farmer</button>
									<div> or </div>
									<button class="req-buy blue-button">Buyer</button>
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
								<p class="adv-title">Only organic products</p>
								<p class="adv-desc">We allow you to find the best products at the best prices</p>
							</div>
							<div class="advantage">
								<div class="adv-icon">
									<img src="img/trusted.png" alt="YourTrader" title="YourTrader"/></a>
								</div>
								<p class="adv-title">Trusted sellers and farmers</p>
								<p class="adv-desc">All our partners improve their positions in the market</p>
							</div>
							<div class="advantage">
								<div class="adv-icon">
									<img src="img/deal.png" alt="YourTrader" title="YourTrader"/></a>
								</div>
								<p class="adv-title">Best deals</p>
								<p class="adv-desc">Every day our platform handles thousands of transactions</p>
							</div>
							<div class="advantage">
								<div class="adv-icon">
									<img src="img/platform.png" alt="YourTrader" title="YourTrader"/></a>
								</div>
								<p class="adv-title">Convenient platform</p>
								<p class="adv-desc">User-friendly interface and fast processing of orders</p>
							</div>
						</div>
					</div>
					<div class="products" id="ex3">
						<div class="container">
							<h3>Products</h3>
							<div class="product-cont">
								<!-- <div class="product prod-apple">
									<h4>Apple</h4>
									<div class="about-product">
										<div class="sell-product">
											<h5>Sell</h5>
											<p class="count">â¬152</p>
											<p class="desc-count">Average price per ton</p>
											<p class="count">33 151</p>
											<p class="desc-count">Requests volume</p>
											<button class="green-button">Sell apple</button>
										</div>
										<div class="buy-product">
											<h5>Buy</h5>
											<p class="count">â¬146</p>
											<p class="desc-count">Average price per ton</p>
											<p class="count">33 151</p>
											<p class="desc-count">Requests volume</p>
											<button class="blue-button">Buy apple</button>
										</div>
									</div>
								</div>
								<div class="product prod-potato">
									<h4>Potato</h4>
									<div class="about-product">
										<div class="sell-product">
											<h5>Sell</h5>
											<p class="count">â¬152</p>
											<p class="desc-count">Average price per ton</p>
											<p class="count">33 151</p>
											<p class="desc-count">Requests volume</p>
											<button class="green-button">Sell apple</button>
										</div>
										<div class="buy-product">
											<h5>Buy</h5>
											<p class="count">â¬146</p>
											<p class="desc-count">Average price per ton</p>
											<p class="count">33 151</p>
											<p class="desc-count">Requests volume</p>
											<button class="blue-button">Buy apple</button>
										</div>
									</div>
								</div> -->
							</div>
						</div>
					</div>
					<div class="for-whom" id="ex4">
						<div class="for-farmers">
							<div class="for-farm-cont">
								<h3>For farmers</h3>
								<ul>
									<li>We allow you to find the best products at the best prices</li>
									<li>All our partners improve their positions in the market</li>
									<li>Every day our platform handles thousands of transactions</li>
								</ul>
								<button class="start-trade green-button">Start trade now</button>
							</div>
						</div>
						<div class="for-buyers">
							<div class="for-buyers-cont">
								<h3>For buyers</h3>
								<ul>
									<li>We allow you to find the best products at the best prices</li>
									<li>All our partners improve their positions in the market</li>
									<li>Every day our platform handles thousands of transactions</li>
								</ul>
								<button class="start-buy blue-button">Start buy now</button>
							</div>
						</div>
					</div>
					<div class="preview-platform">
						<div class="container">
							<div>
								<h3>Responsive platform</h3>
								<p>The online shop covers all the needs of a contemporary ecommerce shop: responsiveness, user account section, a wishlist function, product detail view, tags for new- local- organic or sale products, a blog, a store website, live search and many more features.</p>
								<button class="register green-button">Register</button>
							</div>
						</div>
					</div>
				</main>
		    </div>
		    <footer>
			 	<div class="container">
			 		<div class="contacts">
			 			<p class="phone">+375 (29) <span>202-87-66</span></p>
			 			<p class="mail"><a href="">stock@info.com</a></p>
			 		</div>
			 		<div class="copyright">
			 			 © 2018, YourTrader
			 		</div>
			 	</div>
			</footer>
		</div>
		<script src="js/script.js"></script>
	</body>
</html>