<%--
  Created by IntelliJ IDEA.
  User: v0s004a
  Date: 7/30/19
  Time: 12:03 AM
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
    <title>Knockout Result Update</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" type="image/x-icon" href="/resources/login/images/icons/tennis.ico"/>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.0/css/all.css">
    <link rel="stylesheet" href="/resources/core/css/table.css"/>
    <link rel="stylesheet" href="/resources/core/css/format_css.css"/>
    <style>
        html, body, h1, h2, h3, h4, h5 {
            font-family: "Raleway", sans-serif
        }
        input[type=text] {
            padding: 0;
            height: 30px;
            position: relative;
            left: 0;
            outline: none;
            border: 1px solid #cdcdcd;
            border-color: rgba(0, 0, 0, .15);
            background-color: white;
            font-size: 16px;
        }
    </style>
</head>

<body class="w3-light-grey">

<!-- Top container -->
<div class="w3-bar w3-top w3-black w3-large" style="z-index:4">
    <button class="w3-bar-item w3-button w3-hide-large w3-hover-none w3-hover-text-light-grey" onclick="w3_open();"><i
            class="fa fa-bars"></i> &nbsp;Menu
    </button>
    <span class="w3-bar-item w3-right">NWA Tennis</span>
</div>

<!-- Sidebar/menu -->
<nav class="w3-sidebar w3-collapse w3-white w3-animate-left" style="z-index:3;width:300px;" id="mySidebar"><br>
    <div class="w3-container w3-row">
        <div class="w3-col s4">
            <img src="/resources/core/images/avatar2.png" class="w3-circle w3-margin-right" style="width:46px">
        </div>
        <div class="w3-col s8 w3-bar">
            <span>Welcome, <strong>${user_name}</strong></span><br>
            <a href="/" class="w3-bar-item w3-button"><i class="fa fa-home"></i></a>
            <a href="/schedule" style="text-decoration : none;" class="w3-bar-item w3-button w3-padding"><i class="fa fa-bell fa-fw"></i></a>
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
<div class="w3-overlay w3-hide-large w3-animate-opacity" onclick="w3_close()" style="cursor:pointer"
     title="close side menu" id="myOverlay"></div>

<!-- !PAGE CONTENT! -->
<div class="w3-main" style="margin-left:300px;margin-top:43px;">

    <div style="width: 90%; margin: 0 auto;">

        <br /><br /><br /><br />
        <div class='container'>

            <c:if test="${empty msg}">
                <div class='panel panel-primary dialog-panel'>
                    <div class='panel-heading' style="background-color: #082a3e;">
                        <h1 style="text-align: center;">Update Score</h1>
                    </div>
                    <br />
                    <div class="w3-panel">
                        <div class="w3-row-padding" style="width:100%;margin:0 auto">
                            <table class="w3-table w3-striped w3-white" style="text-align: center; align:center; align-content: center">
                                <tr style="color:black;font-size:20px;text-decoration:none;font-family:Comic Sans MS">
                                    <form modelAttribute="retrievedFixture" action="/matchResult/multiUpdate" method="POST"
                                          class='form-horizontal' role='form'>
                                <tr>
                                    <td style="text-align:center;">Teams</td>
                                    <c:if test="${not empty retrievedFixture.homeTeamScore}">
                                        <td style="text-align:center;">Current Score</td>
                                    </c:if>
                                    <td> </td>
                                    <td style="text-align:center;">Update Score</td>
                                    <td> </td>
                                    <td style="text-align:center;">Action</td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <input type=hidden id="matchNumber" name="matchNumber"
                                           value="${retrievedFixture.matchNumber}">
                                    <input type=hidden id="matchType" name="matchType" value="${retrievedFixture.matchType}">
                                    <td style="text-align:center;">${retrievedFixture.team1} <br />
                                            ${retrievedFixture.team2}</td>
                                    <c:if test="${not empty retrievedFixture.homeTeamScore1}">
                                        <td style="text-align:center;">
                                                ${retrievedFixture.homeTeamScore1} <br/>
                                                ${retrievedFixture.awayTeamScore1}
                                        </td>
                                        <td style="text-align:center;">
                                                ${retrievedFixture.homeTeamScore2} <br/>
                                                ${retrievedFixture.awayTeamScore2}
                                        </td>
                                        <td style="text-align:center;">
                                                ${retrievedFixture.homeTeamScore3} <br/>
                                                ${retrievedFixture.awayTeamScore3}
                                        </td>
                                    </c:if>
                                    <td style="text-align:center;">
                                        <input type="text" id="homeTeamScore1" name="homeTeamScore1"
                                               style="margin: 0 auto;"/> <br/>
                                        <input type="text" id="awayTeamScore1" name="awayTeamScore1"
                                               style="margin: 0 auto;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="text" id="homeTeamScore2" name="homeTeamScore2"
                                               style="margin: 0 auto;"/> <br/>
                                        <input type="text" id="awayTeamScore2" name="awayTeamScore2"
                                               style="margin: 0 auto;"/>
                                    </td>
                                    <td style="text-align:center;">
                                        <input type="text" id="homeTeamScore3" name="homeTeamScore3"
                                               style="margin: 0 auto;"/> <br/>
                                        <input type="text" id="awayTeamScore3" name="awayTeamScore3"
                                               style="margin: 0 auto;"/>
                                    </td>
                                    <td style="text-align:center;">
                                            <%--action="/matchResult/update"--%>
                                        <button class="btn-lg btn-primary" style="height: 90%; background: -webkit-linear-gradient(left, #4f5027, #1f715a, #65fa45); background: -o-linear-gradient(left, #19500f, #297150, #42fa91); background: -moz-linear-gradient(left,#4f5027, #297150, #42fa91); background: linear-gradient(left, #4f5027, #297150, #42fa91);" >Update</button>
                                        &nbsp;&nbsp;&nbsp;
                                        <button class="btn-lg btn-danger" style="height: 60%; background: -webkit-linear-gradient(left, #4f5027, #711e3b, #fa6119); background: -o-linear-gradient(left, #501c0c, #297150, #42fa91); background: -moz-linear-gradient(left,#4f5027, #297150, #42fa91); background: linear-gradient(left, #4f5027, #297150, #42fa91);" formaction="/" >Cancel</button>

                                    </td>
                                </tr>
                                </form>
                                </tr>
                            </table>
                            <br/><br/>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class='panel panel-primary dialog-panel'>
                    <div class='panel-heading' style="background-color: #082a3e;">
                        <h1 style="text-align: center;color: #af3734;">${msg}</h1>
                    </div>
                </div>
            </c:if>
        </div>

        <br>

        <!-- Footer -->
        <footer class="w3-container w3-padding-16 w3-light-grey">
            <p>&copy; All rights Reserved @<b>Vamsi Singamaneni</b></p>
        </footer>

        <!-- End page content -->
    </div>
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
