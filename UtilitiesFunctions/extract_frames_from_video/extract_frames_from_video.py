import cv2

# The parameter frameRate takes in a positive integer between 1 to 24 inclusively, which is 
# how many images needs to be extracted per second. For a 30 seconds video, if you want 30 images,
# 1 would be your frameRate. If you want 60 images instead, 2 would be your frameRate.
def extract_frames_from_mp4(fileName, frameRate):
    mp4_file = fileName + '.mp4'
    # # High definition (OpenCV):
    cv2_ver = (cv2.__version__).split('.')[0]
    if int(cv2_ver)  < 3 :
        capture = cv2.VideoCapture(mp4_file)
        fps = (capture.get(cv2.cv.CV_CAP_PROP_FPS))
    else: 
        capture = cv2.VideoCapture(mp4_file)
        fps = (capture.get(cv2.CAP_PROP_FPS))
        
    print("FPS of the .mp4 file: " + str(fps))
    i = 0
    success = True
    saved_pic_num = 1

    while (success):
        i += 1
        success,image = capture.read()
        if success:
            if (i % int(fps / (frameRate))) == 0:
                cv2.imwrite(fileName + '_' + str(saved_pic_num).zfill(4) + '.jpg', image)
                saved_pic_num += 1
    capture.release()


    return 0


if __name__ == "__main__":
    fileName = input("Please enter the name of .mp4 file:\n")
    frameRate = int(input("How many frames per second do you want to extract?\n"))
    extract_frames_from_mp4(fileName, frameRate)
    	