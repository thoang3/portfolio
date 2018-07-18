import os,sys
folder = 'gun3048'
for filename in os.listdir(folder):
       infilename = os.path.join(folder,filename)
       if not os.path.isfile(infilename): continue
       oldbase = os.path.splitext(filename)
       #print(oldbase)
       newname = infilename.replace('.JPG', '.jpg')
       output = os.rename(infilename, newname)