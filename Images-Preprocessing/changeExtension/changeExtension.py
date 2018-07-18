import os,sys
import glob

folder = 'gun3048'
for filename in os.listdir(folder):
       infilename = os.path.join(folder,filename)
       if not os.path.isfile(infilename): continue
       oldbase = os.path.splitext(filename)
       if (oldbase[-1] == '.JPG'):
       	print(oldbase[-1])
       	newname = infilename.replace('.JPG', '.jpg')
       	print(newname)
       	output = os.rename(infilename, newname)
#for filename in glob.glob('gun3048/*.jpg'): #assuming jpg
	#print(filename)