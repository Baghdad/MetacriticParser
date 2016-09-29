package com.kbogdanov.metaparser;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Author: Kirill Bogdanov
 * Date: 12.09.2016.
 */
public class MetaParser implements Parser {
    public void parse() {
        //int num = 0;
        List<String> games = getGamesFromList(0);
        for (String game: games) {
            getGameInfo(game);
        }
        /*do {
            games = getGamesFromList(num++);
        } while (games.size() == 100);*/
    }

    @Nullable
    private List<String> getGamesFromList(int pageNum) {
        try {
            URL url = new URL("http://www.metacritic.com/browse/games/score/metascore/all/all?page=" + pageNum);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
            String path = "./out/" + pageNum + ".html";
            PrintWriter outputFile = new PrintWriter(path);
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while (line != null) {
                outputFile.print(line);
                line = reader.readLine();
            }
            reader.close();
            outputFile.close();
            File file = new File(path);

            TagNode tagNode = new HtmlCleaner().clean(file);
            Document document = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expression = xPath.compile("//div[@class='product_item product_title']/a/@href");
            NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

            LinkedList<String> result = new LinkedList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                result.add(nodeList.item(i).getTextContent());
                System.out.println(nodeList.item(i).getTextContent());
            }
            file.deleteOnExit();
            return result;
        } catch (ParserConfigurationException | IOException | XPathExpressionException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String getGameInfo(String gameUrl) {
        try {
            URL url = new URL("http://www.metacritic.com" + gameUrl + "/details");
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0; H010818)");
            String path = "./out/" + gameUrl.substring(gameUrl.lastIndexOf("/") + 1) + ".html";
            PrintWriter outputFile = new PrintWriter(path);
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while (line != null) {
                outputFile.print(line);
                line = reader.readLine();
            }
            reader.close();
            outputFile.close();
            File file = new File(path);

            TagNode tagNode = new HtmlCleaner().clean(file);
            Document document = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expression = xPath.compile("//h1/a [@href='" + gameUrl + "']|" +
                    "//span[@class='platform']|" +
                    "//li[@class='summary_detail release_data']/span [@class='data']|" +
                    "//span[@itemprop='ratingValue']|" +
                    "//a [@href='" + gameUrl + "/critic-reviews']/span|" +
                    "//a [@class='metascore_anchor' and @href='" + gameUrl + "/user-reviews']/div|" +
                    "//span[@class='count']/a [@href='" + gameUrl + "/user-reviews']|" +
                    "//div [@class='summary_detail product_summary']/span [@class='data']|" +
                    "//div [@class='product_details']/table/tbody/tr");
            NodeList nodeList = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

            Game game = new Game();
            game.setTitle(nodeList.item(0).getTextContent().trim());
            game.setPlatform(nodeList.item(1).getTextContent().trim());

            try {
                game.setRelease(new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH).parse(nodeList.item(2).getTextContent().trim()));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            game.setCriticScore(Integer.parseInt(nodeList.item(3).getTextContent().trim()));
            game.setCriticNumber(Integer.parseInt(nodeList.item(4).getTextContent().trim()));
            game.setUserScore(Float.parseFloat(nodeList.item(5).getTextContent().trim()));
            game.setUserNumber(Integer.parseInt(nodeList.item(6).getTextContent().trim().split(" ")[0]));
            game.setSummary(nodeList.item(7).getTextContent().trim());

            for (int i = 8; i < nodeList.getLength(); i++) {
                String nodeText = nodeList.item(i).getTextContent().trim();
                String title = nodeText.substring(0, nodeText.indexOf(":"));
                String data = nodeText.substring(title.length() + 1).trim();
                switch (title) {
                    case "Rating":
                        game.setRating(data);
                        break;
                    case "Official Site":
                        game.setSite(data);
                        break;
                    case "Developer":
                        game.setDeveloper(data);
                        break;
                    case "Genre(s)":
                        game.setGenre(data);
                        break;
                    case "Number of Players":
                        game.setPlayerNumber(data);
                        break;
                    case "ESRB Descriptors":
                        game.setESRBDescription(data);
                        break;
                    default:
                }
            }
            System.out.println(game);
            file.deleteOnExit();

        } catch (ParserConfigurationException | IOException | XPathExpressionException ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
