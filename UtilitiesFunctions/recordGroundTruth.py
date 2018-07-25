# This code runs through a folder of frames, and help you copy the frames 
# you like to another folder, e.g. "gun-images", "no-gun-images"

import cv2
import os
import sys

def recordGroundTruth(fileName):

    folder = fileName + '_mp4'
    #print("\n" + folder)
    
    path = folder + '/Ground-truth/'

    if not os.path.exists(path):
        os.makedirs(path)

    listOfFiles = os.listdir(folder + '/Frames/')     
    print(listOfFiles)
    print("\nYou are now processing folder " + folder) 

    ind = 0

    while ind < len(listOfFiles):
        filename = listOfFiles[ind]
        print(filename)
        
        txt_outpath = path + filename.split('.')[0] + '.txt'
        print("Ground-truth output: " + txt_outpath)
        txt_outfile = open(txt_outpath, "w")

        img = cv2.imread(folder + '/Frames/' + filename)
        cv2.imshow('Image', img)
        print("Now showing image: " + filename + " out of " + str(len(listOfFiles)))
        
        print("Does this frame contain a gun? Enter 1 for Yes, 0 for No!")

        k = cv2.waitKey(0)
        check = True
        while (check == True):
            if k == ord('1'):
                print("You chose Yes!")
                txt_outfile.write(str(1) + '\n')
                
                ind += 1
                check = False
            elif k == ord('0'):
                print("You chose No!")
                txt_outfile.write(str(0) + '\n')
                #txt_outfile.close()
                ind += 1
                check = False
            else: 
                print("Please choose only 1/0 in your answer!")
                k = cv2.waitKey(0)

        txt_outfile.close()
        cv2.destroyAllWindows()        

    return 0


if __name__ == "__main__":
    
    print("How many clips folders are there? Make sure your folders are in the format of '1_mp4, 2_mp4, ...', starting with 1.")
    n = int(input("Enter: "))
    
    for i in range(11, n+1):
        recordGroundTruth(str(i))
