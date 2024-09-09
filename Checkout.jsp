<%@ page session="true" import="java.util.*, shopping.CD" %>
<html>
<head>
<title>Harmony Tunes Checkout</title>
</head>
<body bgcolor="#E9967A">
 <font face="Times New Roman,Times" size=+3>Harmony Tunes Checkout</font>
 <hr>
 <center>
 <table class="legacyTable" border="0" cellpadding="0" width="100%" bgcolor="#FFFFFF">
 <tr>
 <td><b>ALBUM</b></td>
 <td><b>ARTIST</b></td>
 <td><b>COUNTRY</b></td>
 <td><b>PRICE</b></td>
 <td><b>QUANTITY</b></td>
 <td></td>
 </tr>
 <%
  Vector buylist = (Vector) session.getAttribute("shopping.shoppingcart");
  String total = (String) session.getAttribute("amount");
  for (int i=0; i < buylist.size();i++) {
   CD anOrder = (CD) buylist.elementAt(i);
 %>
 <tr>
 <td><b><%= anOrder.getAlbum() %></b></td>
 <td><b><%= anOrder.getArtist() %></b></td>
 <td><b><%= anOrder.getCountry() %></b></td>
 <td><b><%= anOrder.getPrice() %></b></td>
 <td><b><%= anOrder.getQuantity() %></b></td>
 </tr>
 <%
  }
  session.invalidate();
 %>
 <tr>
 <td>     </td>
 <td>     </td>
 <td><b>TOTAL</b></td>
 <td><b>$<%=total %></b></td>
 <td>     </td>
 </tr>
 </table>
 
 Shop some more!
 </center>
</body>
</html>