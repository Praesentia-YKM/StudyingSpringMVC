package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.myView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v3.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/frontcontroller/v4/*")
// *의 의미 : v1/ 하위에 어떤  url이 들어와도 일단 이 서블릿이 무조건 호출된다.
public class FrontControllerServletV4 extends HttpServlet {
    private Map<String, ControllerV4> controllerMap = new HashMap<>();
    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV4()); //map의 키값 지정
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Map<String, String> paramMap = createParamMap(request);//메소드 뽑아내기
        Map<String,Object> model = new HashMap<>(); //추가

        String viewName= controller.process(paramMap,model);

        myView view = viewResolver(viewName); //FrontController에서 viewResolver를 호출
        view.render(model, request, response); //render를 호출하면서 model을 넘김
        //기존엔 modelView에서 모델을 꺼냈는데 이젠 그럴 필요가 없다. frontController에서 모델을 제공하니까! 
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

