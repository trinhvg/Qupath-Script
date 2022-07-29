# Qupath-Script
Exporting core tissue and core label from QuPath version 0.2.x
## Note:
There are some changes in syntax between QuPath version 0.2.x and 0.1.x. Referring Quapth Document for more detail [[here]](https://qupath.readthedocs.io/en/latest/).

## To visulize your prediction map on Qupath: refer to 
[[SRA]](https://github.com/christianabbet/SRA) project from Christian Abbet 


## Compress Tiff image with Image.save
from PIL import Image, TiffTags
TiffTags.LIBTIFF_CORE.add(317)
img = Image.open('issue2866.tif')
img.save('issue2866_out.tif', compression='tiff_lzw', tiffinfo={317: 2})
