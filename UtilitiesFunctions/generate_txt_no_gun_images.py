# Generate empty text file for no gun images
from PIL import Image
import os
from os import walk, getcwd


print(getcwd())

# Create new folder to store .txt files of YOLO format
if not os.path.exists("Yolo-Labels"):
    os.makedirs("Yolo-Labels")
else: 
    print("Yolo-Labels folder exist! Please check the folder first!")

# Read images' names
img_name_list = []
for (dirpath, dirnames, filenames) in walk('./Images'):
    img_name_list.extend(filenames)
    break
print(img_name_list)


for img_name in img_name_list:   
    """ Open output text files """
    txt_outpath = './Yolo-Labels/' + img_name.split('.')[0] + '.txt'
    print("Output:" + txt_outpath)
    txt_outfile = open(txt_outpath, "w")
    txt_outfile.close()            
#list_file.close()