import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.function.Consumer;

class Utils {

    public static void recusiveFile(File file, Consumer<File> consumer) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File subFile : listFiles) {
                    recusiveFile(subFile, consumer);
                }
            }
        } else {
            consumer.accept(file);
        }
    }

    public static Document loadXml(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(file);
        } catch (Throwable t) {
            throw new RuntimeException("load xml error. fileName:" + file.getName());
        }
    }
}
