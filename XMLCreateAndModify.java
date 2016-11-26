/*
 * Author:       Shane Bishop
 * Date:         January 6, 2016
 * Program Name: XML Create and Modify
 * Instructor:   Mr Koivisto
 * 
 * Program Description:
 * Creates an XML file, and then modifies the XML file.
 */
package xmlcreateandmodify;

import java.io.*;
import java.util.logging.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class XMLCreateAndModify {
    // The name of the XML file
    private static final String str_FILE_PATH = "Grade 12+ Semester One Courses.xml";
    
    private static boolean bool_fileModified;
    
    /**
     * The main entry method
     * 
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        bool_fileModified = false;
        
        // Creates the initial XML file
        try {
            // Instantiates the output stream writer
            OutputStream fout = new FileOutputStream(str_FILE_PATH);         // Instantiates a file output stream with the parameter str_filePath
            OutputStream bout = new BufferedOutputStream(fout);              // Instantiates a buttered output stream with the parameter fout
            OutputStreamWriter out = new OutputStreamWriter(bout, "8859_1"); // Instantiates an output stream writer with the parameter bout
            
            // Performs initial setup
            out.write("<?xml version=\"1.0\" ");        // The XML version
            out.write("encoding=\"ISO-8859-1\"?>\r\n"); // The encoding type
            out.write("<semester>\r\n");
            
            // Sets up the first course tree
            initializeTree(out,
                    "ICS4E", 
                    "Computer Programming, Grade 12, eLearning",
                    "Teacher A", 
                    "Unmodified");
            
            // Sets up the second course tree
            initializeTree(out, 
                    "AMU4M",
                    "Music, Grade 12",
                    "Mr. Foster",
                    "Unmodified");
             
            // Sets up the third course tree
            initializeTree(out, 
                    "HZT4U",
                    "Philosophy: Questions and Theories, Grade 12, eLearning",
                    "Mr. Parkin",
                    "Unmodified");
            
            out.write("</semester>\r\n");
            
            out.flush(); // Don't forget to flush!
            out.close(); // Don't forget to close the file after working with it!
        }
        // Catches unsupported encoding exceptions
        catch (UnsupportedEncodingException uee) {
            System.out.println("This VM does not support the Latin-1 character set");
        }
        // Catches I/O exceptions
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        
        // Tells the user where the program is at
        System.out.println("XML file has been created. "
                + "Proceeding to print file.");
        
        // Prints the XML file
        try {
            printFile(str_FILE_PATH);
        }
        // Catches parser configuration, SAX, and I/O exceptions
        catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XMLCreateAndModify.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Tells the user where the program is at
        System.out.println("\n\nXML file has been printed. "
                + "Proceeding to modify file.\n\n");
        
        // Modifies the XML file
        try {
            // Retrieves the document for input
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance(); // Creates a new instance of the document builder factory class
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();             // Creates a new document builder
            Document doc = docBuilder.parse(str_FILE_PATH);                            // Retrieves the document for input
            
            // Modifies the trees of the XML file
            modifyTree(doc, 0, "LHBE");  // Modifies the first tree
            modifyTree(doc, 1, "ADSB");  // Modifies the second tree
            modifyTree(doc, 2, "SGDSB"); // Modifies the third tree
            
            bool_fileModified = true;
        }
        // Catches parser configuration exceptions
        catch (ParserConfigurationException pce) {
            System.out.println("A pce has been thrown");
        }
        // Catches I/O exceptions
        catch (IOException ioe) {
            System.out.println("An ioe has been thrown");
        }
        // Catches SAX exceptions
        catch (SAXException saxe) {
            System.out.println("A saxe has been thrown");
        }
        
        // Tells the user where the program is at
        System.out.println("XML file has been modified. "
                + "Proceeding to print the modified file.");
        
        // Prints the XML file
        try {
            printFile(str_FILE_PATH);
        }
        // Catches parser configuration, SAX, and I/O exceptions
        catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XMLCreateAndModify.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Tells the user where the program is at
        System.out.println("\n\nModified XML file has been printed. "
                + "There are no more tasks to run");
    }
    
    /**
     * Initializes a tree of the XML file.
     * 
     * Precondition:  Called from the first try block of the main method
     * Postcondition: The tree has been fully initialized
     * 
     * @param out             The output stream writer
     * @param str_code        The course code
     * @param str_description A description of the course
     * @param str_teacher     The teacher's name
     * @param str_fileType    The type of file
     * 
     * @throws IOException 
     */
    private static void initializeTree(OutputStreamWriter out, 
            String str_code, 
            String str_description, 
            String str_teacher,
            String str_fileType) throws IOException {
        out.write("<course>\r\n");
        out.write("<code>" + str_code + "</code>\r\n");                      // Writes the course code
        out.write("<description>" + str_description + "</description>\r\n"); // Writes the description of the course
        out.write("<teacher>" + str_teacher + "</teacher>\r\n");             // Writes the teacher's name
        out.write("<fileType>" + str_fileType + "</fileType>\r\n");          // Writes the file type
        out.write("</course>\r\n");
    }
    
    /**
     * Modifies a tree of the XML file.
     * 
     * Precondition:  Called from the third try block of the main method
     * Postcondition: The tree has been modified successfully
     * 
     * @param doc             The document object to be modified
     * @param int_index       The index of the tree to be modified (index begins at 0)
     * @param str_schoolBoard The name of the school board
     */
    private static void modifyTree(Document doc, 
            int int_index, String str_schoolBoard) {
        Node course = doc.getElementsByTagName("course").item(int_index); // Retrieves the tree for modification
        Element schoolBoard = doc.createElement("schoolBoard");           // Creates a school board element
        schoolBoard.appendChild(doc.createTextNode(str_schoolBoard));
        course.appendChild(schoolBoard);
    }
    
    /**
     * Prints an element.
     * 
     * Precondition:  Called from the method print
     * Postcondition: The element has been printed successfully
     * 
     * @param courseElement The element to be printed
     * @param str_tagName   The tag name for retrieval
     * @param str_printout  The tag name for printing
     */
    private static void printTree(Element courseElement, 
            String str_tagName, String str_printout) {
        NodeList parentNodeList = courseElement.getElementsByTagName(str_tagName); // Creates a list of the parent nodes
        Element parentElement = (Element) parentNodeList.item(0);                  // Creates an element, which is the element at the beginning of parentNodeList
        NodeList childNodeList = parentElement.getChildNodes();                    // Creates a list of the child nodes
        System.out.println(str_printout + 
                ((Node) childNodeList.item(0)).getNodeValue().trim());
    }
    
    /**
     * Prints the XML file.
     * 
     * Precondition:  The file must be fully created, and must not be in the process of being modified
     * Postcondition: The entire file has been printed to the standard out
     * 
     * @param str_filePath The file path
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    private static void printFile(String str_filePath) 
            throws ParserConfigurationException, SAXException, IOException{
        // Retrieves the document for input
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); // Creates a new instance of the document builder factory class
        DocumentBuilder db = dbf.newDocumentBuilder();                     // Creates a new document builder
        Document doc = db.parse(str_filePath);                             // Retrieves the document for input

        // Outputs the root element of the document
        doc.getDocumentElement().normalize();
        System.out.println("Root element of the doc is " + 
                doc.getDocumentElement().getNodeName());

        // Outputs the total number of courses
        NodeList listOfCourses = doc.getElementsByTagName("course");
        System.out.println("Total number of courses: " + 
                listOfCourses.getLength());

        // Prints the XML file
        for (int int_i = 0; int_i < listOfCourses.getLength(); int_i++) {
            // Iterates through the list of courses
            Node thisNode = listOfCourses.item(int_i);

            if (thisNode.getNodeType() == Node.ELEMENT_NODE) {
                // Stores the current node
                Element courseElement = (Element) thisNode;

                System.out.println(); // Inserts a space in the standard out
                
                // Prints the current tree
                printTree(courseElement, "code",        "Code:         "); // Prints the code element
                printTree(courseElement, "description", "Description:  "); // Prints the description element
                printTree(courseElement, "teacher",     "Teacher:      "); // Prints the teacher element
                printTree(courseElement, "fileType",    "File Type:    "); // Prints the file type element
                
                /*
                // Prints the school board element
                if (!bool_fileModified) {
                    printTree(courseElement, "schoolBoard", "School Board: ");
                }
                */
            }
        }
    }
}
