<%--
  Created by IntelliJ IDEA.
  User: v0s004a
  Date: 8/2/19
  Time: 7:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>R16 Mens Standings</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="icon" type="image/png" href="/resources/login/images/icons/tennis.ico"/>
    <link rel="stylesheet" href="/resources/core/css/standings.css"/>
    <style>
        html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
        a href{text-decoration: none;}
    </style>
</head>

<body class="w3-light-grey" style="font-family: 'Open Sans', sans-serif;">

<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
    <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i class="fa fa-bars"></i> &nbsp;Menu</button>
    <span class="w3-bar-item w3-right">NWA Tennis</span>
</div>

<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
    <div class="w3-container w3-row">
        <div class="w3-col s4">
            <img src="/resources/core/images/avatar2.png" class="w3-circle w3-margin-right" style="width:46px">
        </div>
        <div class="w3-col s8 w3-bar">
            <span>Welcome, <strong>Player</strong></span><br>
            <a href="/" class="w3-bar-item w3-button"><i class="fa fa-home"></i></a>
            <a href="/fixtures/mens" style="text-decoration : none;" class="w3-bar-item w3-button w3-padding"><i class="fa fa-clock-o"></i></a>
            <c:if test="${not empty session}">
                <a href="/logout" class="w3-bar-item w3-button w3-padding"><i class="fa fa-power-off"></i></a>
            </c:if>
            <c:if test="${empty session}">
                <a href="/login" class="w3-bar-item w3-button w3-padding"><i class="fa fa-child"></i></a>
            </c:if>
        </div>
    </div>
    <hr>
    <div class="w3-container">
        <h5>Dashboard</h5>
    </div>
    <div class="w3-bar-block">
        <a href="#" class="w3-bar-item w3-button w3-padding-16 w3-hide-large w3-dark-grey w3-hover-black"
           onclick="w3_close()" title="close menu"><i class="fa fa-remove fa-fw"></i>&nbsp; Close Menu</a>
        <c:if test="${not empty session}">
            <%@include file="../navigation/admin.jsp" %>
        </c:if>
        <c:if test="${empty session}">
            <%@include file="../navigation/bar.jsp" %>
        </c:if>
    </div>
</nav>
<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">
    <br />

    <h1 style="text-align: center; color:black;"><span class="#1F2739">Top 16</span></h1>
    <br /><br />

    <c:if test="${ not empty topHalf}">

    <table class="container">
        <thead>
        <tr>
            <th><h1 style="color: skyblue; text-align: center;">Rank</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Team</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Played</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Won</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Lost</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Points</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Margin</h1></th>
        </tr>
        </thead>
        <c:forEach items="${topHalf}" var="standings">
        <tbody>
        <tr>
            <td style="color: ghostwhite;">${standings.rank}</td>
            <td style="color: ghostwhite;">${standings.team}</td>
            <td style="color: ghostwhite;">${standings.played}</td>
            <td style="color: ghostwhite;">${standings.won}</td>
            <td style="color: ghostwhite;">${standings.lost}</td>
            <td style="color: ghostwhite;">${standings.points}</td>
            <td style="color: ghostwhite;">${standings.margin}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    </c:if>

    <c:if test="${ not empty bottomHalf}">

    <table class="container">
        <thead>
        <tr>
            <th><h1 style="color: skyblue; text-align: center;">Rank</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Team</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Played</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Won</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Lost</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Points</h1></th>
            <th><h1 style="color: skyblue; text-align: center;">Margin</h1></th>
        </tr>
        </thead>
        <c:forEach items="${bottomHalf}" var="standings">
        <tbody>
        <tr>
            <td style="color: ghostwhite;">${standings.rank}</td>
            <td style="color: ghostwhite;">${standings.team}</td>
            <td style="color: ghostwhite;">${standings.played}</td>
            <td style="color: ghostwhite;">${standings.won}</td>
            <td style="color: ghostwhite;">${standings.lost}</td>
            <td style="color: ghostwhite;">${standings.points}</td>
            <td style="color: ghostwhite;">${standings.margin}</td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    <br/>
    </c:if>

</body>

<br />

<!-- Footer -->
<footer class="w3-container w3-padding-16 w3-light-grey">
    <p style="text-align: center;">&copy; All rights Reserved @<b>Vamsi Singamaneni & NWA Tennis Core Team</b></p>
</footer>

<!-- End page content -->
</div>

<script>
    // Get the Sidebar
    var mySidebar = document.getElementById("mySidebar");

    // Get the DIV with overlay effect
    var overlayBg = document.getElementById("myOverlay");

    // Toggle between showing and hiding the sidebar, and add overlay effect
    function w3_open() {
        if (mySidebar.style.display === 'block') {
            mySidebar.style.display = 'none';
            overlayBg.style.display = "none";
        } else {
            mySidebar.style.display = 'block';
            overlayBg.style.display = "block";
        }
    }

    // Close the sidebar with the close button
    function w3_close() {
        mySidebar.style.display = "none";
        overlayBg.style.display = "none";
    }
</script>

</body>
</html>
