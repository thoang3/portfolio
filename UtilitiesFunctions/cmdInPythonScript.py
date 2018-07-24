
# coding: utf-8

# In[1]:


import os
import subprocess
import time
import datetime


# In[2]:


print(os.getcwd())


# In[3]:


os.chdir("C:/Users/thoan/Aegis-AI/darknet/build/darknet/x64")


# In[4]:


print(os.getcwd())


# In[ ]:


#_validate = "darknet.exe detector map data/gun3222.data cfg/yolov3-gun3222test.cfg backup/gun4435_AWS/yolov3-gun3222_16000.weights"
#_list1 = _validate.split(" ")
#_list1


# In[ ]:


#subprocess.run(_list1, check=True)


# In[ ]:


#_testVideo = "darknet.exe detector demo data/gun3222.data cfg/yolov3-gun3222test.cfg backup/gun4435_AWS/yolov3-gun3222_16000.weights data/clips/Test-Data/.mp4 -i 0 -out_filename data/clips/Test-Data/results/_gun4435AWS_16000_608.avi"
#_list2 = _testVideo.split(" ")
#_list2


# In[ ]:


#subprocess.run(['darknet.exe', 'detector', 'test', 'data/gun3222.data', 'cfg/yolov3-gun3222test.cfg', 'backup/gun4435_AWS/yolov3-gun3222_17000.weights', '-out_filename', 'predictions.png', '-ext_output',  ' <', ' data/gun-test/test.txt ', '>', ' tung.txt'], timeout=10, check=True)


# In[ ]:


#print("Please enter number for corresponding command:")
#print("1. Give mAP/IOU/Precision/Recall on test data")
#print("2. Test on a list of video!")
#x = int(input("Enter: "))


# In[ ]:


'''
while (x != 0):
    if (x == 1):
        #print('tung1')
        try: 
            subprocess.run(_list1, check=True)
        except KeyboardInterrupt:
            print("Keyboard Interrupt!")
            exit()
    elif (x == 2):
        print('tung2')
    elif (x == 0):
        break
    x = int(input("Enter: "))
'''    


# In[ ]:


_list = ['darknet.exe', 'detector', 'demo', 'data/gun3222.data', 'cfg/yolov3-gun3222test.cfg', 
         '', '', '-i', '0', 
         '-out_filename', '', '-thresh', '']

epoch = 18000
print("Epoch is: " + str(epoch) + "\n")
_list[5] = 'backup/gun4844_AWS/yolov3-gun3222_' + str(epoch) + '.weights'
#print(_list[5])

testFolder = 'no-gun'
print("Test folder is: " + testFolder + "\n")

confidence = 0.95
_list[12] = str(confidence)
print("Confidence level is: " + str(confidence) + "\n")

outputDir = 'data/testClips/testGun/' + testFolder + '/' + str(datetime.date.today()) + '_gun4844_' + str(epoch) + '_' + str(int(confidence * 100)) + '%_confidence'
print("Output directory is: " + outputDir + "\n")    

if not os.path.exists(outputDir):
    os.makedirs(outputDir)
    
for i in range(1, 44):
    _list[6] = 'data/testClips/testGun/' + testFolder + '/' + str(i) + '.mp4'
    print("Now testing: " + _list[6] + "\n")
    
    _list[10] = outputDir + '/' + str(i) + '_gun4844AWS_' + str(epoch) + '_' + str(int(confidence * 100)) + '%_608.avi'
    print("Command and output: " + ' '.join(_list))#str(_list))
    
    try:
        #print('tung')
        #txt_outpath = 'D:/Yolo-Results/' + str(i) + '.txt'
        #txt_outfile = open(txt_outpath, "w")
        #txt_outfile.close()
        subprocess.run(_list, check=True)
    except KeyboardInterrupt:
        print('\n\nKeyboard exception received. Exiting.')
        exit()
    
    time.sleep(5)




# In[ ]:


#subprocess.call(["ping", "www.google.com"])


# In[ ]:


#p = subprocess.Popen(['darknet.exe', 'detector', 'map', 'data/gun3222.data', 'cfg/yolov3-gun3222test.cfg', 'backup/gun4435_AWS/yolov3-gun3222_17000.weights'], stdout=subprocess.PIPE, shell=True)


# In[ ]:


#(output, err) = p.communicate()


# In[ ]:


#p_status = p.wait()


# In[ ]:


#print("Command output : ", output)


# In[ ]:


#print("Command exit status/return code : ", p_status)


# In[ ]:


#get_ipython().system("jupyter nbconvert --to script 'cmdInPythonScript.ipynb'")

