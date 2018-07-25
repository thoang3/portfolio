import os
import subprocess
import time
import datetime

def testYoloVideosList(videoName, testFolder, epoch=16000, confidence = 0.50):
    os.chdir("C:/Users/thoan/Aegis-AI/darknet/build/darknet/x64")
    print(os.getcwd())

    _list = ['darknet.exe', 'detector', 'demo', 'data/gun3222.data', 'cfg/yolov3-gun3222test.cfg', '', '', '-i', '0', '-out_filename', '', '-thresh', '']

    print("Epoch is: " + str(epoch) + "\n")
    _list[5] = 'backup/gun5479_AWS/yolov3-gun3222_' + str(epoch) + '.weights'
    #print(_list[5])

    #testFolder = 'gun'
    #print("Test folder is: " + testFolder + "\n")

    _list[12] = str(confidence)
    print("Confidence level is: " + str(confidence) + "\n")

    

    outputDir = 'C:/Users/thoan/Aegis-AI/Yolo-Evaluation/yoloDarknetVideosTesting/' + testFolder + '/' + str(datetime.date.today()) + '_gun5479_' + str(epoch) + '_' + str(int(confidence * 100)) + '%_confidence'
    print("Output directory is: " + outputDir + "\n")    

    if not os.path.exists(outputDir):
        os.makedirs(outputDir)

    _list[6] = 'C:/Users/thoan/Aegis-AI/Yolo-Evaluation/' + testFolder + '/' + videoName + '.mp4'
    print("Now testing: " + _list[6] + "\n")
    
    _list[10] = outputDir + '/' + videoName + '_gun5479AWS_' + str(epoch) + '_' + str(int(confidence * 100)) + '%_608.avi'
    print("Command and output: " + ' '.join(_list))#str(_list))
    
    try:
        print('tung')
        #txt_outpath = 'D:/Yolo-Results/' + str(i) + '.txt'
        #txt_outfile = open(txt_outpath, "w")
        #txt_outfile.close()
        #subprocess.run(_list, check=True)
    except KeyboardInterrupt:
        print('\n\nKeyboard exception received. Exiting.')
        exit()


if __name__ == "__main__":
    print("How many clips are there? Make sure your clips are in the format of '1.mp4, 2.mp4, ...', starting with 1.")
    n = int(input("Enter: "))

    for i in range(1, n+1):
        testYoloVideosList(str(i), "gun-database")

