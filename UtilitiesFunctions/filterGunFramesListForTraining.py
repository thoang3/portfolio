# This code runs through a folder of frames, and help you copy the frames 
# you like to another folder. 

import cv2
import os
#import datetime
#import glob
import sys

def filteringFrames(fileName):

    folder = fileName + '_mp4'
    print(folder)
    pathNoGun = 'no-gun-images'
    pathGun = 'gun-images'

    if not os.path.exists(pathNoGun):
        os.makedirs(pathNoGun)
    if not os.path.exists(pathGun):
        os.makedirs(pathGun)

    listOfFiles = os.listdir(folder + '/Frames/')     
    print(listOfFiles)

    ind = 0

    while ind < len(listOfFiles):
        filename = listOfFiles[ind]
        print(filename)

        img = cv2.imread(folder + '/Frames/' + filename)
        cv2.imshow('Image', img)
        print("Now showing image: " + filename + " out of " + str(len(listOfFiles)))
        k = cv2.waitKey(0)

        if k == ord('y'):
            print(filename + " copied to GUN folder!")
            cv2.imwrite(os.path.join(pathGun, filename), img)
            ind += 1
        elif k == ord('n'):
            print(filename + " copied to NO-gun folder!")
            cv2.imwrite(os.path.join(pathNoGun, filename), img)
            ind += 1
        elif k == ord('f'): # break the function to go to next folder
            
            cv2.destroyAllWindows()
            break
        elif k == ord('b'): # go backward
            ind -= 1
            if ind == -1:
                ind += len(listOfFiles)
        elif k == ord('5'):
            ind += 5
        elif k == ord('q'):
            sys.exit()
        else:
            ind += 1    
        cv2.destroyAllWindows()
        


    '''
    for filename in glob.glob(folder + '/Frames/*.jpg'):
        name = filename.split('\\')[-1]
        img = cv2.imread(filename)
        cv2.imshow('Image', img)
        k = cv2.waitKey(0)
        if k == ord('y'):
            print('copied')
            cv2.imwrite(os.path.join(path, name), img)
            cv2.destroyAllWindows()
        elif k == ord('f'):        
            cv2.destroyAllWindows()
            break
        # Will make couple elif more options here to move forward 10 frames, 20 frames, ...
        '''
    return 0


if __name__ == "__main__":
   
      for i in range(2, 13):
        filteringFrames(str(i))
    #else: 
        #print("Folder exists! Please move data if needed and delete the folder first.")
    #os.makedirs(fileName + "/Gun")
    #os.makedirs(fileName + "/No-Gun")
    #os.makedirs(fileName + "/Gun/Images")
    #os.makedirs(fileName + "/Gun/Regular-Labels")
    #os.makedirs(fileName + "/No-Gun/Images")
    
    	