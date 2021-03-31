//def path = buildFilePath(PROJECT_BASE_DIR, 'annotations-' + getProjectEntry().getImageName() + '.txt').replace('check_1', 'check_2')
def path = buildFilePath(PROJECT_BASE_DIR, 'annotations_bn-' + getProjectEntry().getImageName() + '.txt')
print path
def annotations = null
new File(path).withObjectInputStream {
    annotations = it.readObject()
}
addObjects(annotations)
selectObjectsByClassification("core")
getSelectedObjects().each{it.setLocked(true)}
//addObjects(annotations)
//getAnnotationObjects().each{it.setLocked(true)}
print 'Added ' + annotations