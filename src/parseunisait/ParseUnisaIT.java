package parseunisait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author diego
 */
public class ParseUnisaIT {
    
    private static Logger logger = Logger.getLogger("global");
    private static List<Teacher> parsedTeachers = new ArrayList<>();
    private static long id = 0;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       ParseUnisaIT.parse();
       System.out.println(parsedTeachers.size());
    }
    
     public static void parse(){
        try {
            Document doc = Jsoup.connect("http://rubrica.unisa.it/persone?nome=%25").maxBodySize(0).get();
            Element id = doc.getElementById("unisa-middle");
            Elements panels = id.getElementsByAttributeValue("class", "panel panel-primary");
            for(Element e : panels){
                Teacher t = new Teacher();
                t.setId(ParseUnisaIT.id);
                String[] nl = e.child(0).text().split(" ");
                t.setName(nl[0]);
                t.setLastName(nl[1].replace("Personale", ""));
                Element prof = e.child(1).getElementsByTag("tbody").get(0);
                String em = prof.select("a[href*=@]").text();
                t.setEmail(em);
                parsedTeachers.add(t);
                ParseUnisaIT.id++;
            }
        } catch (IOException ex) {
            logger.severe("Non trovato"+ ex.getMessage());
        }

    }

}
