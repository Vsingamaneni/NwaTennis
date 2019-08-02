<%--
  Created by IntelliJ IDEA.
  User: v0s004a
  Date: 7/18/19
  Time: 11:09 PM
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
    <title>Fixtures & Results</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="icon" type="image/png" href="/resources/login/images/icons/tennis.ico"/>
    <link rel="stylesheet" href="/resources/core/css/fixtures.css"/>
    <style>
        html,body,h1,h2,h3,h4,h5 {font-family: "Raleway", sans-serif}
        a href{text-decoration: none;}
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

    <h1 style="text-align: center;"><span class="#1F2739">Fixtures & Results</span></h1>
    <br /><br />

    <c:if test="${not empty finals}">
        <h1 style="text-align: center;"><span class="#1F2739">Grand Finale</span></h1>
        <table style="margin: 0 auto; font-size: 20px;">
            <tr>
                <th>Date</th>
                <th>Team 1</th>
                <th></th>
                <th>Team 2</th>
                <th>Time</th>
                <c:if test="${not empty session}">
                    <th>Match Key</th>
                </c:if>
            </tr>
            <tr>
                <td>${finals.matchdate}</td>
                <td>${finals.team1}</td>
                <c:if test="${not empty finals.homeTeamScore1}">
                    <td>${finals.homeTeamScore1} - ${finals.awayTeamScore1} <br/>
                            ${finals.homeTeamScore2} - ${finals.awayTeamScore2} <br/>
                            ${finals.homeTeamScore3} - ${finals.awayTeamScore3} <br/>
                    </td>
                </c:if>
                <c:if test="${empty finals.homeTeamScore1}">
                    <td>vs</td>
                </c:if>
                <td>${finals.team2}</td>
                <td>${finals.time}</td>
                <c:if test="${not empty session}">
                    <td>${finals.matchKey}</td>
                </c:if>
            </tr>
        </table>
        <br />
    </c:if>

    <c:if test="${not empty semisFixtures}">
        <h1 style="text-align: center;"><span class="#1F2739">Semi Finals</span></h1>
        <table style="margin: 0 auto; font-size: 20px;">
            <tr>
                <th>Date</th>
                <th>Team 1</th>
                <th></th>
                <th>Team 2</th>
                <th>Time</th>
                <c:if test="${not empty session}">
                    <th>Match Key</th>
                </c:if>
            </tr>
            <c:forEach items="${semisFixtures}" var="finals">
                <tr>
                    <td>${finals.matchdate}</td>
                    <td>${finals.team1}</td>
                    <c:if test="${not empty finals.homeTeamScore1}">
                        <td>${finals.homeTeamScore1} - ${finals.awayTeamScore1} <br/>
                                ${finals.homeTeamScore2} - ${finals.awayTeamScore2} <br/>
                                ${finals.homeTeamScore3} - ${finals.awayTeamScore3} <br/>
                        </td>
                    </c:if>
                    <c:if test="${empty finals.homeTeamScore1}">
                        <td>vs</td>
                    </c:if>
                    <td>${finals.team2}</td>
                    <td>${finals.time}</td>
                    <c:if test="${not empty session}">
                        <td>${finals.matchKey}</td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
        <br />
    </c:if>

    <c:if test="${not empty quartersFixtures}">
        <h1 style="text-align: center;"><span class="#1F2739">Quarter Finals</span></h1>
        <table style="margin: 0 auto; font-size: 20px;">
            <tr>
                <th>Date</th>
                <th>Team 1</th>
                <th></th>
                <th>Team 2</th>
                <th>Time</th>
                <c:if test="${not empty session}">
                    <th>Match Key</th>
                </c:if>
            </tr>
            <c:forEach items="${quartersFixtures}" var="finals">
                <tr>
                    <td>${finals.matchdate}</td>
                    <td>${finals.team1}</td>
                    <c:if test="${not empty finals.homeTeamScore1}">
                        <td>${finals.homeTeamScore1} - ${finals.awayTeamScore1} <br/>
                                ${finals.homeTeamScore2} - ${finals.awayTeamScore2} <br/>
                                ${finals.homeTeamScore3} - ${finals.awayTeamScore3} <br/>
                        </td>
                    </c:if>
                    <c:if test="${empty finals.homeTeamScore1}">
                        <td>vs</td>
                    </c:if>
                    <td>${finals.team2}</td>
                    <td>${finals.time}</td>
                    <c:if test="${not empty session}">
                        <td>${finals.matchKey}</td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
        <br />
    </c:if>

    <c:if test="${not empty roundOfSixteen}">
        <h1 style="text-align: center;"><span class="#1F2739">Round Of 16</span></h1>
        <c:forEach items="${roundOfSixteen}" var="fixturesList">

            <table style="margin: 0 auto; font-size: 20px;">
                <tr>
                    <th>Pool ${fixturesList.key}</th>
                    <th>Team 1</th>
                    <th></th>
                    <th>Team 2</th>
                    <th>Time</th>
                    <c:if test="${not empty session}">
                        <th>Match Key</th>
                    </c:if>
                </tr>
                <c:forEach items="${fixturesList.value}" var="fixtures">
                    <tr>
                        <td>${fixtures.matchdate}</td>
                        <td>${fixtures.team1}</td>
                        <c:if test="${not empty fixtures.homeTeamScore1}">
                            <td>${fixtures.homeTeamScore1} - ${fixtures.awayTeamScore1} <br />
                                    ${fixtures.homeTeamScore2} - ${fixtures.awayTeamScore2} <br />
                                    ${fixtures.homeTeamScore3} - ${fixtures.awayTeamScore3} </td>
                        </c:if>
                        <c:if test="${empty fixtures.homeTeamScore1}">
                            <td>vs</td>
                        </c:if>
                        <td>${fixtures.team2}</td>
                        <td>${fixtures.time}</td>
                        <c:if test="${not empty session}">
                            <td>${fixtures.matchKey}</td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
            <br />
        </c:forEach>
        <br />
    </c:if>

    <h1 style="text-align: center;"><span class="#1F2739">Group Stages</span></h1>
    <c:forEach items="${fixturesList}" var="fixturesList">
    <table style="margin: 0 auto; font-size: 20px;">
        <tr>
            <th>Pool ${fixturesList.key}</th>
            <th>Time</th>
            <th>Team 1</th>
            <th></th>
            <th>Team 2</th>
            <th>Referee</th>
            <c:if test="${not empty session}">
                <th>Match Key</th>
            </c:if>
        </tr>
        <c:forEach items="${fixturesList.value}" var="fixtures">
        <tr>
            <td>${fixtures.matchdate}</td>
            <td>${fixtures.time}</td>
            <td>${fixtures.team1}</td>
            <c:if test="${not empty fixtures.homeTeamScore}">
                <td>${fixtures.homeTeamScore} - ${fixtures.awayTeamScore}</td>
            </c:if>
            <c:if test="${empty fixtures.homeTeamScore}">
                <td>vs</td>
            </c:if>
            <td>${fixtures.team2}</td>
            <td>${fixtures.referee}</td>
            <c:if test="${not empty session}">
                <td>${fixtures.matchKey}</td>
            </c:if>
        </tr>
        </c:forEach>
    </table>
        <br /><br />
    </c:forEach>

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
</script>

</body>
</html>