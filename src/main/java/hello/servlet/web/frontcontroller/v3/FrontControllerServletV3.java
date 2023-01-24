package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.myView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
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

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/frontcontroller/v3/*")
// *의 의미 : v1/ 하위에 어떤  url이 들어와도 일단 이 서블릿이 무조건 호출된다.
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String, ControllerV3> controllerMap = new HashMap<>();
    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV3()); //map의 키값 지정
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Map<String, String> paramMap = createParamMap(request);//메소드 뽑아내기
        
        ModelView mv = controller.process(paramMap);
        String viewName = mv.getViewName();
        myView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response); //render를 호출하면서 model을 넘김
    }
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        //iterator로 파라미터 다 꺼냄
        return paramMap;
    }
    private myView viewResolver(String viewName) { //뷰를 해결해준다 -> 뷰리졸버
        return new myView("/WEB-INF/views/" + viewName + ".jsp");
    }
}

