import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 class to read the configuration file
 */
public class PropertiesRead {
    private InputStream f;
/**
 * method to open file
 */
    public void fileRead(String nameFile){

        f  = getClass().getResourceAsStream(nameFile);
    }
    private Properties properties = new Properties();
    public Properties GetProperties()
    {
        return properties;
    }

/**
 *  method to take input stream
 */
    public void loadProperties(){

        InputStream is;
        try {
            is = f;

            properties.load(is);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
