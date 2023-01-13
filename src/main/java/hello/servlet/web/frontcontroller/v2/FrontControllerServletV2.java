package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.myView;
import hello.servlet.web.frontcontroller.v1.ControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/frontcontroller/v2/*")
// *의 의미 : v1/ 하위에 어떤  url이 들어와도 일단 이 서블릿이 무조건 호출된다.
public class FrontControllerServletV2 extends HttpServlet {
    private Map<String, ControllerV2> controllerMap = new HashMap<>();
    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2()); //map의 키값 지정
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI(); //uri부분을 그대로 받을 수 있다 그게 곧 controller의 name과 일치할 것이다.
        ControllerV2 controller = controllerMap.get(requestURI); //controller 키값 가져오기
        if (controller == null) { //url가 없을시 예외처리
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404에러 출력
            return;
        }
        myView view = controller.process(request, response);
        view.render(request, response);
    }
}
