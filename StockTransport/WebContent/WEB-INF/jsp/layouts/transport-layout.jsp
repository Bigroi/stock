<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang ="ru">
	<head>
		<title>TransportPage</title>
		<meta charset ="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href ="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css">
        
        <link rel="stylesheet" href ="/StockTransport/css/styles.css">
        
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/responsive/2.2.1/js/dataTables.responsive.min.js"></script>
        <script>
            $(document).ready(function(){
                $(".burger-container").click(function(){
                    $("nav").toggleClass("nav-mobile");/* show-hide ????? */
                    $("#burger").toggleClass("burger").toggleClass("x-burger");
                    $("#mask").toggleClass("mask");
                });
                $("#mask").click(function(){
                    $("nav").removeClass("nav-mobile");
                    $("#mask").removeClass("mask");
                    $("#burger").removeClass("x-burger");
                    $("#burger").addClass("burger");
                });
               /*  $("#main-table").DataTable({
                    responsive: true
                }); */
            });
        </script>
    </head>
	<body>
        <aside>
            <div class="logo">
                <div>Info<br>Trans</div>
            </div>
            <nav>
            	<tiles:insertAttribute name="navigation" />
            </nav>
            <div class="burger-container">
                <div id="burger" class="burger"></div>
            </div>
        </aside>
        <main>
            <div class="login">
				<a class="reg" href="registration.spr">Registration</a>
                <a class="log" href ="login.spr">Login</a>
            </div >
	        <!-- <div id="table-container">
	            <div class="table-header">
	                <h1>Test!!!!</h1>
	            </div>
	            <table id="main-table" data-url="/deal/json/MyDeals.spr">
	               
	            </table>
            </div> -->
            <div class="content">
				<tiles:insertAttribute name="body" />
			</div>
            <div id="mask"></div>
		</main>
    </body>
</html>