<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="captcha" uri="tld/captcha.tld" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myTag" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="ToolShopWeb"/>
<!DOCTYPE html>
<html>
<head>
    <title>Tool Shop | Register</title>
    <!-- for-mobile-apps -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="application/x-javascript">
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }
    </script>
    <!-- //for-mobile-apps -->
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="css/style.css" rel="stylesheet" type="text/css" media="all"/>
    <!-- js -->
    <script src="js/jquery.min.js"></script>
    <!-- //js -->
    <!-- validate -->
    <script type="text/javascript" src="js/validate.js"></script>
    <script type="text/javascript" src="js/captcha.js"></script>
    <!-- cart -->
    <script src="js/cart.js"></script>
    <!-- cart -->
    <!-- for bootstrap working -->
    <script type="text/javascript" src="js/bootstrap-3.1.1.min.js"></script>
    <!-- //for bootstrap working -->
    <!-- animation-effect -->
    <link href="css/animate.min.css" rel="stylesheet">
    <script src="js/wow.min.js"></script>
    <script>
        new WOW().init();
    </script>
    <!-- //animation-effect -->
    <link href='//fonts.googleapis.com/css?family=Cabin:400,500,600,700' rel='stylesheet' type='text/css'>
    <link href='//fonts.googleapis.com/css?family=Lato:400,100,300,700,900' rel='stylesheet' type='text/css'>
</head>
<body>
<!-- header -->
<div class="header">
    <div class="header-grid">
        <div class="container">
            <div class="header-left animated wow fadeInLeft" data-wow-delay=".5s">
                <myTag:login/>
            </div>
            <myTag:switch_locale/>
            <div class="header-right animated wow fadeInRight" data-wow-delay=".5s">
                <div class="header-right1 ">
                    <ul>
                        <li><i class="glyphicon glyphicon-book"></i><a href="/viewRegisterForm">
                            <fmt:message key="register"/></a></li>
                    </ul>
                </div>
                <myTag:cart/>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="container">
        <div class="logo-nav">
            <nav class="navbar navbar-default">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header nav_2">
                    <button type="button" class="navbar-toggle collapsed navbar-toggle1" data-toggle="collapse"
                            data-target="#bs-megadropdown-tabs">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <div class="navbar-brand logo-nav-left wow fadeInLeft animated" data-wow-delay=".5s">
                        <h1 class="animated wow pulse" data-wow-delay=".5s"><a
                                href="/viewHomePage">Tool<span>Shop</span></a></h1>
                    </div>
                </div>
                <div class="collapse navbar-collapse" id="bs-megadropdown-tabs">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="/viewHomePage" class="act"><fmt:message key="home"/></a></li>
                        <!-- Mega Menu -->
                        <li class="dropdown">
                            <a href="/viewTools"><fmt:message key="tools"/></a>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
    </div>
</div>
<!-- //header -->
<!--banner-->
<div class="banner-top">
    <div class="container">
        <h2 class="animated wow fadeInLeft" data-wow-delay=".5s"><fmt:message key="register"/></h2>
        <h3 class="animated wow fadeInRight" data-wow-delay=".5s">
            <a href="/viewHomePage"><fmt:message key="home"/></a><label>/</label>
            <fmt:message key="register"/>
        </h3>
        <div class="clearfix"></div>
    </div>
</div>
<!-- contact -->
<div class="login">
    <div class="container" id="container">
        <c:if test="${not empty requestScope.successRegistration}">
            <div class="alert alert-success" role="alert">
                <strong>Well done!</strong>${requestScope.successRegistration}
            </div>
        </c:if>
        <c:if test="${not empty requestScope.errors}">
            <div class="alert alert-danger" role="alert">
                <strong>Oops! </strong>Something went wrong :( Please fix and try again.<br><br>
                <c:forEach items="${requestScope.errors}" var="entry">
                    <strong> ${entry.key}</strong><br>${entry.value}<br>
                </c:forEach>
            </div>
        </c:if>
        <form name="registerForm" action="/registerUser" method="post" enctype="multipart/form-data"
              onsubmit="return validateRegisterForm('registerForm')">
            <div class="col-md-6 login-do1 animated wow fadeInLeft" data-wow-delay=".5s">
                <div class="login-mail">
                    <input type="text" name="fullName" placeholder="<fmt:message key="full_name"/>"
                           value="${requestScope.regEntity.fullName}" required="">
                    <img src="images/ID.png" alt=""/>
                </div>
                <div class="login-mail">
                    <input type="text" name="address" placeholder="<fmt:message key="address"/>"
                           value="${requestScope.regEntity.address}"
                           required="">
                    <i class="glyphicon glyphicon-map-marker"></i>
                </div>
                <div class="login-mail">
                    <input type="text" name="phoneNumber" placeholder="+X-XXX-XXX-XXXX"
                           value="${requestScope.regEntity.phoneNumber}" required="">
                    <i class="glyphicon glyphicon-earphone"></i>
                </div>
                <div class="login-mail">
                    <input type="text" name="email" placeholder="<fmt:message key="email"/>"
                           value="${requestScope.regEntity.email}"
                           required="">
                    <i class="glyphicon glyphicon-envelope"></i>
                </div>
                <div class="login-mail">
                    <input type="password" name="password" placeholder="<fmt:message key="password"/>"
                           value="${requestScope.regEntity.password}" required="">
                    <i class="glyphicon glyphicon-lock"></i>
                </div>
                <div class="login-mail">
                    <input type="password" name="passwordCheck" placeholder="<fmt:message key="repeated_pas"/>"
                           value="${requestScope.regEntity.repeatedPassword}"
                           required="">
                    <img src="images/password-check.png" alt=""/>
                </div>
                <captcha:CaptchaImage/>
                <div class="login-mail">
                    <input type="text" name="captcha" placeholder="<fmt:message key="captcha"/>" required="">
                    <img src="images/stop_robot.png" alt=""/>
                </div>
                <input type="file" name="avatar" accept="image/jpeg,image/png"/>
            </div>
            <div class="col-md-6 login-do animated wow fadeInRight" data-wow-delay=".5s">
                <label class="hvr-sweep-to-top login-sub">
                    <input type="submit" value="<fmt:message key="submit"/>">
                </label>
                <p>Already register</p>
                <a href="/viewLoginForm" class="hvr-sweep-to-top"><fmt:message key="login"/></a>
            </div>
            <div class="clearfix"></div>
        </form>
    </div>
</div>
<!-- footer -->
<div class="footer">
    <div class="container">
        <div class="footer-grids">
            <div class="col-md-4 footer-grid animated wow fadeInLeft" data-wow-delay=".5s">
                <h3>About Us</h3>
                <p>Duis aute irure dolor in reprehenderit in voluptate velit esse.<span>Excepteur sint occaecat cupidatat
						non proident, sunt in culpa qui officia deserunt mollit.</span></p>
            </div>
            <div class="col-md-4 footer-grid animated wow fadeInLeft" data-wow-delay=".6s">
                <h3>Contact Info</h3>
                <ul>
                    <li><i class="glyphicon glyphicon-map-marker"></i>1234k Avenue, 4th block,
                        <span>New York City.</span></li>
                    <li class="foot-mid"><i class="glyphicon glyphicon-envelope"></i><a href="mailto:info@example.com">info@example.com</a>
                    </li>
                    <li><i class="glyphicon glyphicon-earphone"></i>+1234 567 567</li>
                </ul>
            </div>
            <div class="col-md-4 footer-grid animated wow fadeInLeft" data-wow-delay=".7s">
                <h3>Sign up for newsletter </h3>
                <form>
                    <input type="text" placeholder="Email" required="">
                    <input type="submit" value="Submit">
                </form>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="copy-right animated wow fadeInUp" data-wow-delay=".5s">
            <p>&copy 2017 Tool Shop. All rights reserved | Design by <a href="http://w3layouts.com/">W3layouts</a></p>
        </div>
    </div>
</div>
<!-- //footer -->
</body>
</html>