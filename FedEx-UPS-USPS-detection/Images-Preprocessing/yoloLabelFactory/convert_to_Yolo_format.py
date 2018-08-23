
# coding: utf-8

# In[1]:


import os
from os import walk, getcwd
from PIL import Image


# In[2]:


# This function helps convert the coordinates returned by Figure 8 to YOLO format
def convert(size, box):
    dw = 1./size[0]
    dh = 1./size[1]
    x = (box[0] + box[1])/2.0
    y = (box[2] + box[3])/2.0
    w = box[1] - box[0]
    h = box[3] - box[2]
    x = x*dw
    w = w*dw
    y = y*dh
    h = h*dh
    return (x,y,w,h)


# In[3]:


print(getcwd())
#os.chdir("..")  # go back, same as cd ..


# In[7]:


# Create new folder to store .txt files of YOLO format
if not os.path.exists("Yolo-Labels"):
    os.makedirs("Yolo-Labels")
else: 
    print("Yolo-Labels folder exist! Please check the folder first!")


# In[16]:


# Read regular labels file names
regular_label_list = []
for (dirpath, dirnames, filenames) in walk("./Regular-Labels/"):
    #print(dirpath)
    #print(dirnames)
    print(filenames)
    regular_label_list.extend(filenames)
    break
print(regular_label_list)
print(len(regular_label_list))


# In[29]:


# Conversion process, bounding box of 392 662 660 212 will be 
# converted to (0.37604166666666666, 0.6000000000000001, 0.34375, 0.16562500000000002)
# where 1920 1280 is the size of corresponding image IMG_6339.jpg

# Input:./labels/gun_original/IMG_6339.txt
# Output:./labels/gun/IMG_6339.txt
# 392 662 660 212

# 1920 1280
# (0.37604166666666666, 0.6000000000000001, 0.34375, 0.16562500000000002)

for txt_name in regular_label_list:
    
    """ Open input text files """
    txt_path = "./Regular-Labels/" + txt_name
    print("Input: " + txt_path)
    txt_file = open(txt_path, "r")
    lines = txt_file.read().split('\n')  # or '\r\n'
    
    """ Open output text files """
    txt_outpath = "./Yolo-Labels/" + txt_name
    print("Output: " + txt_outpath)
    txt_outfile = open(txt_outpath, "w")
    
    """ Convert the data to YOLO format """
    ct = 0
    #print(lines)
    for line in lines:
        #print('lenth of line is: ')
        #print(line)
        #print('\n')
        if(len(line) >= 2):
            ct = ct + 1
            print("Regular Bounding Box: " + line)
            elems = line.split(' ')
            #print(elems)
            # Remember that elems are coordinates of x1 y1 x2 y2 where (x1, y1) is the top left corner,  
            # and (x2, y2) is the bottom right corner
            xmin = int(elems[0])
            xmax = int(elems[2])
            ymin = int(elems[1])
            ymax = int(elems[3])
            
            img_path = str('./Images/%s.JPG'%(os.path.splitext(txt_name)[0]))
            print("Image: " + img_path)

            im=Image.open(img_path)
            w= int(im.size[0])
            h= int(im.size[1])
            #w = int(xmax) - int(xmin)
            #h = int(ymax) - int(ymin)
            #print(xmin)
            print("Image's size: " + str(w) + " " + str(h))
            b = (float(xmin), float(xmax), float(ymin), float(ymax))
            #print(b)
            bb = convert((w,h), b)
            print("Yolo's coordinates: ")
            print(bb)
            print("\n")
            txt_outfile.write(str(0) + " " + " ".join([str(a) for a in bb]) + '\n')
            
    txt_outfile.close()        
