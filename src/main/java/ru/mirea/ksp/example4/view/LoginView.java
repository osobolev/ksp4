package ru.mirea.ksp.example4.view;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class LoginView {

    public void view(String error, Writer out) throws IOException, ServletException {
        Map<String, Object> data = new HashMap<>();
        data.put("error", error);
        TemplateUtil.write("login.ftl", data, out);
    }
}
