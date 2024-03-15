package cn.com.cgh.free;

import cn.hutool.core.codec.Base64Encoder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultKeyedValuesDataset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CreateWordTest {
    private static Template getTemplate(String name) throws IOException {
        //创建freeMarker配置对象
//        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        //设置读取模板的基础路径
        cfg.setClassForTemplateLoading(CreateWordTest.class, "/templates"); // 指定模板加载路径
        //设置编码格式
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        //设置模板异常处理
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        //选择日志处理
        cfg.setLogTemplateExceptions(true);
        return cfg.getTemplate(name);
    }
    public static String getTemplateHtml(String name, Map<String, Object> data) throws IOException {
        //创建freeMarker配置对象
        Template temp = getTemplate(name);
        String result = "";
        try {
            //获取模板问价
            StringWriter stringWriter = new StringWriter();
            //替换占位符
            temp.process(data, stringWriter);
            stringWriter.flush();
            stringWriter.close();
            //获取ftl内容字符串
            result = stringWriter.getBuffer().toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String convertImageToBase64String(String imagePath) throws IOException {
        // 读取图片
        BufferedImage image = ImageIO.read(new File(imagePath));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos); // 这里假设图片是PNG格式，可以根据需要更改
        byte[] imageBytes = baos.toByteArray();
        return Base64Encoder.encode(imageBytes);
    }

    public static void main(String[] args) throws IOException, TemplateException {
        exportWord();
//        createPieChart();
    }

    private static void exportWord() throws IOException, TemplateException {
        // Data model
        Map<String, Object> data = new HashMap<>();
        data.put("title", "虚假业绩风险-湖南省邵阳市大祥区域公共点-2022年4账期");
        Map one = new HashMap();
        one.put("numberType", "${numberType}");
        one.put("number", "${number}");
        one.put("type", "${type}");
        data.put("year", "${year}");
        data.put("month", "${month}");
        data.put("img1", "${img1}");
        data.put("img2", "${img2}");
        data.put("img3", "${img3}");
        data.put("img4", "${img4}");
        data.put("img5", "${img5}");
        data.put("img6", "${img6}");
        data.put("img7", "${img7}");
        data.put("img8", "${img8}");
        data.put("img9", "${img9}");
        data.put("img10", "${img10}");
        data.put("img11", "${img11}");
        data.put("one", one);
        Map two = new HashMap();
        two.put("type", "${type}");
        two.put("number1", "${number1}");
        two.put("number2", "${number2}");
        data.put("two", two);

        Template template = getTemplate("/template.ftl");
        try (
                Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("document.docx"), "UTF-8"))
        ) {
            template.process(data, out);
            out.flush();
        }
    }

    public static void createPieChart() {
        DefaultKeyedValuesDataset dataset = new DefaultKeyedValuesDataset();
        dataset.setValue("s1", 50);
        dataset.setValue("s2", 20);
        dataset.setValue("s3", 10);
        dataset.setValue("s4", 5);
        JFreeChart pieChart = ChartFactory.createPieChart("测试", dataset);

        // 显示图表（这里假设你想要在Swing应用程序中显示）
        // JFrame frame = new JFrame("Bar Chart");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ChartPanel chartPanel = new ChartPanel(barChart);
        // frame.getContentPane().add(chartPanel);
        // frame.pack();
        // frame.setVisible(true);
        try {
            ChartUtils.saveChartAsJPEG(new File("chart_bar.png"), pieChart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
