package ru.mirea.ksp.example4.view;

import freemarker.cache.FileTemplateLoader;
import freemarker.core.HTMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

class TemplateUtil {

    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_30);

    static {
        CONFIGURATION.setDefaultEncoding("UTF-8");
        try {
            CONFIGURATION.setTemplateLoader(new FileTemplateLoader(new File(".")));
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        CONFIGURATION.setOutputFormat(HTMLOutputFormat.INSTANCE);
    }

    static void write(String templateName, Map<String, Object> data, Writer out) throws IOException, ServletException {
        Template template = CONFIGURATION.getTemplate(templateName);
        try {
            template.process(data, out);
        } catch (TemplateException ex) {
            throw new ServletException(ex);
        }
    }
}
