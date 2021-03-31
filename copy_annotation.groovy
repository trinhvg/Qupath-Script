//Export section
//PROJECT_DIR = 'C:/Users/Trinh/Documents/Trinh/Documents/trinh/KBSMC_Gastric_grade/GC_project/'
def path = buildFilePath(PROJECT_BASE_DIR, 'annotations_bn-' + getProjectEntry().getImageName()).replace('svs', 'txt') 

// choose specific class to copy
//selectObjectsByClassification("core")
//def annotations = getSelectedObjects()

//Generally use the findAll{} functionality to get subsets of objects (I think it is covered more in the Selecting Objects gist).
//So you could do it all in one step, but it might be easier to read in to.

def tumorAnnotations = getAnnotationObjects().findAll{it.getPathClass()==getPathClass("mucosa") || it.getPathClass()==getPathClass("submucosa") || it.getPathClass()==getPathClass("MM")}
//def tumorAnnotations = getAnnotationObjects().findAll{it.getPathClass() !=getPathClass("Tumor") || it.getPathClass() !=getPathClass("EBV")}
annotations = tumorAnnotations.collect {new qupath.lib.objects.PathAnnotationObject(it.getROI(), it.getPathClass())}
// selected all ROIS
//def annotatiSons = getAnnotationObjects()
//def annotations = getAnnotationObjects().collect {new qupath.lib.objects.RPathAnnotationObject(it.getROI(), it.getPathClass())}

new File(path).withObjectOutputStream {
    it.writeObject(annotations)
}
print path
print 'Done!'