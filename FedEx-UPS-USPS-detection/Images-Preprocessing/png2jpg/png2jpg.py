from PIL import Image
import glob
import os,sys
import datetime

folderImg = 'Images'
outFolder = 'outImages'

i = 41
count = 1

for filename in os.listdir(folderImg):
	infilename = os.path.join(folderImg, filename)
	#if not os.path.isfile(infilename): continue
	#oldbase = os.path.splitext(filename)
	names = filename.split('.')
	if (names[-1].lower() == 'png'):
		newname = str(i) + '_' + "{0:04}".format(count) + '_' + str(datetime.date.today()) + '.jpg'
		count += 1

		im = Image.open(infilename)
		#bg = Image.new("RGB", im.size, (255, 255, 255))
		#bg.paste(im, (0, 0), im)
		#bg.save("../outImages/" + newname, quality = 95)
		rgb_im = im.convert('RGB')
		rgb_im.save("./outImages/" + newname)
