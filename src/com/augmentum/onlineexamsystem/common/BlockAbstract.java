package com.augmentum.onlineexamsystem.common;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import com.augmentum.onlineexamsystem.util.StringUtils;

public abstract class BlockAbstract {
    private String template;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String displayBlock(PageContext pageContext) {
        execute(pageContext);

        Writer body = new StringWriter();

        try {
            if (!StringUtils.isEmpty(template)) {
                pageContext.pushBody(body);
                pageContext.include(template);
                pageContext.popBody();
                return body.toString();
            }
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                body.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public void execute(PageContext pageContext) {
    }
}
