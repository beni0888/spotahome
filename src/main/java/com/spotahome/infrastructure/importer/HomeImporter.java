package com.spotahome.infrastructure.importer;

import com.spotahome.application.AddHome;
import com.spotahome.application.AddHomeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Component
@Transactional
public class HomeImporter implements CommandLineRunner {

    private static final String FEED_URL = "http://feeds.spotahome.com/trovit-Ireland.xml";

    private AddHome addHome;

    private RestTemplate restTemplate;

    @Autowired
    public HomeImporter(AddHome addHome, RestTemplate restTemplate) {
        this.addHome = addHome;
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String feedContent = restTemplate.getForObject(FEED_URL, String.class);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(feedContent)));

        NodeList ads = doc.getElementsByTagName("ad");
        for (int i = 0; i < ads.getLength(); i++) {
            Element home = (Element) ads.item(i);

            Long id = Long.parseLong(home.getElementsByTagName("id").item(0).getTextContent().trim());
            String title = home.getElementsByTagName("title").item(0).getTextContent().trim();
            String city = home.getElementsByTagName("city").item(0).getTextContent().trim();

            String url = home.getElementsByTagName("url").item(0).getTextContent();
            if (url != null) {
                url = url.trim();
            }

            String picture = home.getElementsByTagName("picture").item(0).getTextContent();
            if (picture != null) {
                picture = picture.trim();
            }

            AddHomeCommand command = new AddHomeCommand(id, title, city, url, picture);
            addHome.execute(command);
        }
    }
}
