<%--
  Created by IntelliJ IDEA.
  User: v0s004a
  Date: 8/4/19
  Time: 1:57 PM
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
    <title>Session Fixtures</title>
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
        .collapsible {
            background-color: #777;
            color: white;
            cursor: pointer;
            padding: 18px;
            width: 100%;
            border: none;
            text-align: left;
            outline: none;
            font-size: 15px;
        }

        .active, .collapsible:hover {
            background-color: #555;
        }

        .content {
            padding: 0 18px;
            display: none;
            overflow: hidden;
            background-color: #f1f1f1;
        }
    </style>
</head>

<body class="w3-light-grey">

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
            <%@include file="navigation/admin.jsp" %>
        </c:if>
        <c:if test="${empty session}">
            <%@include file="navigation/bar.jsp" %>
        </c:if>
    </div>
</nav>
<!-- Overlay effect when opening sidebar on small screens -->
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer" title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">
    <br />

    <button class="collapsible">Open Collapsible</button>
    <div class="content">
        <p><h1 style="color: #1F2739"><span class="#1F2739">Qualifiers</span></h1>
            <table class="container">
                <thead>
                <tr>
                    <th align = "center"><h1 style="color: skyblue; ">Pool ${sessionFixtures.key}</h1></th>
                    <th align = "center"><h1 style="color: skyblue; ">Time</h1></th>
                    <th align = "center"><h1 style="color: skyblue; ">Team 1</h1></th>
                    <th align = "center"><h1 style="color: skyblue; ">Score</h1></th>
                    <th align = "center"><h1 style="color: skyblue; ">Team 2</h1></th>
                    <th align = "center"><h1 style="color: skyblue; ">Referee</h1></th>
                    <c:if test="${not empty session}">
                        <th align = "center"><h1 style="color: skyblue; ">Match Key</h1></th>
                    </c:if>
                </tr>
                </thead>
                <c:forEach items="${sessionFixtures.}" var="fixtures">
                <tbody>
                <tr>
                    <td align = "center" style="color: ghostwhite; ">${fixtures.matchdate}</td>
                    <td align = "center" style="color: ghostwhite; ">${fixtures.time}</td>
                    <td align = "center" style="color: ghostwhite; ">${fixtures.team1}</td>
                    <c:if test="${not empty fixtures.viewStats}">
                        <td align = "center" style="color: ghostwhite; "><a style="text-decoration: none;" href="${fixtures.viewStats}" target="_blank">${fixtures.result}</a></td>
                    </c:if>
                    <c:if test="${empty fixtures.viewStats}">
                        <td align="center" style="color: ghostwhite; ">${fixtures.result}</td>
                    </c:if>
                    <td align = "center" style="color: ghostwhite;">${fixtures.team2}</td>
                    <td align = "center" style="color: ghostwhite;" >${fixtures.referee}</td>
                    <c:if test="${not empty session}">
                        <td align = "center" style="color: ghostwhite;">${fixtures.matchKey}</td>
                    </c:if>
                </tr>
                </c:forEach>
                </tbody>
            </table>
            <br/>
        </p>
    </div>



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

    var coll = document.getElementsByClassName("collapsible");
    var i;

    for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function() {
            this.classList.toggle("active");
            var content = this.nextElementSibling;
            if (content.style.display === "block") {
                content.style.display = "none";
            } else {
                content.style.display = "block";
            }
        });
    }

</script>

</body>
</html>

