import qupath.lib.images.writers.ImageWriterTools
import qupath.lib.regions.RegionRequest
import static qupath.lib.gui.scripting.QPEx.*

//def path = buildFilePath(PROJECT_BASE_DIR, 'cores')
////def dirOutput = buildFilePath(PROJECT_BASE_DIR, 'cores')
//mkdirs(path)

def imageData_ano = getCurrentImageData()

// Define output path (relative to project)
def name = GeneralTools.getNameWithoutExtension(imageData_ano.getServer().getMetadata().getName())
def pathOutput = buildFilePath(PROJECT_BASE_DIR, 'core_data', name)
print pathOutput
mkdirs(pathOutput)

def imageData = QPEx.getCurrentImageData()
def server = imageData.getServer()

//def filename = server.getShortServerName()

i = 1

for (annotation in getAnnotationObjects()) {
    if (annotation.getPathClass() == getPathClass('core')){

    roi = annotation.getROI()
    
    def request = RegionRequest.createInstance(imageData.getServerPath(), 
        1, roi)
    
    tiletype = annotation.getParent().getPathClass()
    
    String tilename = String.format("%s_%d.jpg", 'Image', i)
    
    ImageWriterTools.writeImageRegion(server, request, pathOutput + "/" + tilename);
    
    print("wrote " + tilename)
    
    i++
}
}
print 'done'
