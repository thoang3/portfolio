# This code runs through a folder of frames, and help you copy the frames 
# you like to another folder, e.g. "gun-images", "no-gun-images"

import cv2
import os
import sys

def filteringFrames(fileName):

    folder = fileName + '_mp4'
    #print("\n" + folder)
    pathNoGun = 'no-gun-images'
    pathGun = 'gun-images'

    if not os.path.exists(pathNoGun):
        os.makedirs(pathNoGun)
    if not os.path.exists(pathGun):
        os.makedirs(pathGun)

    listOfFiles = os.listdir(folder + '/Frames/')     
    #print(listOfFiles)
    print("\nYou are now processing folder " + folder) 

    ind = 0

    while ind < len(listOfFiles):
        filename = listOfFiles[ind]
        #print(filename)

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

    return 0


if __name__ == "__main__":
    
    print("How many clips folders are there? Make sure your folders are in the format of '1_mp4, 2_mp4, ...', starting with 1.")
    n = int(input("Enter: "))
    
    for i in range(1, n+1):
        filteringFrames(str(i))
