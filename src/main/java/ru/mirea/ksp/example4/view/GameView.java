package ru.mirea.ksp.example4.view;

import ru.mirea.ksp.example4.SessionUser;
import ru.mirea.ksp.example4.dao.User;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameView {

    public void view(SessionUser currentUser, int clickCount,
                     List<User> winners, int maxClickCount,
                     Writer out) throws IOException, ServletException {
        Map<String, Object> data = new HashMap<>();
        data.put("user", currentUser.displayName);
        data.put("clickCount", clickCount);
        if (maxClickCount > 0) {
            data.put("winner", clickCount >= maxClickCount);
            data.put("winners", winners.stream().map(w -> w.displayName).collect(Collectors.toList()));
        } else {
            data.put("winner", false);
            data.put("winners", Collections.emptyList());
        }
        data.put("maxClickCount", maxClickCount);
        TemplateUtil.write("game.ftl", data, out);
    }
}
