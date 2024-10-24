package shopping;

import java.util.*;
import java.io.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shopping.CD;
public class ShoppingServlet extends HttpServlet {
  public void init(ServletConfig conf) throws ServletException  {
    super.init(conf);
  }
  public void doPost (HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session == null) {
      res.sendRedirect("http://localhost:8080/error.html");
    }
    Vector<CD> buylist=
      (Vector<CD>)(session).getAttribute("shopping.shoppingcart");
    String action = req.getParameter("action");
    if (!action.equals("CHECKOUT")) {
      if (action.equals("DELETE")) {
        String del = req.getParameter("delindex");
        int d = (new Integer(del)).intValue();
        buylist.removeElementAt(d);
      } 
      else if (action.equals("ADD")) {
        //any previous buys of same cd?
        boolean match=false;
        CD aCD = getCD(req);
        if (buylist==null) {
          //add first cd to the cart
          buylist = new Vector(); //first order
          buylist.addElement(aCD);
        } else { // not first buy
          for (int i=0; i< buylist.size(); i++) {
            CD cd = (CD) buylist.elementAt(i);
            if (cd.getAlbum().equals(aCD.getAlbum())) {
              cd.setQuantity(cd.getQuantity()+aCD.getQuantity());
              buylist.setElementAt(cd,i);
              match = true;
            } //end of if name matches
          } // end of for
          if (!match) 
            buylist.addElement(aCD);
        }
      }
      session.setAttribute("shopping.shoppingcart", buylist);
      String url="/EShop.jsp";
      //ServletContext sc = getServletContext();
      //RequestDispatcher rd = sc.getRequestDispatcher(url);
      //rd.forward(req, res);
      res.sendRedirect("EShop.jsp");
      } 
    
    else if (action.equals("CHECKOUT"))  {
      float total =0;
      for (int i=0; i< buylist.size();i++) {
        CD anOrder = (CD) buylist.elementAt(i);
        float price= anOrder.getPrice();
        int qty = anOrder.getQuantity();
        total += (price * qty);
      }
      total += 0.005;
      String amount = new Float(total).toString();
      int n = amount.indexOf('.');
      amount = amount.substring(0,n+3);
      session.setAttribute("amount",amount);
      String url="/Checkout.jsp";
      //ServletContext sc = getServletContext();
      //RequestDispatcher rd = sc.getRequestDispatcher(url);
      //rd.forward(req, res);
      
     res.sendRedirect("Checkout.jsp");
      }
  }

private CD getCD(HttpServletRequest req) {
    String myCd = req.getParameter("CD");
    String qty = req.getParameter("qty");
    StringTokenizer t = new StringTokenizer(myCd,"|");
    String album= t.nextToken();
    String artist = t.nextToken();
    String country = t.nextToken();
    String price = t.nextToken();
    price = price.replace('$',' ').trim();
    CD cd = new CD();
    cd.setAlbum(album);
    cd.setArtist(artist);
    cd.setCountry(country);
    cd.setPrice((new Float(price)).floatValue());
    cd.setQuantity((new Integer(qty)).intValue());
    return cd;
  }
}
