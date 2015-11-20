package cz.muni.fi.pv254.utils; /**
 * Created by skylar on 20.11.15.
 */

/**
 * Created by skylar on 20.11.15.
 */

import cz.muni.fi.pv254.entity.Anime;
import cz.muni.fi.pv254.entity.MalUser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DomParser {

    //No generics
    List users;
    List anime;
    Document dom;


    public DomParser(){
        //create a list to hold the employee objects
        users = new ArrayList();
        anime = new ArrayList();
    }

    public void run() {

        //parse the xml file and get the dom object
        parseXmlFile();

        //get each employee element and create a Employee object
        parseDocument();

        //Iterate through the list and print the data
        printData();

    }


    private void parseXmlFile(){
        //get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = db.parse("/home/skylar/IdeaProjects/parseXML/src/malappinfo.xml");


        }catch(ParserConfigurationException pce) {
            pce.printStackTrace();
        }catch(SAXException se) {
            se.printStackTrace();
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }


    private void parseDocument(){
        //get the root elememt
        Element docEle = dom.getDocumentElement();

        //get a nodelist of users elements
        NodeList nl1 = docEle.getElementsByTagName("myinfo");
        if(nl1 != null && nl1.getLength() > 0) {
            for(int i = 0 ; i < nl1.getLength();i++) {

                //get the employee element
                Element el = (Element)nl1.item(i);

                //get the Employee object
                MalUser e = getUser(el);

                //add it to list
                users.add(e);
            }
        }

        NodeList nl2 = docEle.getElementsByTagName("anime");
        if(nl2 != null && nl2.getLength() > 0) {
            for(int i = 0 ; i < nl2.getLength();i++) {

                //get the employee element
                Element el = (Element)nl2.item(i);

                //get the Employee object
                Anime a = getAnime(el);

                //add it to list
                anime.add(a);
            }
        }
    }


    private MalUser getUser(Element empEl) {

        //for each <employee> element get text or int values of
        //name ,id, age and name
        String name = getTextValue(empEl, "user_name");
        int id = getIntValue(empEl, "user_id");

        //Create a new Employee with the value read from the xml nodes
        MalUser u = new MalUser();
        u.setName(name);
        u.setUser_id(id);

        return u;
    }

    private Anime getAnime(Element empEl) {

        //for each <employee> element get text or int values of
        //name ,id, age and name
        String series_title = getTextValue(empEl, "series_title");
        int series_episodes = getIntValue(empEl, "series_episodes");
        String series_start = getTextValue(empEl, "series_start");
        String series_end = getTextValue(empEl, "series_end");

        //Create a new Employee with the value read from the xml nodes
        Anime a = new Anime(series_title, series_episodes, series_start, series_end);

        return a;
    }

    /**
     * I take a xml element and the tag name, look for the tag and get
     * the text content
     * i.e for <employee><name>John</name></employee> xml snippet if
     * the Element points to employee node and tagName is name I will return John
     * @param ele
     * @param tagName
     * @return
     */
    private String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if(nl != null && nl.getLength() > 0) {
            Element el = (Element)nl.item(0);
            textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }


    /**
     * Calls getTextValue and returns a int value
     * @param ele
     * @param tagName
     * @return
     */
    private int getIntValue(Element ele, String tagName) {
        //in production application you would catch the exception
        return Integer.parseInt(getTextValue(ele,tagName));
    }

    /**
     * Iterate through the list and print the
     * content to console
     */
    private void printData(){

        System.out.println("No of Users '" + users.size() + "'.");

        Iterator it = users.iterator();
        while(it.hasNext()) {
            System.out.println(it.next().toString());
        }

        System.out.println("No of anime '" + anime.size() + "'.");

        Iterator it2 = anime.iterator();
        while(it2.hasNext()) {
            System.out.println(it2.next().toString());
        }
    }


    public static void main(String[] args){
        //create an instance
        DomParser dpe = new DomParser();

        //call run example
        dpe.run();
    }

}
