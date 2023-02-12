package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
        //해당 핸들러가 v3의 인스턴스냐?물어본다
        // -> instanceof를 통해 ControllerV3으로 구현된 뭔가가 넘어오게되면 true를 반환
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        ControllerV3 controller = (ControllerV3) handler;
        /* frontController에서 supports, handle전부다 호출이 되고 넘어오니까 이 메서드에서의 handler는 결국 상단의 supports메서드에 의해
         걸러진 ControllerV3타입의 handler이다. */
        Map<String, String> paramMap = createParamMap(request); //ctrl + alt + v : 해당하는 메소드의 변수 데이터 타입을 바로 추출
        ModelView mv = controller.process(paramMap); //handler는 ModelView반환

        return mv;
    }
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        //iterator로 파라미터 다 꺼냄
        return paramMap;
    }
}
