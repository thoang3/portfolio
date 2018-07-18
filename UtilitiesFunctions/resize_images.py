from PIL import Image, ExifTags
import glob
import os


for filename in glob.glob('*.jpg'): #assuming jpg

	image=Image.open(filename)
	
	for orientation in ExifTags.TAGS.keys():
		if ExifTags.TAGS[orientation]=='Orientation':
			break
	exif=dict(image._getexif().items())
	if exif[orientation] == 3:
		image=image.rotate(180, expand=True)
	elif exif[orientation] == 6:
		image=image.rotate(270, expand=True)
	elif exif[orientation] == 8:
		image=image.rotate(90, expand=True)
	#print(image.size)
	width, height = image.size
	head, tail = os.path.split(filename)
	#print(head)
	#print(tail)
	if height > 720 or width > 1080:
		if height > width:
			baseheight = 720
			hpercent = (baseheight / float(image.size[1]))
			wsize = int((float(image.size[0]) * float(hpercent)))
			im2 = image.resize((wsize, baseheight), Image.ANTIALIAS)
			im2.save('C:/Users/thoan/Aegis-AI/Images-Preprocessing/resized/' + tail)
		elif width > height:       
		   	basewidth = 1080
		   	wpercent = (basewidth / float(image.size[0]))
		   	hsize = int((float(image.size[1]) * float(wpercent)))
		   	im2 = image.resize((basewidth, hsize), Image.ANTIALIAS)
		   	im2.save('C:/Users/thoan/Aegis-AI/Images-Preprocessing/resized/' + tail)
	else:
	   	im2.save('C:/Users/thoan/Aegis-AI/Images-Preprocessing/resized/' + tail)

	