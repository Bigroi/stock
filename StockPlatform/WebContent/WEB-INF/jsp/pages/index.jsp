<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
	
		<c:if test="${ !dev_env}">
			<script async src="https://www.googletagmanager.com/gtag/js?id=UA-114993452-1"></script>
			<script>
			  window.dataLayer = window.dataLayer || [];
			  function gtag(){dataLayer.push(arguments);}
			  gtag('js', new Date());
			
			  gtag('config', 'UA-114993452-1');
			</script>
			
			<!-- Yandex.Metrika counter -->
			<script type="text/javascript" >
			   (function(m,e,t,r,i,k,a){m[i]=m[i]||function(){(m[i].a=m[i].a||[]).push(arguments)};
			   m[i].l=1*new Date();k=e.createElement(t),a=e.getElementsByTagName(t)[0],k.async=1,k.src=r,a.parentNode.insertBefore(k,a)})
			   (window, document, "script", "https://mc.yandex.ru/metrika/tag.js", "ym");
			
			   ym(51509546, "init", {
			        id:51509546,
			        clickmap:true,
			        trackLinks:true,
			        accurateTrackBounce:true,
			        webvisor:true
			   });
			</script>
			<noscript><div><img src="https://mc.yandex.ru/watch/51509546" style="position:absolute; left:-9999px;" alt="" /></div></noscript>
			<!-- /Yandex.Metrika counter -->
		</c:if>
	
		<title>${label.index.index_title}</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="initial-scale=1.0, width=device-width">
		<meta name="context-root" content="${pageContext.request.contextPath}">
		<meta name="google-site-verification" content="57vTO4c-SbhNNvbqR0xSlTjHLgy0kd3Bi7173PGTMbc" />
		<meta name="description" content="${label.index.index_description}">
		
		<link rel="shortcut icon" href="${pageContext.request.contextPath}/Static/img/logo.png" type="image/png">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/jquery.dataTables.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/jquery.responsive.dataTables.min.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/jquery-ui.min.css"> 
		
		<script src="${pageContext.request.contextPath}/Static/js/jQuery.js"></script>
        <script src="${pageContext.request.contextPath}/Static/js/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/Static/js/jquery.responsive.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/Static/js/bowser.min.js"></script>
        <script src="${pageContext.request.contextPath}/Static/js/bxslider.min.js"></script>
        <script src="${pageContext.request.contextPath}/Static/js/jquery-ui.min.js"></script>
        
		
		<!-- application css & js -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Static/css/style.css?version=${build_number}">
        
        <script src="${pageContext.request.contextPath}/Static/js/form.js?version=${build_number}"></script>
        <script src="${pageContext.request.contextPath}/Static/js/intiFormParams.js?version=${build_number}"></script>
        <script src="${pageContext.request.contextPath}/Static/js/localization.js?version=${build_number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/tableMaker.js?version=${build_number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/map.js?version=${build_number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/dialogbox.js?version=${build_number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/script.js?version=${build_number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/productList.js?version=${build_number}"></script>
		<script src="${pageContext.request.contextPath}/Static/js/testTrading.js?version=${build_number}"></script>
		
	</head>
	<body>
		<div id="form-container"></div>
			<div class="wrapper">
			    <div class="content">
			   		<header>
						<div class="container">
							<img class="logo" src="/Static/img/logo.png" alt="YourTrader" title="YourTrader">
							<nav class="main-page-nav">
								<!-- <a href="#ex1">${label.index.about_your_trader}</a>-->
								<a href="#ex2">${label.index.benefits}</a>
								<a href="#ex3">${label.index.products}</a>
								<a href="#ex4">${label.index.for_whom}</a>
								<a href="#ex5">${label.index.try_it_now}</a>
							</nav>
							<div class="buttons-login">
								<button class="registration background-blue registration-button">
									${label.navigation.regestration}
								</button>
								<button class="sign-in login-button">
									${label.navigation.login}
		                		</button>
							</div>
							<div class="languages">
								<select id="languages-select" name="lang" class="language-switcher">
									<c:forEach var="lang" items="${languages}">
										<option data-class="${lang}" value="${lang}">${label.lang.get(lang)}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</header>
					<main>
						<div class="front" id="ex1">
							<div class="container">
								<span class="span_h1">${label.index.description_title}</span>
								<h1 class="front-desc">${label.index.description}</h1>
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
									<p class="adv-title">${label.index.convenient_title}</p>
									<p class="adv-desc">${label.index.convenient_description}</p>
								</div>
							</div>
						</div>
						<div class="products" id="ex3">
							<div class="container">
								<span class="span_h3">${label.index.products}</span>
								 <div class="product-cont unauthorised slider"></div>  
							</div>
						</div>
						<div class="for-whom" id="ex4">
							<div class="for-farmers">
								<div class="for-farm-cont">
									<span class="span_h3">${label.index.for_farmers}</span>
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
									<span class="span_h3">${label.index.for_buyers}</span>
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
						<div class="how-it-works" id="">
							<div class="container">
								<div>
									<span class="span_h3">How it works</span>
									<div></div>
								</div>
								<div>
									<div>
										<table>
											<tbody>
												<tr>
													<td><label>Product</label></td>
													<td colspan="2">										
														<select>
															<option selected>Apple</option>
															<option>Potato</option>
														</select>
													</td>
												</tr> 
											</tbody>
										</table>
									</div>
									<div>
										<table>
											<thead>
												<tr>
												    <td></td>
												    <td>Price zl/kg</td>
												    <td>Volume, kg</td> 
											    </tr> 
											</thead>
											<tbody>
												<tr>
												    <td>Farmer</td>
												    <td><input type="text" value="30"></td>
												    <td><input type="text" value="4000"></td> 
											    </tr> 
											    <tr>
												    <td>Buyer</td>
												    <td><input type="text" value="40"></td>
												    <td><input type="text" value="3000"></td> 
											    </tr> 
											    <tr>
												    <td></td>
												    <td colspan="2"><button class="background-blue">Find deal</button></td> 
											    </tr> 
											</tbody>
										</table>
						
									</div>
									<div>
										<table>
											<tbody>
												<tr>
													<td>Deal</td>
													<td><input type="text" value="500" readonly></td>
													<td><input type="text" value="3500" readonly></td> 
												</tr> 
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
						<!--  <div class="test-test-trading" id="">
							<div class="container">
								<span class="span_h3">${label.index.how_it_works}</span>
							</div>
							<div class="cont-test-trading">
								<div class="container">
									<div class="test-trading-farmer">
										<div></div>
										<div>
											<div>
												<p>Min price /kg</p>
												<p>30,00 zl</p>
											</div>
											<div>
												<p>Volume, kg</p>
												<p>4000</p>
											</div>
											<div>
												<p>Farmer address</p>
												<p>Poland, Warsaw</p>
											</div>
										</div>
									</div>
									<div class="test-trading-result">
										<div>
											<form action="">
												<div>
													<label>Product</label>
													<select>
														<option selected>Apple</option>
														<option>Potato</option>
													</select>
												</div>
												<div>
													<div>
														<p>Deal price /kg</p>
														<p>35,00 zl</p>
													</div>
													<div>
														<p>Deal volume, kg</p>
														<p>3000</p>
													</div>
												</div>
												<button class="background-blue">${label.index.find_deal}</button>
											</form>
										</div>
									</div>
									<div class="test-trading-buyer">
										<div>
											<div>
												<p>Min price /kg</p>
												<p>40,00 zl</p>
											</div>
											<div>
												<p>Volume, kg</p>
												<p>3000</p>
											</div>
											<div>
												<p>Farmer address</p>
												<p>Poland, Kraców</p>
											</div>
										</div>
										<div></div>
									</div>
								</div>
							</div>
						</div> -->
						<div class="about-us" id="">
							<div class="container">
								<div>
									<span class="span_h3">${label.index.about_us_title}</span>
									<p>${label.index.about_us_text_one}</p>
									<p>${label.index.about_us_text_two}</p>
								</div>
								<div></div>
							</div>
						</div>
						<div class="question" id="">
							<div class="container">
								<div>
									<span class="span_h3">${label.index.frequently_asked_question}</span>
									<p class="answer">${label.index.not_found_answer}</p>
									<p class="mail"><a href="">stock@info.com</a></p>
								</div>
								<div>
									<div class="question-answer">
										<div>
											<div class="header-question">
												${label.index.header_question_one}
												<div class="question-close"></div>
											</div>
											<div class="body-answer">
												${label.index.body_answer_one}
											</div>
										</div>
									</div>
									<div class="question-answer">
										<div>
											<div class="header-question">
												${label.index.header_question_two}
												<div class="question-close"></div>
											</div>
											<div class="body-answer">
												${label.index.body_answer_two}
											</div>
										</div>	
									</div>
									<div class="question-answer">
										<div>
											<div class="header-question">
												${label.index.header_question_three}
												<div class="question-close"></div>
											</div>
											<div class="body-answer">
												${label.index.body_answer_three}
											</div>
										</div>	
									</div>
									<div class="question-answer">
										<div>
											<div class="header-question">
												${label.index.header_question_four}
												<div class="question-close"></div>
											</div>
											<div class="body-answer">
												${label.index.body_answer_four}
											</div>
										</div>	
									</div>
								</div>
							</div>
						</div>
						<!--   <div class="test-trading" id="ex5">
							<div class="container">
								<span class="span_h3">${label.index.try_it_now}</span>
								<div class="test-lot-tender-container">
									<div class="test-lot">
										<button class="add-test-lot background-blue">${label.button.new_lot}</button>
										<div class="table-container">
										    <table id="main-table-lot" class="test-lot-table" data-url="/lot/json/TestList.spr" data-add-button="add-test-lot"></table>
										</div>
									</div>
									<div class="test-tender">
										<button class="add-test-tender background-blue">${label.button.new_tender}</button>
										<div class="table-container">
										    <table id="main-table-tender" class="test-tender-table" data-url="/tender/json/TestList.spr" data-add-button="add-test-tender"></table>
										</div>
									</div>
								</div>
								<div class="test-trade-container">
									<div class="table-container">
									    <table id="main-table-deal" data-url="/deal/json/TestDeals.spr"></table>
									</div>
									<button class="test-trade background-green">${label.button.trade}</button>
									<button class="test-clear gray-button">${label.button.clear}</button>
								</div>
							</div>
						</div> -->
						<div class="preview-platform">
							<div class="container">
								<div>
									<span class="span_h3">${label.index.responsive_title}</span>
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
				 	<div class="button_up"></div>
				</footer>
			</div>
	</body>
</html>