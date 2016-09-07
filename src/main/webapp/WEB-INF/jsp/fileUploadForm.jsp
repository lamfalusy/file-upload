<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
</head>

<body>
	<h2>File upload</h2>
	
	 <form action="/upload" method="post" enctype="multipart/form-data" name="form1" id="form1">
		<input name="file" type="file" id="file"/>
		<input name="foldername" type="text"/>
		<button type="submit">Submit</button>
	</form>
	
</body>
</html>