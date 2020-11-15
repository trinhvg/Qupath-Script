import qupath.lib.images.servers.LabeledImageServer
import qupath.lib.images.writers.ImageWriterTools
import qupath.lib.regions.RegionRequest
import static qupath.lib.gui.scripting.QPEx.*

def imageData_ano = getCurrentImageData()

// Define output path (relative to project)
def name = GeneralTools.getNameWithoutExtension(imageData_ano.getServer().getMetadata().getName())
def pathOutput = buildFilePath(PROJECT_BASE_DIR, 'core_data', name)
print pathOutput
mkdirs(pathOutput)

//def imageData = QPEx.getCurrentImageData()
//def server = imageData.getServer()

//// Define output resolution
//double requestedPixelSize = 2.0
//
//// Convert to downsample
//double downsample = requestedPixelSize / imageInfor.getServer().getPixelCalibration().getAveragedPixelSize()

// Create an ImageServer where the pixels are derived from annotations
def labelServer = new LabeledImageServer.Builder(imageData_ano)
    .backgroundLabel(0, ColorTools.WHITE) // Specify background label (usually 0 or 255)
    .downsample(1.0)    // Choose server resolution; this should match the resolution at which tiles are exported
    .addLabel('Tumor', 1)      // Choose output labels (the order matters!)
    .addLabel('EBV', 2)
    .addLabel('MM', 3)
    .addLabel('mucosa', 4)
    .addLabel('submucosa', 5)
    //.lineThickness(0)          // Optionally export annotation boundaries with another label
    //.setBoundaryLabel('Boundary*', 4) // Define annotation boundary label
    .multichannelOutput(false) // If true, each label refers to the channel of a multichannel binary image (required for multiclass probability)
    .build()


// Export each region
int i = 0
for (annotation in getAnnotationObjects()) {
    if (annotation.getPathClass() == getPathClass('core')){
        print annotation.getPathClass()
        def region = RegionRequest.createInstance(
            labelServer.getPath(), 1.0, annotation.getROI())
            //print region
        i++
        def outputPath = buildFilePath(pathOutput, 'Image_' + i + '_ano' + '.png')
        //def outputPath = buildFilePath(pathOutput, annotation.getName() + '.png')
        writeImageRegion(labelServer, region, outputPath)
        
//        roi = annotation.getROI()
//        def request = RegionRequest.createInstance(imageData.getServerPath(),1, roi)
//        tiletype = annotation.getParent().getPathClass()       
//        String tilename = String.format("%s_%d.jpg", 'Tissue', i)
//        ImageWriterTools.writeImageRegion(server, request, pathOutput + "/" + tilename);
//        print("wrote " + tilename)
}
}
print 'done'
