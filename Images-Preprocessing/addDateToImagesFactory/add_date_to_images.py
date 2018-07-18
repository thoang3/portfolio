from PIL import Image
import os
from os import walk, getcwd
import datetime

wd = os.getcwd()
wd
	
folder = 'Images'
i = 22
count = 1
for filename in os.listdir(folder):	
	names = filename.split('.')    
	if (names[-1].lower() == 'jpg' or names[-1].lower() == 'jpeg' or names[-1].lower() == 'png'):
		newname = str(i) + '_' + "{0:03}".format(count) + '_' + str(datetime.date.today()) + '.jpg'
		#newname = str(i) + '_' + "{0:03}".format(count) + '_' + '2018-06-06' + '.jpg'
		count += 1
		infilename = os.path.join(folder, filename)
		print(infilename)
		newinfilename = os.path.join(folder, newname)
		print(newinfilename)
		output = os.rename(infilename, newinfilename)
    #else:
    	#print("There might exist images with extension different that .jpg, .jpeg, .png! Please check your images!")