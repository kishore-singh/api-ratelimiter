<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	blueoptima Rate Limiter!  
</h1>

<P> <br> 1. To get Token call below API [POST] </br>
<P>	/getSecurityToken
<P> sample request body
<P>	{
<P>	"username":"user",
<P>	"password":"password"
<P>	}
<br>
<br>
<P>2.  To get git user profile details call below API [POST]
<P>	/getGitHubProfileDetails
<p>*Need to pass userToken from API 1 in headers for auth, otherwise user will be treated as guest user and so limit will be as per guest user
<p>** Limit for authrosied user is higher compartaively 	
<br>
<P>sample request body:
<p>[
<p>{
<p>"firstName":"Chunky",
<p>"lastName":"Garg",
<p>"location":"Gurgaon"
<p>},
<p>{
<p>"firstName":"Rahul",
<p>"lastName":"Gaur",
<p>"location":"India"
<p>}
<p>]
</body>
</html>
