// Create an empty text file
import static qupath.lib.gui.scripting.QPEx.*
//import qupath.lib
//import qupath.lib.scripting.QPEx

def name = getProjectEntry().getImageName().replace('.svs', '.txt')
print name

def path = buildFilePath(PROJECT_BASE_DIR,'ano_txt', name)
print(path)
def file = new File(path)
file.text = ''

int i = 0
file << "{" << System.lineSeparator() 
// Loop through all objects & write the points to the file
for (pathObject in QPEx.getAllObjects()) {
    // Check for interrupt (Run -> Kill running script)
    if (Thread.interrupted())
        break
    // Get the ROI
    def roi = pathObject.getROI()
    if (roi == null)
        continue
    print(pathObject.getPathClass())
    //print(pathObject)
    //if (pathObject.getPathClass().getName() != "Core")
    //    continue
    //print pathObject.getName()
    if (i != 0)
        file << "," << System.lineSeparator()
    // Write the points; but beware areas, and also ellipses!
    int j = 0
    file << '\t"' << pathObject.getPathClass() << '":' << "["
    for (point in roi.getAllPoints()) {
        if (j != 0)
            file << "," 
        file << System.lineSeparator() << "\t\t"
        file << "{"
        file << '"X":"' << (int) point.getX() << '"'
        file << ","
        file << '"Y":"' << (int) point.getY() << '"'
        file << "}"
        ++j
    }
    file << System.lineSeparator() << "\t]"
    ++i
}
file << System.lineSeparator() << "}" << System.lineSeparator() 
print 'Done!'