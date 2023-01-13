package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/frontcontroller/v1/*")
// *의 의미 : v1/ 하위에 어떤  url이 들어와도 일단 이 서블릿이 무조건 호출된다.
public class FrontControllerServletV1 extends HttpServlet {
    private Map<String, ControllerV1> controllerMap = new HashMap<>();
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1()); //map의 키값 지정
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");
        String requestURI = request.getRequestURI(); //uri부분을 그대로 받을 수 있다 그게 곧 controller의 name과 일치할 것이다.
        ControllerV1 controller = controllerMap.get(requestURI); //controller 키값 가져오기
        if (controller == null) { //url가 없을시 예외처리
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404에러 출력
            return;
        }
        controller.process(request, response);
    }
}
