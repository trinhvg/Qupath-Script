import qupath.lib.scripting.QPEx
import qupath.lib.roi.*

import qupath.lib.geom.Point2
import qupath.lib.roi.PolygonROI
import qupath.lib.objects.PathAnnotationObject
import qupath.lib.images.servers.ImageServer

//Aperio Image Scope displays images in a different orientation
def rotated = true

//Prompt user for exported aperio image scope annotation file
//def file = QP.getQuPath()//.getDialogHelper().promptForFile('xml', null, 'aperio xml file', null)
//def text = file.getText()
current_svs_path = QPEx.getCurrentServerPath()
current_svs_path = current_svs_path.replace('.ndpi', '.xml').replace('ColonWSI/ColonWSI', 'ColonWSI/ColonWSI_annotation')

def text = new File(current_svs_path).getText()
//def text = new File('/media/vqdang/Data/Workspace/KBSMC/COLON/1010716.xml').getText()

print('Start')
def list = new XmlSlurper().parseText(text)
//def ann_types = [
//        'benign': 'normal', 
//        '3': 'Grade 3', 
//        '4': 'Grade 4', 
//        '5': 'Grade 5'
//] // manually add all the class want to extract here

def ann_types = [
        '1': 'WD', 
        '2': 'MD', 
        '3': 'PD'
] // manually add all the class want to extract here
//print(ann_types['MD'])
//print(ann_types['1'])
for (ann_xml in list.Annotation) {
    print(ann_xml.@Id)
    exterior_annotation = new PolygonROI(0, 0) // place holder
    interior_annotation = new PolygonROI(0, 0) // place holder
    
    if ((ann_xml.@Text in ann_types.keySet())) {
        print((ann_xml.@Text in ann_types.keySet()))
        print(ann_xml.@Text)
        print('continue')
        continue
        }

    for (region_xml in ann_xml.Regions.Region) { 
        def vertices_list = []
        for (vertex_xml in region_xml.Vertices.Vertex) {           
            if (!rotated) {
                X = vertex_xml.@Y.toDouble()
                Y = h - vertex_xml.@X.toDouble()
            }
            else {
                X = vertex_xml.@X.toDouble()
                Y = vertex_xml.@Y.toDouble()
            }
            vertices_list.add(new Point2(X, Y))
        }
        def roi_annotation = new PolygonROI(vertices_list)
        if (region_xml.@NegativeROA == 0) {
            exterior_annotation = PathROIToolsAwt.combineROIs(
                                exterior_annotation, roi_annotation, 
                                PathROIToolsAwt.CombineOp.ADD)    
        } else {
            interior_annotation = PathROIToolsAwt.combineROIs(
                                    interior_annotation, roi_annotation, 
                                    PathROIToolsAwt.CombineOp.ADD)
        }
    }
    
    String a = ann_xml.@Id
    print(a)
    ann_path_type = ann_types[a]
    roi_annotation = PathROIToolsAwt.combineROIs(
                exterior_annotation, interior_annotation, 
                PathROIToolsAwt.CombineOp.SUBTRACT)
    def pathAnnotation = new PathAnnotationObject(roi_annotation)
    pathAnnotation.setPathClass(QPEx.getPathClass(ann_path_type))
    // Add to current hierarchy
    QPEx.addObjects(pathAnnotation)
    print(ann_path_type)
}

print('Done')