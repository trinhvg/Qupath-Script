//import org.locationtech.jts.io.WKTWriter
//
////def annotations = getAnnotationObjects()
//selectObjectsByClassification('Region*')
//def annotations = getSelectedObjects()
//def writer = new WKTWriter()
//annotations.each {
//  println writer.write(it.getROI().getGeometry())
//}
//

def pathOutput = buildFilePath(PROJECT_BASE_DIR, 'rectangle_json')
mkdirs(pathOutput)
def path = buildFilePath(pathOutput,getProjectEntry().getImageName() +'.json')

selectObjectsByClassification('Region*')


// all information
//def annotations = getSelectedObjects()
//boolean prettyPrint = true
//def gson = GsonTools.getInstance(prettyPrint)

// rectangle
def rois = getSelectedObjects().collect {it.getROI()}
def gson = GsonTools.getInstance(true)

println gson.toJson(rois)

def file = new File(path)
file.newWriter().withWriter { w ->
  w << gson.toJson(rois)
}
print 'Done!'