package top.zhaoxi.travel.web.servlet;

import top.zhaoxi.travel.domain.Category;
import top.zhaoxi.travel.service.CategoryService;
import top.zhaoxi.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();

    /**
     * 查询所有分类方法
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用service查询所有分类
        List<Category> categories = service.findAll();
        //2.设置编码为utf-8
        response.setContentType("text/html;charset=utf-8");
        //3.序列化json返回
        writeValue(categories,response);
    }
}
