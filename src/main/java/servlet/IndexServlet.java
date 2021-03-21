package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Category;
import model.Item;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import store.HyberStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter writer = new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8);
        JSONArray ar = new JSONArray();
        List<Item> tasks = (List<Item>) HyberStore.instOf().findAll();
        for (Item task :  tasks) {
            JSONObject json = new JSONObject();
            json.put("idTask", task.getId());
            json.put("description", task.getDescription());
            json.put("creatDate", task.getCreateDate());
            json.put("finished", task.isFinished());
            JSONArray arCat = new JSONArray();
            for (Category category :  task.getCategories()) {
                arCat.put(category.getName());
            }
            json.put("categorories", arCat);
            json.put("userName", task.getUser().getName());
            ar.put(json);
        }
        writer.println(ar);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("idTask") != null)  {
            Integer id = Integer.parseInt(req.getParameter("idTask"));
            Item item = HyberStore.instOf().findById(id);
            item.setFinished(true);
            HyberStore.instOf().update(id, item);
        } else {
            resp.setContentType("json");
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Access-Control-Allow-Origin", "*");
            String description = req.getParameter("description");
            User user = (User) req.getSession().getAttribute("user");
            Item item = new Item(description, false, user);
            System.out.println(req.getParameter("description"));
            String[] array = req.getParameterValues("categories[]");
            for (String s : array) {
                item.addCat(HyberStore.instOf().findByIdCategory(Integer.parseInt(s)));
            }
            item = HyberStore.instOf().create(item);
            PrintWriter writer = new PrintWriter(resp.getOutputStream(), true, StandardCharsets.UTF_8);
            JSONObject json = new JSONObject();
            json.put("idTask", item.getId());
            json.put("description", item.getDescription());
            json.put("userName", item.getUser().getName());
            json.put("creatDate", item.getCreateDate());
            JSONArray arCat = new JSONArray();
            for (Category category :  item.getCategories()) {
                arCat.put(category.getName());
            }
            json.put("categorories", arCat);
            json.put("finished", item.isFinished());
            writer.println(json);
            writer.flush();
        }
    }
}
