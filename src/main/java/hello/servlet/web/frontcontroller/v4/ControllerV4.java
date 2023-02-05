package hello.servlet.web.frontcontroller.v4;

import java.util.Map;

public interface ControllerV4 {
    /**
     *
     * @param paramMap
     * @param model
     * @return viewName
     */
    String process(Map<String,String> paramMap, Map<String,Object>model);
    //인터페이스에 ModelView가 없다! model객체는 파라미터로 전달되기 떄문에 그냥 사용하면 되고, 결과로 뷰의 이름만 반환해주면 된다!
}
