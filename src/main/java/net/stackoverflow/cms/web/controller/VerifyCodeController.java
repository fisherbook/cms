package net.stackoverflow.cms.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码接口
 *
 * @author 凉衫薄
 */
@Controller
@Slf4j
public class VerifyCodeController {

    /**
     * 获取验证码
     *
     * @param response http响应对象
     * @param session  会话对象
     */
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public void code(HttpServletResponse response, HttpSession session) throws IOException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String verifyCode = drawImg(output);
        log.info("sessionId:{},验证码:{}", session.getId(), verifyCode);

        session.setAttribute("code", verifyCode);

        ServletOutputStream out = response.getOutputStream();
        output.writeTo(out);
    }

    /**
     * 绘制验证码
     *
     * @param output 字节数组输出流
     * @return 返回验证码
     */
    private String drawImg(ByteArrayOutputStream output) {

        String code = "";
        for (int i = 0; i < 4; i++) {
            code += randomChar();
        }

        int width = 70;
        int height = 30;
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = bi.createGraphics();
        Font font = new Font("Consolas", Font.PLAIN, 25);
        g.setFont(font);
        g.setColor(new Color(80, 80, 180));
        g.setBackground(new Color(255, 255, 255));
        g.clearRect(0, 0, width, height);

        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code, context);
        double x = (width - bounds.getWidth()) / 2;
        double y = (height - bounds.getHeight()) / 2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int) x, (int) baseY);
        g.dispose();
        try {
            ImageIO.write(bi, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 获取随机字符
     *
     * @return 返回获取的字符
     */
    private char randomChar() {
        Random r = new Random();
        String s = "ABCDEFGHJKLMNPRSTUVWXYZ0123456789";
        return s.charAt(r.nextInt(s.length()));
    }
}