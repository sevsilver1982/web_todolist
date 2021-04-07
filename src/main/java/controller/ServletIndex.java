package controller;

import com.google.gson.GsonBuilder;
import dao.ItemDaoImpl;
import model.Item;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import service.FilterEnum;
import service.JsonSerializerBoolean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public final class ServletIndex extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletIndex.class);
    private final ItemDaoImpl itemDaoImpl = new ItemDaoImpl();
    private static final String JSON_FILTER_KEY = "filter";
    private static final String JSON_ACTION_KEY = "action";
    private static final String JSON_ADD_KEY = "add";
    private static final String JSON_DELETE_KEY = "delete";
    private static final String JSON_ID_KEY = "id";
    private static final String JSON_RESULT_KEY = "result";

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        // фильтр по запросу
        List<Item> list = itemDaoImpl.findAll(
                FilterEnum.valueOf(request.getParameter(JSON_FILTER_KEY).toUpperCase())
        );

        // отправить json
        try (OutputStream outputStream = response.getOutputStream()) {
            String json = new GsonBuilder()
                    .registerTypeAdapter(Boolean.class, new JsonSerializerBoolean())
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create()
                    .toJson(list);
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (Exception e) {
            LOGGER.error("Message", e);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try (InputStream inputStream = request.getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            // get item
            String json = bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
            Item item = new GsonBuilder()
                    .registerTypeAdapter(Boolean.class, new JsonSerializerBoolean())
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create()
                    .fromJson(json, Item.class);

            // do action
            switch (new JSONObject(json).getString(JSON_ACTION_KEY)) {
                case JSON_DELETE_KEY:
                    Boolean result = itemDaoImpl.delete(item);
                    JSONObject answer = new JSONObject();
                    answer.put(JSON_ACTION_KEY, JSON_DELETE_KEY);
                    answer.put(JSON_ID_KEY, item.getId());
                    answer.put(JSON_RESULT_KEY, result);
                    json = answer.toString();
                    break;
                case JSON_ADD_KEY:
                    json = new GsonBuilder()
                            .registerTypeAdapter(Boolean.class, new JsonSerializerBoolean())
                            .setDateFormat("yyyy-MM-dd hh:mm:ss")
                            .create()
                            .toJson(itemDaoImpl.add(item));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + new JSONObject(json).getString(JSON_ACTION_KEY));
            }

            // send response
            try (OutputStream outputStream = response.getOutputStream()) {
                outputStream.write(json.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        } catch (Exception e) {
            LOGGER.error("Message", e);
        }
    }

}
