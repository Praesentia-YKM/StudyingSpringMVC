package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "helloServlet", urlPatterns = "/hello") //이름은 아무거나 줘도됨 단순히 여기선 클래스이름이랑 비슷하게줌
public class HelloServlet extends HttpServlet {
    //ctrl + o로 protected service불러옴

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse
            response)
            throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);  //soutv단축키
        System.out.println("response = " + response);

        String username = request.getParameter("username"); //username을 get방식으로 요청받는다.
        System.out.println("username = " + username);
        
        response.setContentType("text/plain"); //http 응답메시지에 데이터가 담겨서 나감
        response.setCharacterEncoding("utf-8");
        response.getWriter().write("hello " + username);
        // soutm 단축키 : system.out.println("hello 클래스명.메서드명 해줌 " ):

    }
}